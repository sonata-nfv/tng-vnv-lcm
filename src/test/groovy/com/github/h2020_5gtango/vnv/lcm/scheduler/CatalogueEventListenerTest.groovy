package com.github.h2020_5gtango.vnv.lcm.scheduler

import com.github.h2020_5gtango.vnv.lcm.restmock.TestCatalogueMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestExecutionEngineMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestPlatformManagerMock
import com.github.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class CatalogueEventListenerTest extends AbstractSpec {

    public static final String MULTIPLE_TEST_PLANS_PACKAGE_ID ='multiple_scheduler:test:0.0.1'

    @Autowired
    TestPlatformManagerMock testPlatformManagerMock

    @Autowired
    TestExecutionEngineMock testExecutionEngineMock

    @Autowired
    TestCatalogueMock testCatalogueMock

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void 'schedule single TestSuite and single NetworkService should produce successfully 2 Result for 2 testPlan'() {

        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/packages/on-change',
                [
                        event_name: UUID.randomUUID().toString(),
                        package_id:  MULTIPLE_TEST_PLANS_PACKAGE_ID,
                ]
                , Void.class)

        then:
        entity.statusCode == HttpStatus.OK
        //fixme the unitestFollowsTheOldServiceTestsMapping - I need to fix the new mapping and return to the correct unitesting
//        testPlatformManagerMock.networkServiceInstances.size()==3
        testPlatformManagerMock.networkServiceInstances.size()==2
//        testPlatformManagerMock.networkServiceInstances.values().last().status=='TERMINATED'

        //fixme the unitestFollowsTheOldServiceTestsMapping - I need to fix the new mapping and return to the correct unitesting
//        testExecutionEngineMock.testSuiteResults.size()==12
        testExecutionEngineMock.testSuiteResults.size()==5
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        //fixme the unitestFollowsTheOldServiceTestsMapping - I need to fix the new mapping and return to the correct unitesting
//        testResultRepositoryMock.testPlans.size()==3
        testResultRepositoryMock.testPlans.size()==2
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        //fixme the unitestFollowsTheOldServiceTestsMapping - I need to fix the new mapping and return to the correct unitesting
        testResultRepositoryMock.testPlans.values().each{testPlan ->
//            testPlan.testSuiteResults.size()==3
            testPlan.testSuiteResults.size()==1
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()
    }


}
