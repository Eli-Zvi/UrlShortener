package com.eli.urlshortener.config;

import com.eli.urlshortener.urlshortener.UrlResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory){
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig() //prefix/keyserializer/conversion service remain default
                .entryTtl(Duration.ofMinutes(10)) //TTL = 10 - change to env later
                .disableCachingNullValues() //no null values in cache
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new JacksonJsonRedisSerializer<>(UrlResponseDto.class))); // want to cache urldtos

        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(cacheConfiguration)
                .build();
    }
}
