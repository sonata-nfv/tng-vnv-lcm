/***
 ## Copyright (c) 2018 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 ## ALL RIGHTS RESERVED.
 ##
 ## Licensed under the Apache License, Version 2.0 (the "License");
 ## you may not use this file except in compliance with the License.
 ## You may obtain a copy of the License at
 ##
 ##     http://www.apache.org/licenses/LICENSE-2.0
 ##
 ## Unless required by applicable law or agreed to in writing, software
 ## distributed under the License is distributed on an "AS IS" BASIS,
 ## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ## See the License for the specific language governing permissions and
 ## limitations under the License.
 ##
 ## Neither the name of the SONATA-NFV [, ANY ADDITIONAL AFFILIATION]
 ## nor the names of its contributors may be used to endorse or promote
 ## products derived from this software without specific prior written
 ## permission.
 ##
 ## This work has been performed in the framework of the SONATA project,
 ## funded by the European Commission under Grant number 671517 through
 ## the Horizon 2020 and 5G-PPP programmes. The authors would like to
 ## acknowledge the contributions of their colleagues of the SONATA
 ## partner consortium (www.sonata-nfv.eu).
 */
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