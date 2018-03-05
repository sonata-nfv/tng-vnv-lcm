package eu.h2020_5gtango.vnv.lcm.restmock

import eu.h2020_5gtango.vnv.lcm.model.NetworkService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestPlatformManagerMock {

    Map<String, NetworkService> networkServices = [:]

    void reset() {
        networkServices.clear()
    }

    @PostMapping('/mock/tpm/test-plans/{testPlanId}/network-services/{networkServiceId}')
    NetworkService deployNsForTest(@RequestBody NetworkService networkService, @PathVariable('testPlanId') String testPlanId, @PathVariable('networkServiceId') String networkServiceId) {
        networkServices[testPlanId + ':' + networkServiceId] = networkService
        networkService.status='RUNNING'
        networkService
    }

    @DeleteMapping('/mock/tpm/test-plans/{testPlanId}/network-services/{networkServiceId}')
    void destroyNsForTest(@PathVariable('testPlanId') String testPlanId, @PathVariable('networkServiceId') String networkServiceId) {
        networkServices[testPlanId + ':' + networkServiceId].status='STOPPED'
    }

}
