package eu.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class TestSuiteResult {

    String testPlanId
    String networkServiceId
    String testSuiteId

    String status

    String generateId(){
        "$testPlanId:$networkServiceId:$testSuiteId"
    }
}
