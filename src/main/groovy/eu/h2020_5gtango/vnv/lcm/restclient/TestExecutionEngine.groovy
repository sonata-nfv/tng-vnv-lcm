package eu.h2020_5gtango.vnv.lcm.restclient

import eu.h2020_5gtango.vnv.lcm.model.TestSuite
import eu.h2020_5gtango.vnv.lcm.model.TestPlan
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
            TestSuite result = restTemplate.postForEntity(suiteExecuteEndpoint, testSuite, TestSuite,testPlan.testPlanId, testPlan.networkServices.first().generateId(), testSuite.generateId()).body
            planStatus = planStatus == 'SUCCESS' ? result.status : planStatus
        }
        testPlan.status = planStatus
        testPlan
    }
}
