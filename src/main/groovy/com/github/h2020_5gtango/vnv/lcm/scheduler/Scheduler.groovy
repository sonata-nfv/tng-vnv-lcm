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
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

import java.util.concurrent.CompletableFuture

import static com.github.h2020_5gtango.vnv.lcm.helper.DebugHelper.nsAndTestsMappingToString

@Log
@Component
class Scheduler {

    @Autowired
    TestCatalogue testCatalogue

    @Autowired
    WorkflowManager workflowManager

    @Async
    CompletableFuture<Boolean> schedule(PackageMetadata packageMetadata) {
        packageMetadata = (packageMetadata != null && packageMetadata.packageId == null) ?
                packageMetadata : testCatalogue.loadPackageMetadata(packageMetadata.packageId)

        def map = discoverAssociatedNssAndTests(loadByMetadata(packageMetadata))
        Boolean out = (map == null) ? false : map.every {networkService,testSuites ->
            workflowManager.execute(networkService,testSuites) == true
        }
        CompletableFuture.completedFuture(out)
    }

    PackageMetadata loadByMetadata(PackageMetadata packageMetadata) {
        if(!packageMetadata) return
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
        if(!packageMetadata) return
        def nsAndTestsMapping = [:] as HashMap
        def tss = [] as Set

        //notes: loadByPackageId the nsAndTestsMapping with all the given services
        packageMetadata.networkServices?.each { ns ->
                ns.nsd.testingTags?.each { tag ->
                        testCatalogue.findTssByTestTag(tag)?.each { ts ->
                            ts = addPackageIdToTestSuit(packageMetadata,ts)
                            tss << ts
                        }
                }
            if(!nsAndTestsMapping.containsKey(ns))
                nsAndTestsMapping.put(ns,tss)
            else
                nsAndTestsMapping.put(ns, tss << nsAndTestsMapping.get(ns))
            tss = []
        }

        //notes: loadByPackageId the nsAndTestsMapping with all the associated services according to the given tests
        packageMetadata.testSuites?.each { ts -> ts.testd.testExecution?.each { tag ->
                if(!tag.testTag.isEmpty()) {
                    testCatalogue.findNssByTestTag(tag.testTag)?.each { ns ->
                        ts = addPackageIdToTestSuit(packageMetadata,ts)
                        if(!nsAndTestsMapping.containsKey(ns))
                            nsAndTestsMapping.put(ns, tss = [] << ts)
                        else
                            nsAndTestsMapping.put(ns, tss = nsAndTestsMapping.get(ns) << ts)
                    }
                }
            }
        }
        if(nsAndTestsMapping.keySet().size() == 0
                || nsAndTestsMapping.values().first() == null
                || nsAndTestsMapping.values()?.first().size() == 0 ) {
            return
        }
        log.info(nsAndTestsMappingToString(nsAndTestsMapping))

        nsAndTestsMapping
    }

    //todo: this is a workaround - to add the packageId to the TestSuite - until the packageId be removed from the source
    def addPackageIdToTestSuit(PackageMetadata metadata, TestSuite ts) {
        ts.packageId = metadata.packageId?: testCatalogue.findPackageId(ts)
        ts
    }
}