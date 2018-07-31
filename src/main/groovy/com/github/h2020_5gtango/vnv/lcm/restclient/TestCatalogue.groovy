package com.github.h2020_5gtango.vnv.lcm.restclient

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.model.TestTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TestCatalogue {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplateWithAuth

    @Autowired
    @Qualifier('restTemplateWithoutAuth')
    RestTemplate restTemplate

    @Value('${app.cat.package.metadata.endpoint}')
    def packageMetadataEndpoint

    @Value('${app.cat.test.metadata.endpoint}')
    def testMetadataEndpoint

    @Value('${app.cat.test.list.endpoint}')
    def testListEndpoint

    @Value('${app.cat.service.metadata.endpoint}')
    def serviceMetadataEndpoint

    @Value('${app.cat.service.list.endpoint}')
    def serviceListEndpoint


    PackageMetadata loadPackageMetadata(String packageId) {
        def rawPackageMetadata=restTemplate.getForEntity(packageMetadataEndpoint,Object.class,packageId).body
        PackageMetadata packageMetadata=new PackageMetadata(packageId: packageId)
        rawPackageMetadata.pd?.package_content.each{resource->
            switch (resource.get('content-type')) {
                case 'application/vnd.5gtango.tstd':
                    packageMetadata.testSuites << restTemplateWithAuth.getForEntity(testMetadataEndpoint, TestSuite.class, resource.uuid).body
                    break
                case 'application/vnd.5gtango.nsd':
                    packageMetadata.networkServices << restTemplateWithAuth.getForEntity(serviceMetadataEndpoint, NetworkService.class, resource.uuid).body
                    break
            }
        }
        packageMetadata
    }

    NetworkService findNetworkService(String networkServiceId) {
        restTemplateWithAuth.getForEntity(serviceMetadataEndpoint, NetworkService, networkServiceId).body
    }

    TestSuite findTestSuite(String testUuid) {
        restTemplateWithAuth.getForEntity(testMetadataEndpoint, TestSuite, testUuid).body
    }


    List<NetworkService> findNssByTestTag(String tag) {
        List filtered = []
        List<NetworkService> nss = restTemplateWithAuth.getForEntity(serviceListEndpoint, NetworkService[]).body
        nss.each { ns ->
            if(ns.testingTags.contains(tag))
                filtered << ns
        }
        filtered
    }


    List<TestSuite> findTssByTestTag(String tag) {
        List filtered = []
        List<TestSuite> tss  = restTemplateWithAuth.getForEntity(testListEndpoint, TestSuite[]).body
                tss.each { ts ->
                    List<TestTag> tt = ts.testExecution
                    tt?.each {
                        it -> if(it.testTag == tag)
                            filtered << ts
                    }
                }
        filtered
    }

}
