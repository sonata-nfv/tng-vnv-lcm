package com.github.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class TestSuite {
    @ApiModelProperty(required = true)
    @NotNull
    String testUuid
    String packageId

    String vendor
    String name
    String version

    TestDescriptor testd

    String testType
    List<TestTag> testExecution
}
