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

package com.github.h2020_5gtango.vnv.lcm.restclient

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.model.TestTag
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

import static com.github.h2020_5gtango.vnv.lcm.helper.DebugHelper.callExternalEndpoint

@Log
@Component
class TestCatalogue {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplateWithAuth

    @Autowired
    @Qualifier('restTemplateWithoutAuth')
    RestTemplate restTemplate

    @Value('${app.gk.package.metadata.endpoint}')
    def packageMetadataEndpoint

    @Value('${app.gk.package.list.endpoint}')
    def packageListEndpoint

    @Value('${app.vnvgk.test.metadata.endpoint}')
    def testMetadataEndpoint

    @Value('${app.vnvgk.test.list.endpoint}')
    def testListEndpoint

    @Value('${app.gk.service.metadata.endpoint}')
    def serviceMetadataEndpoint

    @Value('${app.gk.service.list.endpoint}')
    def serviceListEndpoint


    PackageMetadata loadPackageMetadata(String packageId) {
        if(packageId == null || packageId.length() == 0){
            log.info("##vnvlog packageId is empty or null")
            return
        } else {
            log.info("##vnvlog packageId: $packageId")
        }
        def rawPackageMetadata= callExternalEndpoint(restTemplate.getForEntity(packageMetadataEndpoint,Object.class,packageId),
                'TestCatalogue.loadPackageMetadata',packageMetadataEndpoint).body
        PackageMetadata packageMetadata=new PackageMetadata(packageId: packageId)
        rawPackageMetadata?.pd?.package_content.each{resource ->
            switch (resource.get('content-type')) {
                case 'application/vnd.5gtango.tstd':
                    TestSuite ts = callExternalEndpoint(restTemplateWithAuth.getForEntity(testMetadataEndpoint, TestSuite.class, resource.uuid),
                            'TestCatalogue.findNssByTestTag','TestCatalogue.loadPackageMetadata',testMetadataEndpoint).body
                    log.info("##vnvlog res: testSuite: $ts")
                    log.info("##vnvlog agnostic obj " + callExternalEndpoint(
                            restTemplateWithAuth.getForEntity(testMetadataEndpoint, Object.class, resource.uuid),
                            'TestCatalogue.loadPackageMetadata','TestCatalogue.loadPackageMetadata',
                            testMetadataEndpoint).body.each {println it})
                    if(ts.testUuid)
                        packageMetadata.testSuites << ts
                    break
                case 'application/vnd.5gtango.nsd':
                    NetworkService ns =  callExternalEndpoint(restTemplateWithAuth.getForEntity(serviceMetadataEndpoint,
                            NetworkService.class, resource.uuid),'TestCatalogue.loadPackageMetadata',
                            serviceMetadataEndpoint).body
                    log.info("##vnvlog Request: res: networkService: $ns")
                    log.info("##vnvlog agnostic obj: " + callExternalEndpoint(
                            restTemplateWithAuth.getForEntity(serviceMetadataEndpoint, Object.class, resource.uuid),
                            'TestCatalogue.loadPackageMetadata',serviceMetadataEndpoint).body.each {println it})
                    if(ns.networkServiceId)
                        packageMetadata.networkServices << ns
                    break
            }
        }
        packageMetadata
    }

    NetworkService findNetworkService(String networkServiceId) {
        callExternalEndpoint(restTemplateWithAuth.getForEntity(serviceMetadataEndpoint, NetworkService, networkServiceId),
                'TestCatalogue.findNetworkService',serviceMetadataEndpoint).body
    }

    TestSuite findTestSuite(String testUuid) {
        callExternalEndpoint(restTemplateWithAuth.getForEntity(testMetadataEndpoint, TestSuite, testUuid),
                'TestCatalogue.findTestSuite',testMetadataEndpoint).body
    }

    List<NetworkService> findNssByTestTag(String tag) {
        callExternalEndpoint(restTemplateWithAuth.getForEntity(serviceListEndpoint, NetworkService[]),
                'TestCatalogue.findNssByTestTag',serviceListEndpoint).body?.findAll
                { ns -> ns.nsd.testingTags !=null && (ns.nsd.testingTags.contains(tag) )}
        }

    List<TestSuite> findTssByTestTag(String tag) {
        callExternalEndpoint(restTemplateWithAuth.getForEntity(testListEndpoint, TestSuite[]),
                'TestCatalogue.findTssByTestTag',testListEndpoint).body?.findAll
                { ts -> ts.testd.testExecution.findAll { it.testTag.equalsIgnoreCase(tag) } }
    }

    List<TestSuite> findTssByNetworkServiceUUid(String uuid) {
        def tagHelperList = [] as ArrayList
        def tss  = [] as ArrayList
        findNetworkService(uuid)?.nsd.testingTags?.each { tag ->
            if(!tagHelperList.contains(tag)) {
                findTssByTestTag(tag)?.each { ts ->
                    if(!tss.contains(ts)){
                        tss << ts
                    }
                }
                tagHelperList << tag
            }
        }
        tss
    }

    List<NetworkService> findNssByTestSuiteUuid(String uuid){
        def tagHelperList = [] as ArrayList
        def nss  = [] as ArrayList
        def scannedByTag
        findTestSuite(uuid)?.testd.testExecution?.each { tag ->
            if(!tag.testTag.isEmpty() && !(tagHelperList.contains(tag.testTag))) {
                findNssByTestTag(tag.testTag)?.each { ns ->
                    if(!nss.contains(ns))
                        nss << ns
                }
                scannedByTag = true
                if(!(tagHelperList.join(",").contains(tag.testTag)))
                    tagHelperList << tag.testTag
            }
        }
        nss
    }

    //todo: this is a workaround solution to bypass the null packageId issue for test's
    //todo-y2: remove the packageId from all the TestSuite,TestPlan,TestResult
    def findPackageId(TestSuite testSuite){
        callExternalEndpoint(restTemplateWithAuth.getForEntity(packageListEndpoint, Object[]),
                'TestCatalogue.findPackageId', packageListEndpoint).body?.find { p ->
            p.pd.package_content?.find { pc ->
                pc.get('content-type') == "application/vnd.5gtango.tstd"
            }?.get("uuid") == testSuite.testUuid
        }?.get("uuid")
    }
}
