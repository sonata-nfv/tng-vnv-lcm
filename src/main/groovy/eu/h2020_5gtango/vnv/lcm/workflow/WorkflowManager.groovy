package eu.h2020_5gtango.vnv.lcm.workflow

import eu.h2020_5gtango.vnv.lcm.model.NetworkService
import eu.h2020_5gtango.vnv.lcm.model.VnvTest
import eu.h2020_5gtango.vnv.lcm.model.VnvTestPlan
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

    void execute(NetworkService networkService, List<VnvTest> vnvTests) {
        def vnvTestPlan = createTestPlan(networkService, vnvTests)
        deployNsForTests(vnvTestPlan)
        executeTests(vnvTestPlan)
    }

    VnvTestPlan createTestPlan(NetworkService networkService, List<VnvTest> vnvTests) {
        def vnvTestPlan = new VnvTestPlan(
                networkServices: [networkService],
                vnvTests: vnvTests,
        )
        testResultRepository.createTestPlan(vnvTestPlan)
    }

    void deployNsForTests(VnvTestPlan vnvTestPlan) {
        testPlatformManager.deployNsForTest(vnvTestPlan)
        testResultRepository.updatePlanStatus(vnvTestPlan)
    }

    void executeTests(VnvTestPlan vnvTestPlan) {
        testExecutionEngine.executeTests(vnvTestPlan)
        testResultRepository.updatePlanStatus(vnvTestPlan)
    }

}
