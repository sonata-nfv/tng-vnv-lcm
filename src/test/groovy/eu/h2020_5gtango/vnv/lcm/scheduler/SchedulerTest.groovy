package eu.h2020_5gtango.vnv.lcm.scheduler

import eu.h2020_5gtango.vnv.lcm.AbstractSpec
import eu.h2020_5gtango.vnv.lcm.restmock.TestExecutionEngineMock
import eu.h2020_5gtango.vnv.lcm.restmock.TestPlatformManagerMock
import eu.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import org.springframework.beans.factory.annotation.Autowired

class SchedulerTest extends AbstractSpec {

    public static final String SINGLE_TEST_PLAN_PACKAGE_ID='single_scheduler:test:0.0.1'
    public static final String MULTIPLE_TEST_PLANS_PACKAGE_ID ='multiple_scheduler:test:0.0.1'

    @Autowired
    Scheduler scheduler

    @Autowired
    TestPlatformManagerMock testPlatformManagerMock

    @Autowired
    TestExecutionEngineMock testExecutionEngineMock

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock


    void "schedule single test plan should produce success result"() {
        given:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

        when:
        scheduler.scheduleTests(SINGLE_TEST_PLAN_PACKAGE_ID)

        then:
        testPlatformManagerMock.networkServices.size()==1
        testPlatformManagerMock.networkServices.values().last().name=='name'
        testPlatformManagerMock.networkServices.values().last().status=='STOPPED'

        testExecutionEngineMock.testSuiteResults.size()==1
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==1
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServices.size()==1
        testResultRepositoryMock.testPlans.values().last().networkServices.last().name=='name'
        testResultRepositoryMock.testPlans.values().last().testSuites.size()==1
        testResultRepositoryMock.testPlans.values().last().testSuites.last().name=='name'
    }


    void "schedule multiple test plans should produce success result"() {
        given:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

        when:
        scheduler.scheduleTests(MULTIPLE_TEST_PLANS_PACKAGE_ID)

        then:
        testPlatformManagerMock.networkServices.size()==4
        testPlatformManagerMock.networkServices.values().last().name=='multiple_ns_4'
        testPlatformManagerMock.networkServices.values().last().status=='STOPPED'

        testExecutionEngineMock.testSuiteResults.size()==8
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==4
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServices.size()==1
        testResultRepositoryMock.testPlans.values().last().networkServices.last().name=='multiple_ns_4'
        testResultRepositoryMock.testPlans.values().each{testPlan->
            testPlan.testSuites.size()==2
        }
        testResultRepositoryMock.testPlans.values().last().testSuites.last().name=='multiple_test_2'
    }

}