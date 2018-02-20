package eu.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class VnvTestPlan {
    String testPlanId
    String status
    List<NetworkService> networkServices
    List<VnvTest> vnvTests
}
