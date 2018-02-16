package eu.h2020_5gtango.vnv.lcm.catalogue

import eu.h2020_5gtango.vnv.lcm.AbstractSpec
import eu.h2020_5gtango.vnv.lcm.config.RestMonitor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class CatalogueEventListenerTest extends AbstractSpec {

    @Autowired
    RestMonitor restMonitor

    void "catalogue should handle the package on change event without exception"() {
        given:
        String eventName="test_${System.currentTimeMillis()}"

        when:
        def entity = postForEntity('/api/v1/tng-vnv-lcm/package/on-change',
                [
                        eventName: eventName,
                        packageId: 'myNS:UPB:1.0',
                ]
                , Void.class)

        then:
        entity.statusCode == HttpStatus.OK
        restMonitor.requests.last().args[0].eventName == eventName
    }

}