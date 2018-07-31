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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestCatalogue {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplateWithAuth

    @Autowired
    @Qualifier('restTemplateWithoutAuth')
    RestTemplate restTemplate

    @Value('${app.cat.package.metadata.endpoint}')
    def packageMetadataEndpoint

    @Value('${app.cat.test.metadata.endpoint}')
    def testMetadataEndpoint

    @Value('${app.cat.test.list.endpoint}')
    def testListEndpoint

    @Value('${app.cat.service.metadata.endpoint}')
    def serviceMetadataEndpoint

    @Value('${app.cat.service.list.endpoint}')
    def serviceListEndpoint


    PackageMetadata loadPackageMetadata(String packageId) {
        def rawPackageMetadata=restTemplate.getForEntity(packageMetadataEndpoint,Object.class,packageId).body
        PackageMetadata packageMetadata=new PackageMetadata(packageId: packageId)
        rawPackageMetadata.pd?.package_content.each{resource->
            switch (resource.get('content-type')) {
                case 'application/vnd.5gtango.tstd':
                    packageMetadata.testSuites << restTemplateWithAuth.getForEntity(testMetadataEndpoint, TestSuite.class, resource.uuid).body
                    break
                case 'application/vnd.5gtango.nsd':
                    packageMetadata.networkServices << restTemplateWithAuth.getForEntity(serviceMetadataEndpoint, NetworkService.class, resource.uuid).body
                    break
            }
        }
        packageMetadata
    }

    NetworkService findNetworkService(String networkServiceId) {
        restTemplateWithAuth.getForEntity(serviceMetadataEndpoint, NetworkService, networkServiceId).body
    }

    TestSuite findTestSuite(String testUuid) {
        restTemplateWithAuth.getForEntity(testMetadataEndpoint, TestSuite, testUuid).body
    }


    List<NetworkService> findNssByTestTag(String tag) {
        List filtered = []
        List<NetworkService> nss = restTemplateWithAuth.getForEntity(serviceListEndpoint, NetworkService[]).body
        nss.each { ns ->
            if(ns.testingTags.contains(tag))
                filtered << ns
        }
        filtered
    }


    List<TestSuite> findTssByTestTag(String tag) {
        List filtered = []
        List<TestSuite> tss  = restTemplateWithAuth.getForEntity(testListEndpoint, TestSuite[]).body
                tss.each { ts ->
                    List<TestTag> tt = ts.testExecution
                    tt?.each {
                        it -> if(it.testTag == tag)
                            filtered << ts
                    }
                }
        filtered
    }

}
