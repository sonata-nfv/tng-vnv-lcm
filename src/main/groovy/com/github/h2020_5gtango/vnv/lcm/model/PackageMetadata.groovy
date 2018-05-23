package com.github.h2020_5gtango.vnv.lcm.model

class PackageMetadata {
    List<NetworkService> networkServices
    List<TestSuite> testSuites

    String packageId
    String uuid
    TestDescriptor testd
}


class TestDescriptor{
    String vendor
    String name
    String version
    String testType
    List<TestTag> testExecution
}


class TestTag{
    String testTag
    String tagId
}
