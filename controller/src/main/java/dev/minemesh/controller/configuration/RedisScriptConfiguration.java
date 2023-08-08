package dev.minemesh.controller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class RedisScriptConfiguration {

    @Bean("script-get-counter")
    public RedisScript<Short> registerServiceScript() {
        Resource scriptSource = new ClassPathResource(getScriptPath("get_counter"));
        return RedisScript.of(scriptSource, Short.class);
    }

    private static final String SCRIPTS_TEMPLATE = "scripts/%s.lua";

    private static String getScriptPath(String scriptName) {
        return SCRIPTS_TEMPLATE.formatted(scriptName);
    }

}
