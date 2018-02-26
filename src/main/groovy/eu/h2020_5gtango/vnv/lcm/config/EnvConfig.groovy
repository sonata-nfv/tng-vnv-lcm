package eu.h2020_5gtango.vnv.lcm.config

import java.lang.management.ManagementFactory

class EnvConfig {

    static void init() {
        populateStartupTime()
        setupActiveProfile()
    }

    private static void setupActiveProfile() {
        if (!System.properties['spring.profiles.active'] && !System.getenv('SPRING_PROFILES_ACTIVE')) {
            def classPath = System.properties['java.class.path']
            String activeProfiles = (classPath=~/.*test.classes.*/).matches() || (classPath=~/.*classes.test.*/).matches() ? 'test'
                    : (classPath=~/.*production.classes.*/).matches() || (classPath=~/.*classes.main.*/).matches() ? 'local'
                    : 'server'
            try{
                activeProfiles="${activeProfiles},${InetAddress.getLocalHost().getHostName()}"
            }catch (Exception ignore){}
            System.properties['spring.profiles.active'] = activeProfiles
        }
    }

    private static void populateStartupTime() {
        System.properties['info.app.start.time'] = new Date(ManagementFactory.getRuntimeMXBean().getStartTime()).format('yyyy-MM-dd HH:mm:ss z')
    }
}
