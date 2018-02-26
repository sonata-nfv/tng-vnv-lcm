package eu.h2020_5gtango.vnv.lcm.restclient

import eu.h2020_5gtango.vnv.lcm.model.NetworkService
import eu.h2020_5gtango.vnv.lcm.model.PackageMetadata
import eu.h2020_5gtango.vnv.lcm.model.TestSuite
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestCatalogue {

    @Autowired
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
        restTemplate.getForEntity(filterTestEndpoint, TestSuite[].class, "$networkService.name:$networkService.vendor:$networkService.version").body
    }

    List<NetworkService> findNsApplicableToTest(TestSuite testSuite) {
        restTemplate.getForEntity(filterNsEndpoint, NetworkService[].class, "$testSuite.name:$testSuite.version").body
    }
}
