package eu.h2020_5gtango.vnv.lcm.restclient

import eu.h2020_5gtango.vnv.lcm.model.NetworkService
import eu.h2020_5gtango.vnv.lcm.model.PackageMetadata
import eu.h2020_5gtango.vnv.lcm.model.VnvTest
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

    @Value('${app.catalogue.search.test.endpoint}')
    def searchTestEndpoint

    @Value('${app.catalogue.search.ns.endpoint}')
    def searchNsEndpoint

    PackageMetadata loadPackageMetadata(String packageId) {
        restTemplate.getForEntity(packageMetadataEndpoint,PackageMetadata,packageId).body
    }

    List<VnvTest> findTestsApplicableToNs(NetworkService networkService) {
        restTemplate.getForEntity(searchTestEndpoint, VnvTest[].class, "$networkService.name:$networkService.vendor:$networkService.version").body
    }

    List<NetworkService> findNsApplicableToTest(VnvTest vnvTest) {
        restTemplate.getForEntity(searchNsEndpoint, NetworkService[].class, "$vnvTest.name:$vnvTest.version").body
    }
}
