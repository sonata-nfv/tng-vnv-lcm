package com.github.h2020_5gtango.vnv.lcm.model

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