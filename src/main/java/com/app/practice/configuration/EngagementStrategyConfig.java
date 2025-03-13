package com.app.practice.configuration;

/*
    Code Author : Ruchir Bisht

    This class is a Spring Configuration class that defines the beans for different engagement strategy services
    based on the active profile (either "dev" or "prod"). It configures the appropriate service implementation
    (DB or Kafka) for each environment to handle user engagement strategies.

    - The @Configuration annotation marks this class as a source of bean definitions for the Spring context.
    - The @Profile annotations specify which beans should be created for which profile (dev or prod).
    - The @Primary annotation indicates that the dbEngagementStrategy bean will be the default bean when multiple
      candidates are available in the "dev" profile.
 */

import com.app.practice.service.EngagementStrategyService;
import com.app.practice.service.VideoStreamService;
import com.app.practice.service.impl.engagement.DBEngagementStrategyServiceImpl;
import com.app.practice.service.impl.engagement.KafkaEngagementStrategyServiceImpl;
import com.app.practice.service.impl.streaming.DBVideoStreamService;
import com.app.practice.service.impl.streaming.KafkaVideoStreamService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class EngagementStrategyConfig {

    /**
     * Bean definition for the EngagementStrategyService implementation to be used in the "dev" profile.
     * This bean will use the DBEngagementStrategyServiceImpl class.
     * The @Primary annotation ensures that this bean is the default choice when multiple beans are available.
     *
     * @param dbEngagementStrategyImpl The DBEngagementStrategyServiceImpl instance injected by Spring.
     * @return An instance of EngagementStrategyService configured for the "dev" environment.
     */
    @Bean
    @Profile("dev")
    @Primary
    public EngagementStrategyService dbEngagementStrategy(DBEngagementStrategyServiceImpl dbEngagementStrategyImpl) {
        return dbEngagementStrategyImpl;
    }

    /**
     * Bean definition for the EngagementStrategyService implementation to be used in the "prod" profile.
     * This bean will use the KafkaEngagementStrategyServiceImpl class to integrate with Kafka in production.
     *
     * @param kafkaEngagementStrategyImpl The KafkaEngagementStrategyServiceImpl instance injected by Spring.
     * @return An instance of EngagementStrategyService configured for the "prod" environment.
     */
    @Bean
    @Profile("prod")
    public EngagementStrategyService kafkaEngagementStrategy(KafkaEngagementStrategyServiceImpl kafkaEngagementStrategyImpl) {
        return kafkaEngagementStrategyImpl;
    }

    @Bean
    @Profile("dev")
    @Primary
    public VideoStreamService dbVideoStreamService(DBVideoStreamService dbVideoStreamService) {
        return dbVideoStreamService;
    }

    @Bean
    @Profile("prod")
    public VideoStreamService kafkaVideoStreamService(KafkaVideoStreamService kafkaVideoStreamService) {
        return kafkaVideoStreamService;
    }


}
