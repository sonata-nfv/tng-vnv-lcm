package eu.h2020_5gtango.vnv.lcm.restclient

import eu.h2020_5gtango.vnv.lcm.model.TestPlan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestResultRepository {

    @Autowired
    RestTemplate restTemplate

    @Value('${app.trr.test.plan.create.endpoint}')
    def testPlanCreateEndpoint

    @Value('${app.trr.test.plan.update.endpoint}')
    def testPlanUpdateEndpoint

    TestPlan createTestPlan(TestPlan testPlan) {
        restTemplate.postForEntity(testPlanCreateEndpoint,testPlan,TestPlan).body
    }

    void updatePlan(TestPlan testPlan) {
        restTemplate.postForEntity(testPlanUpdateEndpoint,testPlan,TestPlan,testPlan.testPlanId)
    }
}
