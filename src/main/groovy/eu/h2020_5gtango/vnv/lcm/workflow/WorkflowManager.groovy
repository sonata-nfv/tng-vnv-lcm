package eu.h2020_5gtango.vnv.lcm.workflow

import eu.h2020_5gtango.vnv.lcm.model.NetworkService
import eu.h2020_5gtango.vnv.lcm.model.TestSuite
import eu.h2020_5gtango.vnv.lcm.model.TestPlan
import eu.h2020_5gtango.vnv.lcm.restclient.TestExecutionEngine
import eu.h2020_5gtango.vnv.lcm.restclient.TestPlatformManager
import eu.h2020_5gtango.vnv.lcm.restclient.TestResultRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WorkflowManager {

    @Autowired
    TestResultRepository testResultRepository

    @Autowired
    TestPlatformManager testPlatformManager

    @Autowired
    TestExecutionEngine testExecutionEngine

    void execute(NetworkService networkService, List<TestSuite> testSuites) {
        def testPlan = createTestPlan(networkService, testSuites)
        testPlan = deployNsForTest(testPlan)
        testPlan = executeTests(testPlan)
        destroyNsAfterTest(testPlan)
    }

    TestPlan createTestPlan(NetworkService networkService, List<TestSuite> testSuites) {
        def testPlan = new TestPlan(
                networkServices: [networkService],
                testSuites: testSuites,
                status: 'CREATED',
        )
        testResultRepository.createTestPlan(testPlan)
    }

    TestPlan deployNsForTest(TestPlan testPlan) {
        testPlan = testPlatformManager.deployNsForTest(testPlan)
        testResultRepository.updatePlan(testPlan)
        testPlan
    }

    TestPlan executeTests(TestPlan testPlan) {
        testPlan = testExecutionEngine.executeTests(testPlan)
        testResultRepository.updatePlan(testPlan)
        testPlan
    }

    TestPlan destroyNsAfterTest(TestPlan testPlan) {
        testPlan = testPlatformManager.destroyNsAfterTest(testPlan)
        testPlan
    }

}
