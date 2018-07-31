package com.github.h2020_5gtango.vnv.lcm.restmock

import com.github.h2020_5gtango.vnv.lcm.model.TestPlan
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
        testPlan.uuid = UUID.randomUUID().toString()
        testPlans[testPlan.uuid] = testPlan
    }

    @PutMapping('/mock/trr/test-plans/{testPlanId:.+}')
    TestPlan updatePlan(@RequestBody TestPlan testPlan, @PathVariable('testPlanId') String testPlanId) {
         testPlan.uuid = testPlanId
        testPlans[testPlan.uuid] = testPlan
    }

}
