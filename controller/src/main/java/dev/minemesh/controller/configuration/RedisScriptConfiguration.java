package dev.minemesh.controller.configuration;

import dev.minemesh.servicediscovery.common.RegisteredService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class RedisScriptConfiguration {

    private static final String SCRIPTS_TEMPLATE = "script/%s.lua";

    @Bean("script-register-service")
    public RedisScript<RegisteredService> registerServiceScript() {
        Resource scriptSource = new ClassPathResource(getScriptPath("register_service"));
        return RedisScript.of(scriptSource, RegisteredService.class);
    }

    @Bean("script-unregister-service")
    public RedisScript<RegisteredService> unregisterServiceScript() {
        Resource scriptSource = new ClassPathResource(getScriptPath("unregister_service"));
        return RedisScript.of(scriptSource, RegisteredService.class);
    }

    private static String getScriptPath(String scriptName) {
        return SCRIPTS_TEMPLATE.formatted(scriptName);
    }

}
