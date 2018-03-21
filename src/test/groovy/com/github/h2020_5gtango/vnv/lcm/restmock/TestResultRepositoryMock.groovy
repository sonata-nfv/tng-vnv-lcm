package com.github.h2020_5gtango.vnv.lcm.restmock

import com.github.h2020_5gtango.vnv.lcm.model.TestPlan
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestResultRepositoryMock {

    Map<String, TestPlan> testPlans = [:]

    void reset() {
        testPlans.clear()
    }

    @PostMapping('/mock/trr/test-plans')
    TestPlan createTestPlan(@RequestBody TestPlan testPlan) {
        testPlan.testPlanId = UUID.randomUUID().toString()
        testPlans[testPlan.testPlanId] = testPlan
    }

    @PostMapping('/mock/trr/test-plans/{testPlanId:.+}')
    TestPlan updatePlan(@RequestBody TestPlan testPlan, @PathVariable('testPlanId') String testPlanId) {
        testPlan.testPlanId = testPlanId
        testPlans[testPlan.testPlanId] = testPlan
    }

}
