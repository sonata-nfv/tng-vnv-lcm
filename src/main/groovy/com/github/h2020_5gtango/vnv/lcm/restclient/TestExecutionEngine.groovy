package com.github.h2020_5gtango.vnv.lcm.restclient

import com.github.h2020_5gtango.vnv.lcm.model.TestPlan
import com.github.h2020_5gtango.vnv.lcm.model.TestSuiteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestExecutionEngine {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Value('${app.tee.suite.execute.endpoint}')
    def suiteExecuteEndpoint

    TestPlan executeTests(TestPlan testPlan) {
        def planStatus = 'SUCCESS'
        def results=[]
        testPlan.testSuiteResults.each { testSuiteResult ->
            testSuiteResult.testPlanId=testPlan.uuid
            testSuiteResult.networkServiceInstanceId=testPlan.networkServiceInstances.first().serviceInstanceUuid
            testSuiteResult = restTemplate.postForEntity(suiteExecuteEndpoint, testSuiteResult, TestSuiteResult).body
            planStatus = planStatus == 'SUCCESS' ? testSuiteResult.status : planStatus
            results << testSuiteResult
        }
        testPlan.testSuiteResults=results
        testPlan.status = planStatus
        testPlan
    }
}
