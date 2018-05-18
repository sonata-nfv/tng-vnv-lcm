package com.github.h2020_5gtango.vnv.lcm.restclient

import com.github.h2020_5gtango.vnv.lcm.model.NsRequest
import com.github.h2020_5gtango.vnv.lcm.model.NsResponse
import com.github.h2020_5gtango.vnv.lcm.model.TestPlan
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@Log
class TestPlatformManager {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Value('${app.tpm.ns.deploy.endpoint}')
    def nsDeployEndpoint

    @Value('${app.tpm.ns.status.endpoint}')
    def nsStatusEndpoint

    @Value('${app.tpm.ns.status.timeout.in.seconds}')
    int nsStatusTimeoutInSeconds

    @Value('${app.tpm.ns.destroy.endpoint}')
    def nsDestroyEndpoint

    TestPlan deployNsForTest(TestPlan testPlan) {
        def createRequest = new NsRequest(
                serviceUuid: testPlan.networkServiceInstances.first().serviceUuid,
                requestType: 'CREATE',
        )
        NsResponse response = restTemplate.postForEntity(nsDeployEndpoint, createRequest, NsResponse).body
        for (int i = 0; i < nsStatusTimeoutInSeconds; i++) {
            if (['ERROR', 'READY'].contains(response.status)) {
                break
            }
            response = restTemplate.getForEntity(nsStatusEndpoint, NsResponse, response.id).body
            Thread.sleep(1000)
        }

        testPlan.networkServiceInstances.first().status = response.status
        if (response.status == 'READY') {
            testPlan.networkServiceInstances.first().serviceInstanceUuid = response.serviceInstanceUuid
            testPlan.status = 'NS_DEPLOYED'
        } else {
            log.warning("Deploy NS failed with status $response.status")
            testPlan.status = 'NS_DEPLOY_FAILED'
        }
        testPlan
    }

    TestPlan destroyNsAfterTest(TestPlan testPlan) {
        def terminateRequest =  new NsRequest(
                serviceInstanceUuid: testPlan.networkServiceInstances.first().serviceInstanceUuid,
                requestType         : 'TERMINATE',
        )
        NsResponse response = restTemplate.postForEntity(nsDestroyEndpoint, terminateRequest, NsResponse).body
        testPlan.networkServiceInstances.first().status = 'TERMINATED'
        testPlan
    }
}
