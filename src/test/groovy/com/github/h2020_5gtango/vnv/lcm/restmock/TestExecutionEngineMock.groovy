package com.github.h2020_5gtango.vnv.lcm.restmock

import com.github.h2020_5gtango.vnv.lcm.model.TestSuiteResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestExecutionEngineMock {

    Map<String, TestSuiteResult> testSuiteResults = [:]

    void reset() {
        testSuiteResults.clear()
    }

    @PostMapping('/mock/tee/test-suite-results')
    TestSuiteResult executeTestAgainstNs(@RequestBody TestSuiteResult testSuiteResult) {
        testSuiteResult.testSuiteResultId = UUID.randomUUID().toString()
        testSuiteResult.status = 'SUCCESS'
        testSuiteResults[testSuiteResult.testSuiteResultId] = testSuiteResult
        testSuiteResult
    }

}
