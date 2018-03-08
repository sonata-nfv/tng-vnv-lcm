package eu.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class TestPlan {
    String testPlanId
    List<NetworkService> networkServices
    List<TestSuite> testSuites

    String status

}
