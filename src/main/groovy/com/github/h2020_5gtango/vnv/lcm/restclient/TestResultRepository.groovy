package com.github.h2020_5gtango.vnv.lcm.restclient

import com.github.h2020_5gtango.vnv.lcm.model.TestPlan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestResultRepository {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Value('${app.trr.test.plan.create.endpoint}')
    def testPlanCreateEndpoint

    @Value('${app.trr.test.plan.update.endpoint}')
    def testPlanUpdateEndpoint

    TestPlan createTestPlan(TestPlan testPlan) {
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def entity = new HttpEntity<TestPlan>(testPlan ,headers)
        restTemplate.postForEntity(testPlanCreateEndpoint,entity,TestPlan).body
    }

    TestPlan updatePlan(TestPlan testPlan) {
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def entity = new HttpEntity<TestPlan>(testPlan ,headers)
        restTemplate.exchange(testPlanUpdateEndpoint, HttpMethod.PUT, entity, TestPlan.class ,testPlan.uuid).body
    }
}
