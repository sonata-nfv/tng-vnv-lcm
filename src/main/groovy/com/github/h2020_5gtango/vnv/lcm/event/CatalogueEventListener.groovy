package com.github.h2020_5gtango.vnv.lcm.event

import com.github.h2020_5gtango.vnv.lcm.scheduler.Scheduler
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RestController
class CatalogueEventListener {

    static final String PACKAGE_DELETED = 'DELETED'

    @Autowired
    Scheduler scheduler

    @ApiResponses(value = [@ApiResponse(code = 400, message = 'Bad Request')])
    @PostMapping('/api/v1/packages/on-change')
    void onChange(@Valid @RequestBody OnPackageChangeEvent onPackageChangeEvent) {
        switch (onPackageChangeEvent.eventName) {
            case PACKAGE_DELETED:
                //TODO: handle package deletion case to cancel any running or pending tests
                break
            default:
                scheduler.scheduleTests(onPackageChangeEvent.packageId)
        }
    }

}
