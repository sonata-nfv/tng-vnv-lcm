package eu.h2020_5gtango.vnv.lcm.catalogue

import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RestController
class CatalogueEventListener {

    @ApiResponses(value = [@ApiResponse(code = 400, message = 'Bad Request')])
    @PostMapping('/api/v1/tng-vnv-lcm/package/on-change')
    void onChange(@Valid @RequestBody OnPackageChangeEvent onPackageChangeEvent) {
        //TODO: to schedule test
    }

}
