/*
 * Copyright (c) 2015 SONATA-NFV, 2017 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * ALL RIGHTS RESERVED.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Neither the name of the SONATA-NFV, 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * nor the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * This work has been performed in the framework of the SONATA project,
 * funded by the European Commission under Grant number 671517 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the SONATA
 * partner consortium (www.sonata-nfv.eu).
 *
 * This work has been performed in the framework of the 5GTANGO project,
 * funded by the European Commission under Grant number 761493 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the 5GTANGO
 * partner consortium (www.5gtango.eu).
 */

package com.github.h2020_5gtango.vnv.lcm.scheduler

import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import com.github.h2020_5gtango.vnv.lcm.restclient.TestCatalogue
import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.workflow.WorkflowManager
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Log
@Component
class Scheduler {

    @Autowired
    TestCatalogue testCatalogue

    @Autowired
    WorkflowManager workflowManager

    void scheduleTests(String packageId) {
        discoverAssociatedNssAndTests(
                load(packageId)
        ).each {networkService,testSuiteList ->
            workflowManager.execute(networkService,testSuiteList)
        }
    }

    void scheduleTests(PackageMetadata packageMetadata) {
        discoverAssociatedNssAndTests(
                load(packageMetadata)
        ).each {networkService,testSuiteList ->
            workflowManager.execute(networkService,testSuiteList)
        }
    }

    PackageMetadata load(String packageId) {
        testCatalogue.loadPackageMetadata(packageId)
    }

    PackageMetadata load(PackageMetadata packageMetadata) {
        PackageMetadata metadata = new PackageMetadata();

        packageMetadata.networkServices?.each { ns ->
                def s = testCatalogue.findNetworkService(ns.networkServiceId)
                if(s) metadata.networkServices << s

        }

        packageMetadata.testSuites?.each { ts ->
                def t = testCatalogue.findTestSuite(ts.testUuid)
                if(t) metadata.testSuites << t
        }

        log.info("##vnvlog: \nnetworkServices: ${metadata.networkServices}, \ntestSuites: ${metadata.testSuites}")
        metadata
    }

    Map discoverAssociatedNssAndTests(PackageMetadata packageMetadata) {

        def nsAndTestsMapping = [:] as HashMap

        def testSuiteHelperMap = [:] as HashMap
        def networkServiceHelperMap = [:] as HashMap
        def filteredTestSuiteHelperList = [] as Set
        def tagHelperList = [] as Set
        def scannedByTag

        //notes: load the testSuiteHelperMap with all the associated tests according to the extracted tags
        packageMetadata.networkServices?.each { ns ->
            if(ns) {
                networkServiceHelperMap.put(ns.networkServiceId,ns)
                ns.nsd.testingTags?.each { tag ->
                    if(!tagHelperList.contains(tag)) {
                        testCatalogue.findTssByTestTag(tag)?.each { ts ->
                            if(!testSuiteHelperMap.containsKey(ts.testUuid)){
                                ts.packageId = packageMetadata.packageId?: testCatalogue.findPackageId(ts)
                                testSuiteHelperMap.put(ts.testUuid,ts)
                            }
                        }
                        tagHelperList << tag
                    }
                }
            }
        }

        tagHelperList = []
        //notes: load the networkServiceHelperMap with all the associated services according to the requested tests
        packageMetadata.testSuites?.each { ts ->
            if ( !testSuiteHelperMap.containsKey(ts.testUuid)) {
                ts.packageId = packageMetadata.packageId?: testCatalogue.findPackageId(ts)
                testSuiteHelperMap.put(ts.testUuid,ts);
            }
                ts.testd.testExecution?.each { tag ->
                    if(!tag.testTag.isEmpty() && !(tagHelperList.contains(tag.testTag))) {
                    testCatalogue.findNssByTestTag(tag.testTag)?.each { ns ->
                        if(!networkServiceHelperMap.containsKey(ns.networkServiceId))
                            networkServiceHelperMap.put(ns.networkServiceId, ns)
                    }
                    scannedByTag = true
                    if(!(tagHelperList.join(",").contains(tag.testTag)))
                        tagHelperList << tag.testTag
                }
            }
        }

        //notes: load the nsAndTestsMapping with all the related NetworkServices, TestsSuites and Tags
        //cleancode: new loop for the extraction of matching tags
        networkServiceHelperMap.each { networkService2Id, networkService2 ->
            networkService2.nsd.testingTags?.each { tag ->
                filteredTestSuiteHelperList += testSuiteHelperMap.findAll { key, value ->
                    value.testd.testExecution.findIndexOf { tt ->
                        tt.testTag.contains(tag) || tag.contains(tt.testTag)
                    } > 0
                }.values()
            }

            List tss = new ArrayList(filteredTestSuiteHelperList)

            nsAndTestsMapping.put(networkService2, tss)
        }

        def nsAndTestsMappingCandidateNew = nsAndTestsMapping

        //cleancode: old loop for the extraction of matching tags
        nsAndTestsMapping = [:]
        networkServiceHelperMap.each { networkServiceId, networkService ->
            networkService.nsd.testingTags?.each { tag ->
                def filteredTestSuiteHelperMap = testSuiteHelperMap.findAll { key, value -> value.testd.testExecution.testTag.join(",").contains(tag) }
                filteredTestSuiteHelperMap.each { testId, ts ->
                    nsAndTestsMapping = addNsTestToMap(nsAndTestsMapping, networkService, ts)
                    }
            }
        }


        def nsAndTestsMappingCandidateOld = nsAndTestsMapping

        def stringBuffer = new StringBuffer("\n ---- \n")
        stringBuffer.append("##vnvlog testPlantsDebate: \n")
        stringBuffer.append(" -- NEW -- nss(#${nsAndTestsMappingCandidateNew?.keySet()?.size()})");
        nsAndTestsMappingCandidateNew.each { ns1, tss1 -> stringBuffer.append(
                "\n + nsId: ${ns1}, \n\t - tsId's (#${tss1.size()}): ");
            tss1.each { t1 ->
                stringBuffer.append(" ${t1.testUuid},"
                )
            }
        }
        stringBuffer.append("\n ---- \n")
        stringBuffer.append(" -- OLD -- nss(#${nsAndTestsMappingCandidateOld?.keySet()?.size()})");
        nsAndTestsMappingCandidateOld.each { ns1, tss1 -> stringBuffer.append(
                "\n + nsId: ${ns1}, \n\t - tsId's (#${tss1.size()}): ");
            tss1.each { t1 ->
                stringBuffer.append(" ${t1.testUuid},"
                )
            }
        }
        stringBuffer.append("\n ")
        log.info("${stringBuffer.toString()}")

        nsAndTestsMapping
    }

    Map addNsTestToMap(Map nsAndTestsMapping, NetworkService ns, TestSuite ts) {
        def tss = nsAndTestsMapping.get(ns)
        nsAndTestsMapping.remove(ns)
        if(tss != null)
            tss << ts
        else
            tss = [ts]
        nsAndTestsMapping.put(ns, tss)

        nsAndTestsMapping
    }
}