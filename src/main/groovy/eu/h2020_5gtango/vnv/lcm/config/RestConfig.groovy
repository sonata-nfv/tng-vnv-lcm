package eu.h2020_5gtango.vnv.lcm.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfig {

    @Value('${app.lcm.bearer.token}')
    def bearerToken

    @Autowired
    BearerAuthorizationInterceptor bearerAuthorizationInterceptor

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        builder.interceptors(bearerAuthorizationInterceptor).build()
    }
}