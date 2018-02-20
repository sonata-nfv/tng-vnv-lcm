package eu.h2020_5gtango.vnv.lcm.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfig {
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        builder.build()
    }
}