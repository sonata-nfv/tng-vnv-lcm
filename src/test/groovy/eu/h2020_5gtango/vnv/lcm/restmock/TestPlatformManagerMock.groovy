package eu.h2020_5gtango.vnv.lcm.restmock

import eu.h2020_5gtango.vnv.lcm.model.NetworkServiceInstance
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestPlatformManagerMock {

    Map<String, NetworkServiceInstance> networkServiceInstances = [:]

    void reset() {
        networkServiceInstances.clear()
    }

    @PostMapping('/mock/tpm/network-service-instances')
    NetworkServiceInstance deployNsForTest(@RequestParam('networkServiceId') String networkServiceId) {
        def networkServiceInstance = new NetworkServiceInstance(
                networkServiceInstanceId: UUID.randomUUID().toString(),
                networkServiceId: networkServiceId,
                status: 'RUNNING',
        )
        networkServiceInstances[networkServiceInstance.networkServiceInstanceId] = networkServiceInstance
        networkServiceInstance
    }

    @DeleteMapping('/mock/tpm/network-service-instances/{networkServiceInstanceId}')
    void destroyNsForTest(@PathVariable('networkServiceInstanceId') String networkServiceInstanceId) {
        networkServiceInstances[networkServiceInstanceId].status='STOPPED'
    }

}
