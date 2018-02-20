package eu.h2020_5gtango.vnv.lcm.restmock

import eu.h2020_5gtango.vnv.lcm.model.NetworkService
import eu.h2020_5gtango.vnv.lcm.model.PackageMetadata
import eu.h2020_5gtango.vnv.lcm.model.VnvTest
import eu.h2020_5gtango.vnv.lcm.model.VnvTestPlan
import org.aspectj.lang.JoinPoint
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestResultRepositoryMock {

    Map<String,VnvTestPlan> testPlans=[:]

    void reset(){
        testPlans.clear()
    }

    @PostMapping('/mock/store/test-plans')
    VnvTestPlan createTestPlan(@RequestBody VnvTestPlan vnvTestPlan) {
        vnvTestPlan.testPlanId="TEST_PLAN_ID_${System.currentTimeMillis()}"
        vnvTestPlan.status='CREATED'
        testPlans[vnvTestPlan.testPlanId]=vnvTestPlan
        vnvTestPlan
    }

    @PostMapping('/mock/store/test-plans/{testPlanId:.+}')
    void updatePlanStatus(@RequestBody VnvTestPlan vnvTestPlan,@PathVariable('testPlanId') String testPlanId) {
        testPlans[testPlanId].status=vnvTestPlan.status
    }

}
