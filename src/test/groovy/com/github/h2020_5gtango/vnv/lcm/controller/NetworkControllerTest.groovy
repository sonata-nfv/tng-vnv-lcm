package com.github.h2020_5gtango.vnv.lcm.controller

import com.github.h2020_5gtango.vnv.lcm.restmock.TestCatalogueMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestExecutionEngineMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestPlatformManagerMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import junit.framework.TestSuite
import org.springframework.beans.factory.annotation.Autowired
    import spock.lang.Ignore

class NetworkControllerTest extends AbstractSpec {

    final def NETWORK_SERVICE_ID = 'input0ns-f213-4fae-8d3f-04358e1e1445'

    final def NETWORK_SERVICE_HTTP_ADVANCED_ID='d07742ed-9429-4a07-b7af-d0b24a6d5c4c'
    final def TEST_HTTP_ADVANCED_ID='aa5c779a-8cc7-47a9-9112-d2ff348898b4'
    final def PACKAGE_OF_TEST_HTTP_ADVANCED_ID='a3acb16d-c314-4122-9b3d-9c180547d580'

    @Autowired
    TestPlatformManagerMock testPlatformManagerMock

    @Autowired
    TestExecutionEngineMock testExecutionEngineMock

    @Autowired
    TestCatalogueMock testCatalogueMock

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void "schedule single NetworkService should produce successfully 1 Result for 1 testPlan"() {

        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/schedulers/services',
                ["service_uuid": NETWORK_SERVICE_ID]
                , Void.class)


        then:
        testPlatformManagerMock.networkServiceInstances.size()==1

        testExecutionEngineMock.testSuiteResults.size()==1
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==1
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().each{testPlan ->
            testPlan.testSuiteResults.size()==1
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()
    }

    void "retrieval of a single test suite's related testSuites should successfully all the tag related tests"() {
        when:
        List tss = getForEntity('/tng-vnv-lcm/api/v1/schedulers/services/{serviceUuid}/tests', List, NETWORK_SERVICE_ID).body
        then:

        tss.size() == 1
        cleanup:
        testPlatformManagerMock.reset()

    }

    void "schedule Service GOBETWEEN with tag: http-advanced should run once with TEST-HTTP-BENCHMARK-ADVANCED with 1 Result and 1 testPlan"() {

        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/schedulers/services',
                ["service_uuid": NETWORK_SERVICE_HTTP_ADVANCED_ID]
                , Void.class)

        then:
        testPlatformManagerMock.networkServiceInstances.size()==1
        testPlatformManagerMock.networkServiceInstances.values().last().status=='TERMINATED'

        testExecutionEngineMock.testSuiteResults.size()==1
        testExecutionEngineMock.testSuiteResults.values().testUuid[0] == TEST_HTTP_ADVANCED_ID
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==1
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.packageId[0]==PACKAGE_OF_TEST_HTTP_ADVANCED_ID
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()
    }

    @Ignore
    void "when one service is related with one test should run only once"() {

        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/schedulers/services',
                ["service_uuid": NETWORK_SERVICE_HTTP_ADVANCED_ID]
                , Void.class)

        then:
        testPlatformManagerMock.networkServiceInstances.size()==1

        testExecutionEngineMock.testSuiteResults.size()==1

        //fixme: how could num of executions be 11
        testExecutionEngineMock.numOfExecutions == 1
        //fixme: how could num of calls for the TestPlanCreation be 9
        testResultRepositoryMock.numOfCallsForTestPlanCreation == 1
        //fixme: how could num of calls for the TestPlanUpdate be 27
        testResultRepositoryMock.numOfCallsForTestPlanUpdate == 3

        testResultRepositoryMock.testPlans.size()==1

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()
    }

}