package com.pl.vask.memorius.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MemoriusClient.class)
@EnableConfigurationProperties(MemoriusProperties.class)
public class MemoriusAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MemoriusClient memoriusClient(MemoriusProperties props) {
        return new MemoriusClientImpl(props.getHost(), props.getPort());
    }
}
