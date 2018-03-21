package com.github.h2020_5gtango.vnv.lcm.scheduler


import com.github.h2020_5gtango.vnv.lcm.restmock.TestExecutionEngineMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestPlatformManagerMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
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
        testPlatformManagerMock.networkServiceInstances.size()==1
        testPlatformManagerMock.networkServiceInstances.values().last().status=='STOPPED'

        testExecutionEngineMock.testSuiteResults.size()==1
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==1
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.last().status=='DESTROYED'
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.size()==1
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'
    }


    void "schedule multiple test plans should produce success result"() {
        given:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

        when:
        scheduler.scheduleTests(MULTIPLE_TEST_PLANS_PACKAGE_ID)

        then:
        testPlatformManagerMock.networkServiceInstances.size()==4
        testPlatformManagerMock.networkServiceInstances.values().last().status=='STOPPED'

        testExecutionEngineMock.testSuiteResults.size()==8
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==4
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().each{testPlan->
            testPlan.testSuiteResults.size()==2
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'
    }

}