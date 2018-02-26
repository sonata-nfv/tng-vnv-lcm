package eu.h2020_5gtango.vnv.lcm.restclient

import eu.h2020_5gtango.vnv.lcm.model.TestPlan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestPlatformManager {

    @Autowired
    RestTemplate restTemplate

    @Value('${app.tpm.ns.deploy.endpoint}')
    def nsDeployEndpoint

    @Value('${app.tpm.ns.destroy.endpoint}')
    def nsDestroyEndpoint

    TestPlan deployNsForTest(TestPlan testPlan) {
        restTemplate.postForEntity(nsDeployEndpoint,testPlan.networkServices.first(),TestPlan,testPlan.testPlanId,testPlan.networkServices.first().generateId()).body
        testPlan.status='NS_DEPLOYED'
        testPlan
    }

    TestPlan destroyNsAfterTest(TestPlan testPlan) {
        restTemplate.delete(nsDestroyEndpoint,testPlan.testPlanId,testPlan.networkServices.first().generateId())
        testPlan
    }
}
