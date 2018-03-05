package eu.h2020_5gtango.vnv.lcm.restmock

import eu.h2020_5gtango.vnv.lcm.model.TestSuite
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestExecutionEngineMock {

    Map<String, TestSuite> testSuites = [:]

    void reset() {
        testSuites.clear()
    }

    @PostMapping('/mock/tee/test-plans/{testPlanId}/network-services/{networkServiceId}/test-suites/{testSuiteId}')
    TestSuite executeTestAgainstNs(@RequestBody TestSuite testSuite, @PathVariable('testPlanId') String testPlanId, @PathVariable('networkServiceId') String networkServiceId, @PathVariable('testSuiteId') String testSuiteId) {
        testSuite.status='SUCCESS'
        testSuites[testPlanId + ':' + networkServiceId+ ':' + testSuiteId] = testSuite
        testSuite
    }

}
