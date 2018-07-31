package com.github.h2020_5gtango.vnv.lcm.restmock

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.scheduler.SchedulerTest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCatalogueMock {

    private static String TEST_UUID='unit-test-uuid'
    private static String SERVICE_UUID='unit-test-uuid'

    @GetMapping('/mock/gk/packages/{packageId:.+}')
    Map loadPackageMetadata(@PathVariable('packageId') String packageId) {
        if (packageId == SchedulerTest.MULTIPLE_TEST_PLANS_PACKAGE_ID) {
            [pd:[package_content:[
                    [
                            'uuid':TEST_UUID,
                            'content-type':'application/vnd.5gtango.tstd',
                    ],
                    [
                            'uuid':SERVICE_UUID,
                            'content-type':'application/vnd.5gtango.nsd',
                    ],
            ]]]
        } else {
            [package_id:packageId]
        }
    }

    @GetMapping('/mock/catalogue/services')
    List<NetworkService> findServics() {
        DataMock.allNetworkServices01234
    }

    @GetMapping('/mock/catalogue/services/{networkServiceId:.+}')
    NetworkService findService(@PathVariable('networkServiceId') String networkServiceId) {
        def result
        switch (networkServiceId) {
            case SERVICE_UUID:
                result = DataMock.singleNetworkService1
                break

            case 'single_ns_0':
                result = DataMock.singleNetworkService
                break
            case 'single_ns_1':
                result = DataMock.singleNetworkService1
                break
            case 'multiple_ns_2':
                result = DataMock.multipleNetworkService2
                break
            case 'multiple_ns_3':
                result = DataMock.multipleNetworkService3
                break
            case 'multiple_ns_4':
                result = DataMock.multipleNetworkService4
                break
            default:
                result = null

        }
        result

    }

    @GetMapping('/mock/catalogue/tests')
    List<TestSuite> findTests() {
        DataMock.allTestSuites01234
    }

    @GetMapping('/mock/catalogue/tests/{testUuid:.+}')
    TestSuite findTest(@PathVariable('testUuid') String testUuid) {
        def result
        switch (testUuid) {
            case TEST_UUID:
                result = DataMock.singleTestSuite1
                break
            case 'single_test_0':
                result = DataMock.singleTestSuite
                break
            case 'single_test_1':
                result = DataMock.singleTestSuite1
                break
            case 'multiple_test_2':
                result = DataMock.multipleTestSuite2
                break
            case 'multiple_test_3':
                result = DataMock.multipleTestSuite3
                break
            case 'multiple_test_4':
                result = DataMock.multipleTestSuite4
                break
            default:
                result = null
                break
        }
        result
    }
}

