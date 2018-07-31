package com.github.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class NetworkService {

//    @ApiModelProperty(required = true)
//    @NotNull
    String networkServiceId

    String name
    String vendor
    String version

    String status

    Map serviceDescriptor

    List<String> testingTags;
}
