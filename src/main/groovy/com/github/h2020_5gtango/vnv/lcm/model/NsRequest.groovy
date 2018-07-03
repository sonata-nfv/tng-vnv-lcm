package com.github.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class NsRequest {
    String serviceUuid
    String serviceInstanceUuid
    String requestType
    List<String> ingresses=[]
    List<String> egresses=[]
}
