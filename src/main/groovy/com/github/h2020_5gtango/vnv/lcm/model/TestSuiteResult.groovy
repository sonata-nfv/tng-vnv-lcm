package com.github.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class TestSuiteResult {
    String testSuiteResultId
    String testPlanId
    String networkServiceInstanceId

    @ApiModelProperty(required = true)
    @NotNull
    String testSuiteId

    String status
}
