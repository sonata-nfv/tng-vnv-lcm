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

package com.github.h2020_5gtango.vnv.lcm.restmock

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCatalogueMock {

    private static String TEST_UUID='input0ts-75f5-4ca1-90c8-12ec80a79821'
    private static String SERVICE_UUID='input0ns-f213-4fae-8d3f-04358e1e1445'
    private static String MULTIPLE_TEST_PLANS_PACKAGE_ID ='multiple_scheduler:test:0.0.1'

    @GetMapping('/mock/gk/packages')
    def findPackages() {
        DataMock.packages
    }

    @GetMapping('/mock/gk/packages/{packageId:.+}')
    Map loadPackageMetadata(@PathVariable('packageId') String packageId) {
        if (packageId == MULTIPLE_TEST_PLANS_PACKAGE_ID) {
            [pd:[package_content:[
                    [
                            'uuid':TEST_UUID,
                            'content-type':'application/vnd.5gtango.tstd',
                    ],
                    [
                            'uuid':SERVICE_UUID,
                            'content-type':'application/vnd.5gtango.nsd',
                    ],
            ], test_type: 'fgh'],
            ]
        } else {
            DataMock.getPackage(packageId)
        }
    }

    @GetMapping('/mock/gk/services')
    def findServices() {
        DataMock.services
    }

    @GetMapping('/mock/gk/services/{networkServiceId:.+}')
    def findService(@PathVariable('networkServiceId') String networkServiceId) {
        DataMock.getService(networkServiceId)
    }

    @GetMapping('/mock/gk/tests/descriptors')
    def findTests() {
        DataMock.tests
    }

    @GetMapping('/mock/gk/tests/descriptors/{testUuid:.+}')
    def findTest(@PathVariable('testUuid') String testUuid) {
        DataMock.getTest(testUuid)
    }
}

