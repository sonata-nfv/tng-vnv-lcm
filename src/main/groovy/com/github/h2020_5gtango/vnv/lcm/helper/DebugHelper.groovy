package com.github.h2020_5gtango.vnv.lcm.helper

import groovy.util.logging.Log
import org.springframework.http.ResponseEntity

@Log
class DebugHelper {
    static def callExternalEndpoint(ResponseEntity responseEntity, def methodName, def endpoint, String message="ERROR CONNECTING WITH ENDPOINT"){
        if(responseEntity.statusCodeValue in [200, 201, 202, 203, 204, 205, 206, 207, 208, 226]) {
            log.info("##vnvlog-v.2:$methodName call_endpoint: $endpoint, status: ${responseEntity.statusCode}")
        } else {
            log.severe("##vnvlog-v.2:$methodName $message: $endpoint, status: ${responseEntity.statusCode}")
            return null
        }

        responseEntity
    }

    static String nsAndTestsMappingToString(Map map){
        def stringBuffer = new StringBuffer("\n ---- \n")
        stringBuffer.append("##vnvlog-v.2: testPlans:\n\n")
        stringBuffer.append(" ns's(#${map?.keySet()?.size()})");
        map?.each { ns1, tss1 -> stringBuffer.append(
                "\n nsId: ${ns1.networkServiceId}, testingTags: ${ns1.nsd.testingTags} \n\t - ts's(#${tss1.size()})" );
            tss1.each { t1 ->
                stringBuffer.append(" \n\t\ttsId: ${t1.testUuid}, testTag's: ( ")
                t1.testd.testExecution.each{ stringBuffer.append('\'' + it.testTag+'\' ')}
                stringBuffer.append(')')

            }
        }
        stringBuffer.append("\n ")
        stringBuffer.toString()
    }

}
