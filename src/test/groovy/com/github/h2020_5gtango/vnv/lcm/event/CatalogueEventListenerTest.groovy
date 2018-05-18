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