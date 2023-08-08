package dev.minemesh.controller.configuration;

import dev.minemesh.controller.model.ServiceModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean("template-service-model")
    public RedisTemplate<String, ServiceModel> redisTemplateServiceModel(RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(
                connectionFactory,
                RedisSerializer.string(),
                new Jackson2JsonRedisSerializer<>(ServiceModel.class)
        );
    }

    @Bean("template-string")
    public RedisTemplate<String, String> redisTemplateString(RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(
                connectionFactory,
                RedisSerializer.string(),
                RedisSerializer.string()
        );
    }

    private static <K, V> RedisTemplate<K, V> createRedisTemplate(RedisConnectionFactory connectionFactory, RedisSerializer<K> keySerializer, RedisSerializer<V> valueSerializer) {
        RedisTemplate<K, V> template = new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);
        template.setEnableTransactionSupport(true);

        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);

        return template;
    }

}
