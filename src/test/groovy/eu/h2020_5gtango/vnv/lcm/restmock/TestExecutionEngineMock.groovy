package eu.h2020_5gtango.vnv.lcm.restmock

import eu.h2020_5gtango.vnv.lcm.model.TestSuiteResult
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

import java.security.MessageDigest

@RestController
class TestExecutionEngineMock {

    Map<String, TestSuiteResult> testSuiteResults = [:]

    void reset() {
        testSuiteResults.clear()
    }

    @PostMapping('/mock/tee/test-plans/{testPlanId}/network-services/{networkServiceId}/test-suites/{testSuiteId}/execute')
    TestSuiteResult executeTestAgainstNs(
            @PathVariable('testPlanId') String testPlanId,
            @PathVariable('networkServiceId') String networkServiceId,
            @PathVariable('testSuiteId') String testSuiteId) {
        def result = new TestSuiteResult(
                testPlanId: testPlanId,
                networkServiceId: networkServiceId,
                testSuiteId: testSuiteId,
                status: 'SUCCESS',
        )
        result.testSuiteResultId=MessageDigest.getInstance("MD5").digest("$result.testPlanId:$result.networkServiceId:$result.testSuiteId".bytes).encodeHex().toString()

        result.status = 'SUCCESS'
        testSuiteResults[result.testSuiteResultId] = result
        result
    }

}
