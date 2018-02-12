package eu.h2020_5gtango.vnv.lcm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.context.ApplicationContext
import org.springframework.http.ResponseEntity
import org.springframework.boot.test.TestRestTemplate
import spock.lang.Specification

@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(["server.port=0"])
class AbstractSpec extends Specification {

    @Value('${local.server.port}')
    int port

    @Autowired
    ApplicationContext context

    String url(String path){
        "http://localhost:$port$path"
    }

    public <T> ResponseEntity<T> getForEntity(String path, Class<T> responseType, Object... urlVariables){
        new TestRestTemplate().getForEntity(url(path), responseType,urlVariables)
    }

}