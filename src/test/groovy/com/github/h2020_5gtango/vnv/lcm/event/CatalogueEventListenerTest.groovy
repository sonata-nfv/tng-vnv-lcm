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

package com.github.h2020_5gtango.vnv.lcm.event

import com.github.h2020_5gtango.vnv.lcm.config.RestMonitor
import com.github.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus

class CatalogueEventListenerTest extends AbstractSpec {

    @Value('${app.test.package.id}')
    def testPackageId

    @Autowired
    RestMonitor restMonitor

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void "catalogue should handle the package on change event without exception"() {
        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/packages/on-change',
                [
                        event_name: 'CREATED',
                        package_id: testPackageId,
                ]
                , Void.class)

        then:
        entity.statusCode == HttpStatus.OK
        restMonitor.requests.last().args[0].eventName == 'CREATED'
        restMonitor.requests.last().args[0].packageId == testPackageId
    }


    void "catalogue should not execute tests on DELETE event"() {
        given:
        testResultRepositoryMock.reset()

        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/packages/on-change',
                [
                        event_name: CatalogueEventListener.PACKAGE_DELETED,
                        package_id: testPackageId,
                ]
                , Void.class)

        then:
        entity.statusCode == HttpStatus.OK
        testResultRepositoryMock.testPlans.size()==0
    }

}