package eu.h2020_5gtango.vnv.lcm.scheduler

import eu.h2020_5gtango.vnv.lcm.restclient.TestCatalogue
import eu.h2020_5gtango.vnv.lcm.model.NetworkService
import eu.h2020_5gtango.vnv.lcm.model.VnvTest
import eu.h2020_5gtango.vnv.lcm.workflow.WorkflowManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Scheduler {

    @Autowired
    TestCatalogue testCatalogue

    @Autowired
    WorkflowManager workflowManager

    void scheduleTests(String packageId) {
        Map<NetworkService, List<VnvTest>> nsAndTestsMapping = discoverNssAndTestsToRun(packageId)
        nsAndTestsMapping.each {ns,vnvTests->
            workflowManager.execute(ns,vnvTests)
        }
    }

    Map discoverNssAndTestsToRun(String packageId) {
        def packageMetadata = testCatalogue.loadPackageMetadata(packageId)
        Map nsAndTestsMapping = [:]

        packageMetadata.networkServices.each { ns ->
            testCatalogue.findTestsApplicableToNs(ns).each { vnvTest ->
                addNsTestToMap(nsAndTestsMapping, ns, vnvTest)
            }
        }

        packageMetadata.vnvTests.each { vnvTest ->
            testCatalogue.findNsApplicableToTest(vnvTest).each { ns ->
                addNsTestToMap(nsAndTestsMapping, ns, vnvTest)
            }
        }

        nsAndTestsMapping
    }

    void addNsTestToMap(Map nsAndTestsMapping, NetworkService ns, VnvTest vnvTest) {
        List nsTests = nsAndTestsMapping.get(ns)
        if (!nsTests) {
            nsTests = []
            nsAndTestsMapping.put(ns, nsTests)
        }
        if (!nsTests.contains(vnvTest)) {
            nsTests << vnvTest
        }
    }
}
