package eu.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class TestSuiteResult {

    @ApiModelProperty(required = true)
    @NotNull
    String testSuiteResultId

    @ApiModelProperty(required = true)
    @NotNull
    String testPlanId

    @ApiModelProperty(required = true)
    @NotNull
    String networkServiceId

    @ApiModelProperty(required = true)
    @NotNull
    String testSuiteId

    String status
}
