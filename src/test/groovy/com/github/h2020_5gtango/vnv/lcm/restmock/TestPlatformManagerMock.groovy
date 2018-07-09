package com.github.h2020_5gtango.vnv.lcm.restmock

import com.github.h2020_5gtango.vnv.lcm.model.NetworkServiceInstance
import com.github.h2020_5gtango.vnv.lcm.model.NsRequest
import com.github.h2020_5gtango.vnv.lcm.model.NsResponse
import org.springframework.web.bind.annotation.*

@RestController
class TestPlatformManagerMock {

    Map<String, NsResponse> networkServiceInstances = [:]

    void reset() {
        networkServiceInstances.clear()
    }

    @PostMapping('/mock/tpm/requests')
    NsResponse deployNsForTest(@RequestBody NsRequest nsRequest) {
        def networkServiceInstance = new NsResponse(
                instanceUuid: nsRequest.requestType == 'CREATE' ? UUID.randomUUID().toString() : nsRequest.instanceUuid,
                serviceUuid: nsRequest.serviceUuid,
                status: nsRequest.requestType == 'CREATE' ? 'CREATED' : 'TERMINATED',
        )
        networkServiceInstance.id=networkServiceInstance.instanceUuid
        networkServiceInstances[networkServiceInstance.id] = networkServiceInstance
        networkServiceInstance
    }

    @GetMapping('/mock/tpm/requests')
    List<NsResponse> getDeployedNs() {
        []
    }

    @GetMapping('/mock/tpm/requests/{requestId}')
    NsResponse getNsForTest(@PathVariable('requestId') String requestId) {
        def nsi=networkServiceInstances[requestId]
        nsi.status = 'READY'
        nsi
    }

}
