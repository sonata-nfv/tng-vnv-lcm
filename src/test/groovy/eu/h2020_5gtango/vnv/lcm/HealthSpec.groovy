package eu.h2020_5gtango.vnv.lcm

import org.springframework.http.HttpStatus

class HealthSpec extends AbstractSpec {

    void "web app should be health"() {
        when:
        def entity = getForEntity('/health', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.status == 'UP'
        entity.body.diskSpace.status == 'UP'
    }

}