package com.github.h2020_5gtango.vnv.lcm.scheduler

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RestController
class NetworkServiceController {

    @Autowired
    Scheduler scheduler

    @ApiResponses(value = [@ApiResponse(code = 400, message = 'Bad Request')])
    @PostMapping('/api/v1/schedulers/services')
    void onChange(@Valid @RequestBody List<NetworkService> serviceList) {
        def metadata = new PackageMetadata()
        metadata.networkServices << serviceList
        scheduler.scheduleTests(metadata)
    }
}
