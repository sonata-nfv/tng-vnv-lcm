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

package com.github.h2020_5gtango.vnv.lcm.restclient

import com.github.h2020_5gtango.vnv.lcm.model.TestPlan
import com.github.h2020_5gtango.vnv.lcm.model.TestSuiteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestExecutionEngine {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Value('${app.tee.suite.execute.endpoint}')
    def suiteExecuteEndpoint

    TestPlan executeTests(TestPlan testPlan) {
        def planStatus = 'SUCCESS'
        def results=[]
        testPlan.testSuiteResults.each { testSuiteResult ->
            testSuiteResult.testPlanId=testPlan.uuid
            testSuiteResult.instanceUuid=testPlan.networkServiceInstances.first().instanceUuid
            testSuiteResult.serviceUuid=testPlan.networkServiceInstances.first().serviceUuid
            testSuiteResult = restTemplate.postForEntity(suiteExecuteEndpoint, testSuiteResult, TestSuiteResult).body
            planStatus = planStatus == 'SUCCESS' ? testSuiteResult.status : planStatus
            results.add(testSuiteResult)
        }
        testPlan.testSuiteResults=results
        testPlan.status = planStatus
        testPlan
    }
}
