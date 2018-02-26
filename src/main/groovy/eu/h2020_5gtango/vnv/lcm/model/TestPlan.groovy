package eu.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class TestPlan {
    @ApiModelProperty(required = true)
    @NotNull
    String testPlanId

    @ApiModelProperty(required = true)
    @NotNull
    List<NetworkService> networkServices

    @ApiModelProperty(required = true)
    @NotNull
    List<TestSuite> testSuites

    String status

}
