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

    @Value('${app.catalogue.package.metadata.endpoint}')
    def packageMetadataEndpoint

    @Value('${app.catalogue.filter.test.endpoint}')
    def filterTestEndpoint

    @Value('${app.catalogue.filter.ns.endpoint}')
    def filterNsEndpoint

    PackageMetadata loadPackageMetadata(String packageId) {
        restTemplate.getForEntity(packageMetadataEndpoint,PackageMetadata,packageId).body
    }

    List<TestSuite> findTestsApplicableToNs(NetworkService networkService) {
        restTemplate.getForEntity(filterTestEndpoint, TestSuite[].class, networkService.networkServiceId).body
    }

    List<NetworkService> findNsApplicableToTest(TestSuite testSuite) {
        restTemplate.getForEntity(filterNsEndpoint, NetworkService[].class, testSuite.testSuiteId).body
    }
}
