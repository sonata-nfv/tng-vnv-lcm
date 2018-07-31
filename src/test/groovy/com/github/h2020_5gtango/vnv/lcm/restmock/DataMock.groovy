package com.github.h2020_5gtango.vnv.lcm.restmock

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.model.TestTag

class DataMock {

    static NetworkService getSingleNetworkService(){
                new NetworkService(networkServiceId: 'single_ns_0', name: 'single_ns_0', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag'])
    }

    static NetworkService getSingleNetworkService1(){
        new NetworkService(networkServiceId: 'single_ns_1', name: 'single_ns_1', vendor: 'vendor',
                version: 'version', testingTags: ['testTag1'])
    }

    static NetworkService getMultipleNetworkService1(){
        new NetworkService(networkServiceId: 'multiple_ns_1', name: 'multiple_ns_1', vendor: 'vendor',
                version: 'version', testingTags: ['testTag1'])
    }

    static NetworkService getMultipleNetworkService2(){
        new NetworkService(networkServiceId: 'multiple_ns_2', name: 'multiple_ns_2', vendor: 'vendor',
                version: 'version', testingTags: ['testTag'])
    }

    static NetworkService getMultipleNetworkService3(){
        new NetworkService(networkServiceId: 'multiple_ns_3', name: 'multiple_ns_3', vendor: 'vendor',
                version: 'version', testingTags: ['testTag'])
    }

    static NetworkService getMultipleNetworkService4(){
        new NetworkService(networkServiceId: 'multiple_ns_4', name: 'multiple_ns_4', vendor: 'vendor',
                version: 'version', testingTags: ['testTag'])
    }

    static List<NetworkService> getMultipleNetworkServices12(){
        [
                new NetworkService(networkServiceId: 'multiple_ns_1', name: 'multiple_ns_1', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag1']),
                new NetworkService(networkServiceId: 'multiple_ns_2', name: 'multiple_ns_2', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag']),
        ]
    }

    static List<NetworkService> getMultipleNetworkServices34(){
        [
                new NetworkService(networkServiceId: 'multiple_ns_3', name: 'multiple_ns_3', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag']),
                new NetworkService(networkServiceId: 'multiple_ns_4', name: 'multiple_ns_4', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag']),
        ]
    }

    static List<NetworkService> getAllNetworkServices01234(){
        [
                new NetworkService(networkServiceId: 'single_ns_0', name: 'single_ns_0', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag']),
                new NetworkService(networkServiceId: 'multiple_ns_1', name: 'multiple_ns_1', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag1']),
                new NetworkService(networkServiceId: 'multiple_ns_2', name: 'multiple_ns_2', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag']),
                new NetworkService(networkServiceId: 'multiple_ns_3', name: 'multiple_ns_3', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag']),
                new NetworkService(networkServiceId: 'multiple_ns_4', name: 'multiple_ns_4', vendor: 'vendor',
                        version: 'version', testingTags: ['testTag']),
        ]
    }

    static TestSuite getSingleTestSuite(){
                new TestSuite(testUuid: 'single_test_0', name: 'single_test_0', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')])
    }

    static TestSuite getSingleTestSuite1(){
        new TestSuite(testUuid: 'single_test_1', name: 'single_test_1', version: 'version',
                testType: 'test_type', testExecution: [new TestTag(testTag:'testTag1', tagId: 'tagId')])
    }

    static TestSuite getMultipleTestSuite1(){
        new TestSuite(testUuid: 'single_test_1', name: 'single_test_1', version: 'version',
                testType: 'test_type', testExecution: [new TestTag(testTag:'testTag1', tagId: 'tagId')])
    }

    static TestSuite getMultipleTestSuite2(){
        new TestSuite(testUuid: 'multiple_test_2', name: 'single_test_2', version: 'version',
                testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')])
    }

    static TestSuite getMultipleTestSuite3(){
        new TestSuite(testUuid: 'multiple_test_3', name: 'single_test_3', version: 'version',
                testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')])
    }

    static TestSuite getMultipleTestSuite4(){
        new TestSuite(testUuid: 'multiple_test_4', name: 'single_test_4', version: 'version',
                testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')])
    }

    static List<TestSuite> getMultipleTestSuites12(){
        [
                new TestSuite(testUuid: 'multiple_test_1', name: 'multiple_test_1', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]),
                new TestSuite(testUuid: 'multiple_test_2', name: 'multiple_test_2', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]),
        ]
    }

    static List<TestSuite> getMultipleTestSuites34(){
        [
                new TestSuite(testUuid: 'multiple_test_3', name: 'multiple_test_3', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]),
                new TestSuite(testUuid: 'multiple_test_4', name: 'multiple_test_4', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]),
        ]
    }

    static List<TestSuite> getAllTestSuites01234(){
        [
                new TestSuite(testUuid: 'single_test_0', name: 'single_test_0', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]),
                new TestSuite(testUuid: 'multiple_test_1', name: 'multiple_test_1', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag1', tagId: 'tagId')]),
                new TestSuite(testUuid: 'multiple_test_2', name: 'multiple_test_2', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]),
                new TestSuite(testUuid: 'multiple_test_3', name: 'multiple_test_3', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]),
                new TestSuite(testUuid: 'multiple_test_4', name: 'multiple_test_4', version: 'version',
                        testType: 'test_type', testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]),
        ]
    }
}
