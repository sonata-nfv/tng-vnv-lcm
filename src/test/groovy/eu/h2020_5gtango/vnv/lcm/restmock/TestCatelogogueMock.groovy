package eu.h2020_5gtango.vnv.lcm.restmock

import eu.h2020_5gtango.vnv.lcm.model.NetworkService
import eu.h2020_5gtango.vnv.lcm.model.PackageMetadata
import eu.h2020_5gtango.vnv.lcm.model.VnvTest
import eu.h2020_5gtango.vnv.lcm.scheduler.SchedulerTest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCatelogogueMock {


    @GetMapping('/mock/catalogue/packages/{packageId:.+}')
    PackageMetadata loadPackageMetadata(@PathVariable('packageId') String packageId) {
        if(packageId== SchedulerTest.MULTIPLE_TEST_PLANS_PACKAGE_ID){
            new PackageMetadata(
                    networkServices: [
                            new NetworkService(name: 'multiple_ns_1', vendor: 'vendor', version: 'version'),
                            new NetworkService(name: 'multiple_ns_2', vendor: 'vendor', version: 'version'),
                    ],
                    vnvTests: [
                            new VnvTest(name: 'multiple_test_1', version: 'version'),
                            new VnvTest(name: 'multiple_test_2', version: 'version'),
                    ],
            )
        }else{
            new PackageMetadata(
                    networkServices: [new NetworkService(name: 'name', vendor: 'vendor', version: 'version')],
                    vnvTests: [new VnvTest(name: 'name', version: 'version')],
            )
        }
    }

    @GetMapping('/mock/catalogue/search/test-by-ns/{nsId:.+}')
    List<VnvTest> findTestsApplicableToNs(@PathVariable('nsId') String nsId) {
        if(nsId.startsWith('multiple_')){
            [
                    new VnvTest(name: 'multiple_test_3', version: 'version'),
                    new VnvTest(name: 'multiple_test_4', version: 'version'),
            ]
        }else{
            [new VnvTest(name: 'name', version: 'version')]
        }
    }

    @GetMapping('/mock/catalogue/search/ns-by-test/{testId:.+}')
    List<NetworkService> findNsApplicableToTest(@PathVariable('testId') String testId) {
        if(testId.startsWith('multiple_')){
            [
                    new NetworkService(name: 'multiple_ns_3', vendor: 'vendor', version: 'version'),
                    new NetworkService(name: 'multiple_ns_4', vendor: 'vendor', version: 'version'),
            ]
        }else{
            [new NetworkService(name: 'name', vendor: 'vendor', version: 'version')]
        }
    }

}
