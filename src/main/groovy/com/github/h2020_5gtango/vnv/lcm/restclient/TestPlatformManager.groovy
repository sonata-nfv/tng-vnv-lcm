package com.github.h2020_5gtango.vnv.lcm.restclient

import com.github.h2020_5gtango.vnv.lcm.model.NetworkServiceInstance
import com.github.h2020_5gtango.vnv.lcm.model.TestPlan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestPlatformManager {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Value('${app.tpm.ns.deploy.endpoint}')
    def nsDeployEndpoint

    @Value('${app.tpm.ns.destroy.endpoint}')
    def nsDestroyEndpoint

    TestPlan deployNsForTest(TestPlan testPlan) {
        NetworkServiceInstance networkServiceInstance=restTemplate.postForEntity(nsDeployEndpoint,null,NetworkServiceInstance,testPlan.networkServiceInstances.first().networkServiceId).body
        testPlan.networkServiceInstances=testPlan.networkServiceInstances.collect{nsi->
            nsi.networkServiceId==networkServiceInstance.networkServiceId?networkServiceInstance:nsi
        }
        testPlan
    }

    TestPlan destroyNsAfterTest(TestPlan testPlan) {
        restTemplate.delete(nsDestroyEndpoint,testPlan.networkServiceInstances.first().networkServiceInstanceId)
        testPlan.networkServiceInstances.first().status='DESTROYED'
        testPlan
    }
}
