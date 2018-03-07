package eu.h2020_5gtango.vnv.lcm.restclient

import eu.h2020_5gtango.vnv.lcm.model.TestSuite
import eu.h2020_5gtango.vnv.lcm.model.TestPlan
import eu.h2020_5gtango.vnv.lcm.model.TestSuiteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestExecutionEngine {

    @Autowired
    RestTemplate restTemplate

    @Value('${app.tee.suite.execute.endpoint}')
    def suiteExecuteEndpoint

    TestPlan executeTests(TestPlan testPlan) {
        def planStatus = 'SUCCESS'
        testPlan.testSuites.each { testSuite ->
            TestSuiteResult result = restTemplate.postForEntity(suiteExecuteEndpoint, testSuite, TestSuiteResult,testPlan.testPlanId, testPlan.networkServices.first().networkServiceId, testSuite.testSuiteId).body
            planStatus = planStatus == 'SUCCESS' ? result.status : planStatus
        }
        testPlan.status = planStatus
        testPlan
    }
}
