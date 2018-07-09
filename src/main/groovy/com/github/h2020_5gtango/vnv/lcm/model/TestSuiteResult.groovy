package com.github.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class TestSuiteResult {
    String packageId
    String uuid
    String testPlanId
    String instanceUuid
    String serviceUuid

    @ApiModelProperty(required = true)
    @NotNull
    String testUuid

    String status
}
