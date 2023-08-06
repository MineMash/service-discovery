package dev.minemesh.controller.configuration;

import dev.minemesh.controller.model.ServiceModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean("template-service-model")
    public ReactiveRedisTemplate<String, ServiceModel> reactiveRedisTemplateServiceModel(ReactiveRedisConnectionFactory connectionFactory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<ServiceModel> valueSerializer = new Jackson2JsonRedisSerializer<>(ServiceModel.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, ServiceModel> builder = RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, ServiceModel> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

    @Bean("template-string-string")
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplateString(ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveRedisTemplate<>(
                connectionFactory,
                RedisSerializationContext.fromSerializer(RedisSerializer.string())
        );
    }

    @Bean
    public ReactiveValueOperations<String, ServiceModel> reactiveValueOperations(
            @Qualifier("template-service-model") ReactiveRedisTemplate<String, ServiceModel> reactiveRedisTemplate) {
        return reactiveRedisTemplate.opsForValue();
    }

}
