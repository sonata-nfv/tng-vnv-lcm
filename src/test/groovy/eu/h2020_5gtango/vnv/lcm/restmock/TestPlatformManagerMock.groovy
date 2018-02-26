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

    @PostMapping('/mock/tpm/plans/{testPlanId}/nss/{nsId}')
    NetworkService deployNsForTest(@RequestBody NetworkService networkService, @PathVariable('testPlanId') String testPlanId, @PathVariable('nsId') String nsId) {
        networkServices[testPlanId + ':' + nsId] = networkService
        networkService.status='RUNNING'
        networkService
    }

    @DeleteMapping('/mock/tpm/plans/{testPlanId}/nss/{nsId}')
    void destroyNsForTest(@PathVariable('testPlanId') String testPlanId, @PathVariable('nsId') String nsId) {
        networkServices[testPlanId + ':' + nsId].status='STOPPED'
    }

}
