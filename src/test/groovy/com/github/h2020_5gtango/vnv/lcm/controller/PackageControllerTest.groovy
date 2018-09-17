package com.github.h2020_5gtango.vnv.lcm.controller

import com.github.h2020_5gtango.vnv.lcm.restmock.TestCatalogueMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestExecutionEngineMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestPlatformManagerMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import org.springframework.beans.factory.annotation.Autowired

class PackageControllerTest extends AbstractSpec {

    @Autowired
    TestPlatformManagerMock testPlatformManagerMock

    @Autowired
    TestExecutionEngineMock testExecutionEngineMock

    @Autowired
    TestCatalogueMock testCatalogueMock

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void "schedule single Package with 1 NetworkService and 2 TestSuite should produce successfully 1 Result for 1 testPlan"() {

        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/schedulers',
                [
                        "uuid": "input0pa-75f5-4231-90c8-12ec80a7oi97",
                        "network_services":
                                [
                                        ["uuid": "input0ns-f213-4fae-8d3f-04358e1e1445"],
                                ],

                        "test_suites":
                                [
                                        ["uuid": "input0ts-75f5-4ca1-90c8-12ec80a79821"],
                                        ["uuid": "9bbbd636-75f5-4ca1-90c8-12ec80a79821"],
                                ]

                ]
                , Void.class)

        then:
        Thread.sleep(10000L);
        while (testPlatformManagerMock.networkServiceInstances.values().last().status!='TERMINATED')
            Thread.sleep(1000L);
        testPlatformManagerMock.networkServiceInstances.size()==3

        testExecutionEngineMock.testSuiteResults.size()==5
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==3
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

    }

}
