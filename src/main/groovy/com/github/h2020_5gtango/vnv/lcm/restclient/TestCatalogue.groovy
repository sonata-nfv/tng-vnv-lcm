package com.github.h2020_5gtango.vnv.lcm.restclient

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestCatalogue {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Value('${app.cat.package.metadata.endpoint}')
    def packageMetadataEndpoint

    @Value('${app.cat.filter.test.endpoint}')
    def filterTestEndpoint

    @Value('${app.cat.filter.ns.endpoint}')
    def filterNsEndpoint

    @Value('${app.cat.list.ns.endpoint}')
    def listNsEndpoint

    PackageMetadata loadPackageMetadata(String packageId) {
        restTemplate.getForEntity(packageMetadataEndpoint,PackageMetadata,packageId).body
    }

    List<TestSuite> findTestsApplicableToNs(NetworkService networkService) {
        restTemplate.getForEntity(filterTestEndpoint, TestSuite[].class, networkService.networkServiceId).body
    }

    List<NetworkService> findNsApplicableToTest(TestSuite testSuite) {
        restTemplate.getForEntity(filterNsEndpoint, NetworkService[].class, testSuite.testSuiteId).body
    }

    NetworkService findNsBySpec(String vendor, String name, String version) {
        NetworkService ns
        restTemplate.getForEntity(listNsEndpoint,Object[].class).body.each{spec->
            if(
            spec.nsd.vendor==vendor &&
            spec.nsd.name==name &&
            spec.nsd.version==version
            ){
                ns=new NetworkService(networkServiceId: spec.uuid,vendor:vendor,name:name,version:version)
            }
        }
        assert ns
        ns
    }
}
