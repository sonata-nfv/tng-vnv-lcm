package com.github.h2020_5gtango.vnv.lcm.scheduler

import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import com.github.h2020_5gtango.vnv.lcm.restmock.DataMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestCatalogueMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestExecutionEngineMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestPlatformManagerMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import org.springframework.beans.factory.annotation.Autowired
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
    TestCatalogueMock testCatelogogueMock

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void 'schedule multiple test plans should produce success result'() {

        when:
        scheduler.scheduleTests(MULTIPLE_TEST_PLANS_PACKAGE_ID)

        then:
        testPlatformManagerMock.networkServiceInstances.size()==2
//        testPlatformManagerMock.networkServiceInstances.values().last().status=='TERMINATED'

        testExecutionEngineMock.testSuiteResults.size()==4
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==2
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().each{testPlan->
            testPlan.testSuiteResults.size()==2
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
        testResultRepositoryMock.testPlans.values().each{testPlan->
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
        testResultRepositoryMock.testPlans.values().each{testPlan->
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
        testResultRepositoryMock.testPlans.values().each{testPlan->
            testPlan.testSuiteResults.size()==4
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

    }

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
        testResultRepositoryMock.testPlans.values().each{testPlan->
            testPlan.testSuiteResults.size()==4
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

    }
}