package eu.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class NetworkService {

    @ApiModelProperty(required = true)
    @NotNull
    String name

    @ApiModelProperty(required = true)
    @NotNull
    String vendor

    @ApiModelProperty(required = true)
    @NotNull
    String version

    String status

    String generateId(){
        "$name:$vendor:$version"
    }
}
