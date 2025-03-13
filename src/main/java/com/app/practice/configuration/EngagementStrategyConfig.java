package com.app.practice.configuration;

import com.app.practice.service.EngagementStrategyService;
import com.app.practice.service.impl.DBEngagementStrategyServiceImpl;
import com.app.practice.service.impl.KafkaEngagementStrategyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class EngagementStrategyConfig {

    @Bean
    @Profile("dev")
    @Primary
    public EngagementStrategyService dbEngagementStrategy(DBEngagementStrategyServiceImpl dbEngagementStrategyImpl) {
        return dbEngagementStrategyImpl;
    }

    @Bean
    @Profile("prod")
    public EngagementStrategyService kafkaEngagementStrategy(KafkaEngagementStrategyServiceImpl kafkaEngagementStrategyImpl) {
        return kafkaEngagementStrategyImpl;
    }
}
