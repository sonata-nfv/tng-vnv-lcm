package com.github.h2020_5gtango.vnv.lcm.scheduler

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.restmock.TestCatalogueMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestExecutionEngineMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestPlatformManagerMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import junit.framework.TestSuite
import org.springframework.beans.factory.annotation.Autowired

class TestSuiteControllerTest extends AbstractSpec {

    final def TEST_SUITE_ID='input0ts-75f5-4ca1-90c8-12ec80a79821'
    @Autowired
    TestPlatformManagerMock testPlatformManagerMock

    @Autowired
    TestExecutionEngineMock testExecutionEngineMock

    @Autowired
    TestCatalogueMock testCatalogueMock

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void "schedule single TestSuite should produce successfully 1 Result for 1 testPlan"() {

        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/schedulers/tests',
                ["test_uuid": TEST_SUITE_ID]
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

    void "retrieval of a single network service's related NetworkServices should successfully all the tag related services "() {
        when:
        List nss = getForEntity('/tng-vnv-lcm/api/v1/schedulers/tests/{testUuid}/services', List,TEST_SUITE_ID).body
        then:

        nss.size() == 3

        cleanup:
        testPlatformManagerMock.reset()

    }
}
