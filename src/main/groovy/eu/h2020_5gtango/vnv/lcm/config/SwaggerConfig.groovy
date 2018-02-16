package eu.h2020_5gtango.vnv.lcm.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    Docket api() {
        new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(groovy.lang.MetaClass.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex('/api/.*'))
                .build()
                .apiInfo(apiInfo())
    }


    ApiInfo apiInfo() {
        new ApiInfo(
                '5GTANGO VnV Platform Lifecycle Manager REST API',
                'An H2020 5GTANGO project',
                '0.0.1',
                'https://5gtango.eu/',
                null,
                'Apache License 2.0', 'https://github.com/sonata-nfv/tng-vnv-lcm/blob/master/LICENSE', Collections.emptyList())
    }
}