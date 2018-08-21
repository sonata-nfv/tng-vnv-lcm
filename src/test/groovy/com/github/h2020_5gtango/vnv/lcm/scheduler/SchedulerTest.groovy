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

import com.github.h2020_5gtango.vnv.lcm.event.CatalogueEventListener
import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import com.github.h2020_5gtango.vnv.lcm.restmock.DataMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestCatalogueMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestExecutionEngineMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestPlatformManagerMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import spock.lang.Ignore

class SchedulerTest extends AbstractSpec {

    public static final String MULTIPLE_TEST_PLANS_PACKAGE_ID ='multiple_scheduler:test:0.0.1'

    @Autowired
    Scheduler scheduler

    @Autowired
    TestPlatformManagerMock testPlatformManagerMock

    @Autowired
    TestExecutionEngineMock testExecutionEngineMock

    @Autowired
    TestCatalogueMock testCatalogueMock

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void 'schedule multiple test plans should produce success result'() {

        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/packages/on-change',
                [
                        event_name: UUID.randomUUID().toString(),
                        package_id:  MULTIPLE_TEST_PLANS_PACKAGE_ID,
                ]
                , Void.class)

//        scheduler.scheduleTests(MULTIPLE_TEST_PLANS_PACKAGE_ID)

        then:
        entity.statusCode == HttpStatus.OK
        testPlatformManagerMock.networkServiceInstances.size()==3
//        testPlatformManagerMock.networkServiceInstances.values().last().status=='TERMINATED'

        testExecutionEngineMock.testSuiteResults.size()==12
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==3
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().each{testPlan ->
            testPlan.testSuiteResults.size()==3
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()
    }


    @Ignore
    void "schedule single TestSuite and single NetworkService should produce success result and size one for all the numeric outputs"() {

        when:
        scheduler.scheduleTests(
                new PackageMetadata(
                        networkServices: [] + DataMock.singleNetworkService1,
                        testSuites: [] + DataMock.singleTestSuite1
                )
        )

        then:
        testPlatformManagerMock.networkServiceInstances.size()==2

        testExecutionEngineMock.testSuiteResults.size()==4
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==2
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().each{testPlan ->
            testPlan.testSuiteResults.size()==2
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

    }

    @Ignore
    void "schedule retrieve multiple TestSuites and single NetworkService should produce success result"() {

        when:
        scheduler.scheduleTests(
                new PackageMetadata(
                        networkServices: [] + DataMock.singleNetworkService,
                        testSuites: [] + DataMock.multipleTestSuites12

                )
        )

        then:
        testPlatformManagerMock.networkServiceInstances.size()==4

        testExecutionEngineMock.testSuiteResults.size()==16
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==4
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().each{testPlan ->
            testPlan.testSuiteResults.size()==4
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

    }

    @Ignore
    void "schedule retrieve multiple NetworkServices for a single TestSuite should produce success result"() {

        when:
        scheduler.scheduleTests(
                new PackageMetadata(
                        networkServices: [] + DataMock.allNetworkServices01234,
                        testSuites: [] + DataMock.singleTestSuite
                )
        )

        then:
        testPlatformManagerMock.networkServiceInstances.size()==4

        testExecutionEngineMock.testSuiteResults.size()==16
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==4
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().each{testPlan ->
            testPlan.testSuiteResults.size()==4
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

    }

    @Ignore
    void "schedule retrieval from multiple NetworkServices and multiple TestSuites should produce success result"() {

        when:
        scheduler.scheduleTests(
                new PackageMetadata(
                        networkServices: DataMock.allNetworkServices01234,
                        testSuites: DataMock.allTestSuites01234
                )
        )

        then:
        testPlatformManagerMock.networkServiceInstances.size()==4

        testExecutionEngineMock.testSuiteResults.size()==16
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==4
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().each{testPlan ->
            testPlan.testSuiteResults.size()==4
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

    }
}