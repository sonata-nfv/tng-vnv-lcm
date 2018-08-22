package com.github.h2020_5gtango.vnv.lcm.helper

import groovy.util.logging.Log
import org.springframework.http.ResponseEntity

@Log
class DebugHelper {
    static def callExternalEndpoint(ResponseEntity responseEntity, def endpoint, String message="ERROR CONNECTING WITH ENDPOINT"){
        log.info("##vnvlog call_external_endpoint: $endpoint, status: ${responseEntity.statusCode}, statusSalue: ${responseEntity.statusCodeValue}")
        if(responseEntity.statusCodeValue != 200) {
            log.error("##vnvlog $message: $endpoint, status: ${responseEntity.statusCode}")
            return null
        }

        responseEntity
    }
}
