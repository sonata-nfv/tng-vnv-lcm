package eu.h2020_5gtango.vnv.lcm.event

import eu.h2020_5gtango.vnv.lcm.AbstractSpec
import eu.h2020_5gtango.vnv.lcm.config.RestMonitor
import eu.h2020_5gtango.vnv.lcm.restmock.TestResultRepositoryMock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class CatalogueEventListenerTest extends AbstractSpec {

    public static final String TEST_PACKAGE_ID='myNS:UPB:1.0'

    @Autowired
    RestMonitor restMonitor

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void "catalogue should handle the package on change event without exception"() {
        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/packages/on-change',
                [
                        event_name: 'CREATED',
                        package_id: TEST_PACKAGE_ID,
                ]
                , Void.class)

        then:
        entity.statusCode == HttpStatus.OK
        restMonitor.requests.last().args[0].eventName == 'CREATED'
        restMonitor.requests.last().args[0].packageId == TEST_PACKAGE_ID
    }


    void "catalogue should not execute tests on DELETE event"() {
        given:
        testResultRepositoryMock.reset()

        when:
        def entity = postForEntity('/tng-vnv-lcm/api/v1/packages/on-change',
                [
                        event_name: CatalogueEventListener.PACKAGE_DELETED,
                        package_id: TEST_PACKAGE_ID,
                ]
                , Void.class)

        then:
        entity.statusCode == HttpStatus.OK
        testResultRepositoryMock.testPlans.size()==0
    }

}