package eu.h2020_5gtango.vnv.lcm.restclient

import eu.h2020_5gtango.vnv.lcm.model.PackageMetadata
import eu.h2020_5gtango.vnv.lcm.model.VnvTestPlan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestResultRepository {

    @Autowired
    RestTemplate restTemplate

    @Value('${app.store.test.plan.create.endpoint}')
    def testPlanCreateEndpoint

    @Value('${app.store.test.plan.update.endpoint}')
    def testPlanUpdateEndpoint

    VnvTestPlan createTestPlan(VnvTestPlan vnvTestPlan) {
        restTemplate.postForEntity(testPlanCreateEndpoint,vnvTestPlan,VnvTestPlan).body
    }

    void updatePlanStatus(VnvTestPlan vnvTestPlan) {
        restTemplate.postForEntity(testPlanUpdateEndpoint,vnvTestPlan,VnvTestPlan,vnvTestPlan.testPlanId)
    }
}
