package eu.h2020_5gtango.vnv.lcm.scheduler

import eu.h2020_5gtango.vnv.lcm.AbstractSpec
import eu.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import org.springframework.beans.factory.annotation.Autowired

class SchedulerTest extends AbstractSpec {

    public static final String SINGLE_TEST_PLAN_PACKAGE_ID='single_scheduler:test:0.0.1'
    public static final String MULTIPLE_TEST_PLANS_PACKAGE_ID ='multiple_scheduler:test:0.0.1'

    @Autowired
    Scheduler scheduler

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock


    void "schedule single test plan should produce success result"() {
        given:
        testResultRepositoryMock.reset()

        when:
        scheduler.scheduleTests(SINGLE_TEST_PLAN_PACKAGE_ID)

        then:
        testResultRepositoryMock.testPlans.size()==1
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServices.size()==1
        testResultRepositoryMock.testPlans.values().last().networkServices.last().name=='name'
        testResultRepositoryMock.testPlans.values().last().vnvTests.size()==1
        testResultRepositoryMock.testPlans.values().last().vnvTests.last().name=='name'
    }


    void "schedule multiple test plans should produce success result"() {
        given:
        testResultRepositoryMock.reset()

        when:
        scheduler.scheduleTests(MULTIPLE_TEST_PLANS_PACKAGE_ID)

        then:
        testResultRepositoryMock.testPlans.size()==4
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServices.size()==1
        testResultRepositoryMock.testPlans.values().last().networkServices.last().name=='multiple_ns_4'
        testResultRepositoryMock.testPlans.values().each{testPlan->
            testPlan.vnvTests.size()==2
        }
        testResultRepositoryMock.testPlans.values().last().vnvTests.last().name=='multiple_test_2'
    }

}