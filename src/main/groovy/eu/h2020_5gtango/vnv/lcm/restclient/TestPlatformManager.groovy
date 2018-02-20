package eu.h2020_5gtango.vnv.lcm.restclient

import eu.h2020_5gtango.vnv.lcm.model.VnvTestPlan
import org.springframework.stereotype.Component

@Component
class TestPlatformManager {
    void deployNsForTest(VnvTestPlan vnvTestPlan) {
        vnvTestPlan.status='NS_DEPLOYED'
    }
}
