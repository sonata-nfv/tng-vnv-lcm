package eu.h2020_5gtango.vnv.lcm.event

import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

class OnPackageChangeEvent {

    @ApiModelProperty(required = true)
    @NotNull
    String eventName

    @ApiModelProperty(required = true)
    @NotNull
    String packageId

    String packageLocation

}
