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


import com.github.h2020_5gtango.vnv.lcm.scheduler.SchedulerTest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCatalogueMock {

    private static String TEST_UUID='unit-test-uuid'
    private static String SERVICE_UUID='network-service-uuid'

    @GetMapping('/mock/gk/packages')
    def findPackages() {
        DataMock.packages
    }

    @GetMapping('/mock/gk/packages/{packageId:.+}')
    Map loadPackageMetadata(@PathVariable('packageId') String packageId) {
        if (packageId == SchedulerTest.MULTIPLE_TEST_PLANS_PACKAGE_ID) {
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
            [package_id:packageId]
        }
    }

    @GetMapping('/mock/gk/services')
    def findServices() {
        [
                DataMock.singleNetworkService_input0nsJson,
                DataMock.singleNetworkService_4763bde6Json,
                DataMock.singleNetworkService_a0c112acJson,
                DataMock.singleNetworkService_f64a458cJson,

        ]
    }

    @GetMapping('/mock/gk/services/{networkServiceId:.+}')
    def findService(@PathVariable('networkServiceId') String networkServiceId) {
        def result
        switch (networkServiceId) {
            case SERVICE_UUID:
                result = DataMock.singleNetworkService_input0nsJson
                break

            case networkServiceId.contains('single_ns_0'):
                result = DataMock.singleNetworkService
                break
            case 'single_ns_1':
                result = DataMock.singleNetworkService1
                break
            case 'multiple_ns_2':
                result = DataMock.multipleNetworkService2
                break
            case 'multiple_ns_3':
                result = DataMock.multipleNetworkService3
                break
            case 'multiple_ns_4':
                result = DataMock.multipleNetworkService4
                break

            case ~/^input0ns.*/:
                result = DataMock.singleNetworkService_input0nsJson
                break
            case ~/^4763bde6.*/:
                result = DataMock.singleNetworkService_4763bde6Json
                break
            case ~/^a0c112ac.*/:
                result = DataMock.singleNetworkService_a0c112acJson
                break
            case ~/^f64a458c.*/:
                result = DataMock.singleNetworkService_f64a458cJson
                break
            default:
                result = DataMock.singleNetworkService_input0nsJson
        }
        result

    }

    @GetMapping('/mock/gk/tests/descriptors')
    def findTests() {
        [
                DataMock.singleTestSuite_input0tsJson,
                DataMock.singleTestSuite_9bbbd636Json,
                DataMock.singleTestSuite_ccbf8badJson,
                DataMock.singleTestSuite_fe7ec2a8Json,
        ]
    }

    @GetMapping('/mock/gk/tests/descriptors/{testUuid:.+}')
    def findTest(@PathVariable('testUuid') String testUuid) {
        def result
        switch (testUuid) {
            case TEST_UUID:
                result = DataMock.singleTestSuite_input0tsJson
                break
            case 'single_test_0':
                result = DataMock.singleTestSuite
                break
            case 'single_test_1':
                result = DataMock.singleTestSuite1
                break
            case 'multiple_test_2':
                result = DataMock.multipleTestSuite2
                break
            case 'multiple_test_3':
                result = DataMock.multipleTestSuite3
                break
            case 'multiple_test_4':
                result = DataMock.multipleTestSuite4
                break
            case ~/^input0ts.*/:
                result = DataMock.singleTestSuite_input0tsJson
                break
            case ~/^9bbbd636.*/:
                result = DataMock.singleTestSuite_9bbbd636Json
                break
            case ~/^ccbf8bad.*/:
                result = DataMock.singleTestSuite_ccbf8badJson
                break
            case ~/^fe7ec2a8.*/:
                result = DataMock.singleTestSuite_fe7ec2a8Json
                break
            default:
                result = DataMock.singleTestSuite_input0tsJson
        }
        result
    }
}

