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
import com.github.h2020_5gtango.vnv.lcm.model.TestDescriptor
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.model.TestTag
import groovy.json.JsonSlurper
import org.springframework.util.ResourceUtils

class DataMock {

    static NetworkService getSingleNetworkService(){
        new NetworkService(networkServiceId: 'single_ns_0', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'],
                name: 'single_ns_0', vendor: 'vendor', version: 'version'))
    }

    static NetworkService getSingleNetworkService1(){
        new NetworkService(networkServiceId: 'single_ns_1', nsd: new NetworkServiceDescriptor(testingTags: ['testTag1'],
                name: 'single_ns_1', vendor: 'vendor', version: 'version'))
    }

    static NetworkService getMultipleNetworkService1(){
        new NetworkService(networkServiceId: 'multiple_ns_1', nsd: new NetworkServiceDescriptor(testingTags: ['testTag1'],
                name: 'multiple_ns_1', vendor: 'vendor', version: 'version'))
    }

    static NetworkService getMultipleNetworkService2(){
        new NetworkService(networkServiceId: 'multiple_ns_2',nsd: new NetworkServiceDescriptor(testingTags: ['testTag'],
                name: 'multiple_ns_2', vendor: 'vendor', version: 'version'))
    }

    static NetworkService getMultipleNetworkService3(){
        new NetworkService(networkServiceId: 'multiple_ns_3', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'],
                name: 'multiple_ns_3', vendor: 'vendor', version: 'version'))
    }

    static NetworkService getMultipleNetworkService4(){
        new NetworkService(networkServiceId: 'multiple_ns_4', nsd: new NetworkServiceDescriptor(testingTags: ['testTag'],
                name: 'multiple_ns_4', vendor: 'vendor', version: 'version'))
    }

    static List<NetworkService> getMultipleNetworkServices12(){
        [
                getMultipleNetworkService1(),
                getMultipleNetworkService2(),
        ]
    }

    static List<NetworkService> getMultipleNetworkServices34(){
        [
                getMultipleNetworkService3(),
                getMultipleNetworkService4(),
        ]
    }

    static List<NetworkService> getAllNetworkServices01234(){
        [
                getSingleNetworkService(),
                getMultipleNetworkService1(),
                getMultipleNetworkService2(),
                getMultipleNetworkService3(),
                getMultipleNetworkService4(),
        ]
    }

    static TestSuite getSingleTestSuite(){

                new TestSuite(testUuid: 'single_test_0',
                        testd: new TestDescriptor(name: 'single_test_0', version: 'version', testType: 'test_type',
                                testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]))

    }

    static TestSuite getSingleTestSuite1(){
        new TestSuite(testUuid: 'single_test_1',
                testd: new TestDescriptor(name: 'single_test_1', version: 'version', testType: 'test_type',
                        testExecution: [new TestTag(testTag:'testTag1', tagId: 'tagId')]))
    }

    static TestSuite getMultipleTestSuite1(){
        new TestSuite(testUuid: 'single_test_1',
                testd: new TestDescriptor(name: 'multiple_test_1', version: 'version', testType: 'test_type',
                        testExecution: [new TestTag(testTag:'testTag1', tagId: 'tagId')]))
    }

    static TestSuite getMultipleTestSuite2(){
        new TestSuite(testUuid: 'multiple_test_2',
                testd: new TestDescriptor(name: 'multiple_test_2', version: 'version', testType: 'test_type',
                        testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]))
    }

    static TestSuite getMultipleTestSuite3(){
        new TestSuite(testUuid: 'multiple_test_3',
                testd: new TestDescriptor(name: 'multiple_test_3', version: 'version', testType: 'test_type',
                        testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]))
    }

    static TestSuite getMultipleTestSuite4(){
        new TestSuite(testUuid: 'multiple_test_4',
                testd: new TestDescriptor(name: 'multiple_test_4', version: 'version', testType: 'test_type',
                        testExecution: [new TestTag(testTag:'testTag', tagId: 'tagId')]))
    }

    static List<TestSuite> getMultipleTestSuites12(){
        [
                getMultipleTestSuite1(),
                getMultipleTestSuite2(),
        ]
    }

    static List<TestSuite> getMultipleTestSuites34(){
        [
                getMultipleTestSuite3(),
                getMultipleTestSuite4(),
        ]
    }

    static List<TestSuite> getAllTestSuites01234(){
        [
                getSingleTestSuite(),
                getMultipleTestSuite1(),
                getMultipleTestSuite2(),
                getMultipleTestSuite3(),
                getMultipleTestSuite4(),

        ]
    }

    static def getSingleNetworkService_input0nsJson() {
        attachJsonData("classpath:static/json/ns.json")

    }

    static def getSingleNetworkService_4763bde6Json() {
        attachJsonData("classpath:static/json/ns_4763bde6-f213-4fae-8d3f-04358e1e1445.json")

    }

    static def getSingleNetworkService_a0c112acJson() {
        attachJsonData("classpath:static/json/ns_a0c112ac-8c06-49e0-a34b-16693a50e72a.json")

    }

    static def getSingleNetworkService_f64a458cJson() {
        attachJsonData("classpath:static/json/ns_f64a458c-e157-49ff-a8f9-3bbbbf8db625.json")

    }

    static def getSingleTestSuite_input0tsJson() {
        attachJsonData("classpath:static/json/ts.json")
    }

    static def getSingleTestSuite_9bbbd636Json() {
        attachJsonData("classpath:static/json/ts_9bbbd636-75f5-4ca1-90c8-12ec80a79821.json")
    }

    static def getSingleTestSuite_ccbf8badJson() {
        attachJsonData("classpath:static/json/ts_ccbf8bad-2534-4308-b47c-4034133b37ac.json")
    }

    static def getSingleTestSuite_fe7ec2a8Json() {
        attachJsonData("classpath:static/json/ts_fe7ec2a8-644f-4788-9aa7-bc2ff059819e.json")
    }

    static def attachJsonData(String resourceLocation){
        File file = ResourceUtils.getFile(resourceLocation)
        (file.exists()) ? new JsonSlurper().parseText(file.text) :  null

    }
}
