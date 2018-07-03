package com.github.h2020_5gtango.vnv.lcm.restmock

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.scheduler.SchedulerTest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCatelogogueMock {

    private static String TEST_UUID='unit-test-uuid'

    @GetMapping('/mock/gk/packages/{packageId:.+}')
    Map loadPackageMetadata(@PathVariable('packageId') String packageId) {
        if (packageId == SchedulerTest.MULTIPLE_TEST_PLANS_PACKAGE_ID) {
            [pd:[package_content:[
                    [
                            'uuid':TEST_UUID,
                            'content-type':'application/vnd.5gtango.tstd',
                    ]
            ]]]
        } else {
            [package_id:packageId]
        }
    }

    @GetMapping('/mock/catalogue/tests/{testUuid:.+}')
    PackageMetadata loadTestMetadata(@PathVariable('testUuid') String testUuid) {
        if (testUuid == TEST_UUID) {
            new PackageMetadata(
                    networkServices: [
                            new NetworkService(networkServiceId: 'multiple_ns_1', name: 'multiple_ns_1', vendor: 'vendor', version: 'version'),
                            new NetworkService(networkServiceId: 'multiple_ns_2', name: 'multiple_ns_2', vendor: 'vendor', version: 'version'),
                    ],
                    testSuites: [
                            new TestSuite(testUuid: 'multiple_test_1', name: 'multiple_test_1', version: 'version'),
                            new TestSuite(testUuid: 'multiple_test_2', name: 'multiple_test_2', version: 'version'),
                    ],
            )
        } else {
            new PackageMetadata(
                    networkServices: [new NetworkService(networkServiceId: 'serviceInstanceUuid', name: 'name', vendor: 'vendor', version: 'version')],
                    testSuites: [new TestSuite(testUuid: 'testUuid', name: 'name', version: 'version')],
            )
        }
    }

    @GetMapping('/mock/catalogue/network-services/{networkServiceId}/matched-test-suites')
    List<TestSuite> findTestsApplicableToNs(@PathVariable('networkServiceId') String networkServiceId) {
        if (networkServiceId.startsWith('multiple_')) {
            [
                    new TestSuite(testUuid: 'multiple_test_3', name: 'multiple_test_3', version: 'version'),
                    new TestSuite(testUuid: 'multiple_test_4', name: 'multiple_test_4', version: 'version'),
            ]
        } else {
            [new TestSuite(testUuid: 'testUuid', name: 'name', version: 'version')]
        }
    }

    @GetMapping('/mock/catalogue/test-suites/{testUuid}/matched-network-services')
    List<NetworkService> findNsApplicableToTest(@PathVariable('testUuid') String testUuid) {
        if (testUuid.startsWith('multiple_')) {
            [
                    new NetworkService(networkServiceId: 'multiple_ns_3', name: 'multiple_ns_3', vendor: 'vendor', version: 'version'),
                    new NetworkService(networkServiceId: 'multiple_ns_4', name: 'multiple_ns_4', vendor: 'vendor', version: 'version'),
            ]
        } else {
            [new NetworkService(networkServiceId: 'serviceInstanceUuid', name: 'name', vendor: 'vendor', version: 'version')]
        }
    }

}
