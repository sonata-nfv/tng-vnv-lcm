package com.github.h2020_5gtango.vnv.lcm.model

class PackageMetadata {
    List<NetworkService> networkServices=[]
    List<TestSuite> testSuites=[]

    String packageId
    String uuid
    TestDescriptor testd //TODO remove from here
}
