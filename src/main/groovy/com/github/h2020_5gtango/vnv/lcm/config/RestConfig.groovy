package com.github.h2020_5gtango.vnv.lcm.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfig {

    @Autowired
    BearerAuthorizationInterceptor bearerAuthorizationInterceptor

    @Bean
    RestTemplate restTemplateWithAuth(RestTemplateBuilder builder) {
        builder.interceptors(bearerAuthorizationInterceptor).build()
    }

    @Bean
    RestTemplate restTemplateWithoutAuth(RestTemplateBuilder builder) {
        builder.build()
    }
}