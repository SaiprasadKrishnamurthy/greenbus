package com.sai.greenbus;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sai.greenbus.model.BusRoute;
import com.sai.greenbus.service.RouteInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by saipkri on 18/08/17.
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
@EnableTransactionManagement
@EnableCaching
public class RoutePlannerApp {

    private static final Logger log = LoggerFactory.getLogger(RoutePlannerApp.class);

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RouteInfoService routeInfoService;

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public CacheManager cacheManager(final HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            System.out.println(routeInfoService);
            System.out.println("\n\n\n");
            List<BusRoute> br = routeInfoService.findRoutes("okkiyam", "srp");
            System.out.println(br);
            System.out.println(" ------------------ ");
            routeInfoService.findRoutes("okkiyam", "srp");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(RoutePlannerApp.class);
    }
}
