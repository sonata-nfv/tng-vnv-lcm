package eu.h2020_5gtango.vnv.lcm.restmock

import eu.h2020_5gtango.vnv.lcm.model.TestPlan
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestResultRepositoryMock {

    Map<String,TestPlan> testPlans=[:]

    void reset(){
        testPlans.clear()
    }

    @PostMapping('/mock/trr/plans')
    TestPlan createTestPlan(@RequestBody TestPlan testPlan) {
        testPlan.testPlanId="TEST_PLAN_ID_${System.currentTimeMillis()}"
        testPlans[testPlan.testPlanId]=testPlan
        testPlan
    }

    @PostMapping('/mock/trr/plans/{testPlanId:.+}')
    void updatePlanStatus(@RequestBody TestPlan testPlan, @PathVariable('testPlanId') String testPlanId) {
        testPlans[testPlanId].status=testPlan.status
    }

}
