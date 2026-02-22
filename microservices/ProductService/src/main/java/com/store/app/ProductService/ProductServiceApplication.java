package com.store.app.ProductService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@ComponentScan("com.store")
public class ProductServiceApplication {

    @Value("${app.threadPoolSize:10}")
    private Integer threadPoolSize;
    @Value("${app.taskQueueSize:100}")
    private Integer taskQueueSize;

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

    @Bean
    public Scheduler jdbcScheduler() {
        return Schedulers.newBoundedElastic(threadPoolSize, taskQueueSize, "jdbc-pool");
    }
}
