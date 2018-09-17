package com.github.h2020_5gtango.vnv.lcm.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

import java.util.concurrent.Executor

@EnableAsync
@Configuration
class AsyncConfig {

    @Value('${app.pool.size.core}')
    int corePoolSize

    @Value('${app.pool.size.max}')
    int maxPoolSize

    @Value('${app.queue.capacity}')
    int queueCapacity

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("lcm-scheduler-");
        executor.initialize();
        return executor;
    }
}
