package eu.h2020_5gtango.vnv.lcm.restclient

import eu.h2020_5gtango.vnv.lcm.model.VnvTestPlan
import org.springframework.stereotype.Component

@Component
class TestExecutionEngine {
    void executeTests(VnvTestPlan vnvTestPlan) {
        boolean success = true
        vnvTestPlan.vnvTests.each { vnvTest ->
            def testSuccess = true//TODO: run single test
            success = success && testSuccess
        }
        vnvTestPlan.status = success ? 'SUCCESS' : 'FAILED'
    }
}
