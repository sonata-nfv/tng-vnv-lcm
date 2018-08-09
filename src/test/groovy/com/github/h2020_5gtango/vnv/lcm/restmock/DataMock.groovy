/*
 * Copyright (c) 2015 SONATA-NFV, 2017 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * ALL RIGHTS RESERVED.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Neither the name of the SONATA-NFV, 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * nor the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * This work has been performed in the framework of the SONATA project,
 * funded by the European Commission under Grant number 671517 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the SONATA
 * partner consortium (www.sonata-nfv.eu).
 *
 * This work has been performed in the framework of the 5GTANGO project,
 * funded by the European Commission under Grant number 761493 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the 5GTANGO
 * partner consortium (www.5gtango.eu).
 */

package com.github.h2020_5gtango.vnv.lcm.restmock

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.NetworkServiceDescriptor
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.model.TestTag
import groovy.json.JsonSlurper
import org.springframework.util.ResourceUtils

class DataMock {

    static NetworkService getSingleNetworkService(){
                new NetworkService(networkServiceId: 'single_ns_0', name: 'single_ns_0', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag']))
    }

    static NetworkService getSingleNetworkService1(){
        new NetworkService(networkServiceId: 'single_ns_1', name: 'single_ns_1', vendor: 'vendor',
                version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag1']))
    }

    static NetworkService getMultipleNetworkService1(){
        new NetworkService(networkServiceId: 'multiple_ns_1', name: 'multiple_ns_1', vendor: 'vendor',
                version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag1']))
    }

    static NetworkService getMultipleNetworkService2(){
        new NetworkService(networkServiceId: 'multiple_ns_2', name: 'multiple_ns_2', vendor: 'vendor',
                version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag']))
    }

    static NetworkService getMultipleNetworkService3(){
        new NetworkService(networkServiceId: 'multiple_ns_3', name: 'multiple_ns_3', vendor: 'vendor',
                version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag']))
    }

    static NetworkService getMultipleNetworkService4(){
        new NetworkService(networkServiceId: 'multiple_ns_4', name: 'multiple_ns_4', vendor: 'vendor',
                version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag']))
    }

    static List<NetworkService> getMultipleNetworkServices12(){
        [
                new NetworkService(networkServiceId: 'multiple_ns_1', name: 'multiple_ns_1', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag1'])),
                new NetworkService(networkServiceId: 'multiple_ns_2', name: 'multiple_ns_2', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'])),
        ]
    }

    static List<NetworkService> getMultipleNetworkServices34(){
        [
                new NetworkService(networkServiceId: 'multiple_ns_3', name: 'multiple_ns_3', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'])),
                new NetworkService(networkServiceId: 'multiple_ns_4', name: 'multiple_ns_4', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'])),
        ]
    }

    static List<NetworkService> getAllNetworkServices01234(){
        [
                new NetworkService(networkServiceId: 'single_ns_0', name: 'single_ns_0', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'])),
                new NetworkService(networkServiceId: 'multiple_ns_1', name: 'multiple_ns_1', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag1'])),
                new NetworkService(networkServiceId: 'multiple_ns_2', name: 'multiple_ns_2', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'])),
                new NetworkService(networkServiceId: 'multiple_ns_3', name: 'multiple_ns_3', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'])),
                new NetworkService(networkServiceId: 'multiple_ns_4', name: 'multiple_ns_4', vendor: 'vendor',
                        version: 'version', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'])),
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

    static def getAllNetworkServiceJson() {
        [
                attachJsonData("classpath:static/ns.json"),

        ]
    }

    static def getAllTestSuiteJson() {
        [
                attachJsonData("classpath:static/ts.json"),
        ]
    }

    static def getSingleNetworkServiceJson() {
        attachJsonData("classpath:static/ns.json")

    }

    static def getSingleTestSuiteJson() {
        attachJsonData("classpath:static/ts.json")
    }


    static def attachJsonData(String resourceLocation){
        File file = ResourceUtils.getFile(resourceLocation)
        (file.exists()) ? new JsonSlurper().parseText(file.text) :  null

    }
}
