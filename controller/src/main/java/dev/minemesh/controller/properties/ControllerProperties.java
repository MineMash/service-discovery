package dev.minemesh.controller.properties;


import dev.minemesh.controller.configuration.RedisConfiguration;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "servicediscovery.controller")
public class ControllerProperties {

    private RedisConfiguration redis = new RedisConfiguration();

    @Bean
    public RedisConfiguration getRedis() {
        return this.redis;
    }

    public void setRedis(RedisConfiguration redis) {
        this.redis = redis;
    }

    public static class RedisProperties {

        private String host = "localhost";
        private int port = 6379;
        private String user;
        private String password = Strings.EMPTY;

        public String getHost() {
            return this.host;
        }

        public void setHost(String host) {
            this.host = host;
            System.out.printf("Set redis.host to %s%n", host);
        }

        public int getPort() {
            return this.port;
        }

        public void setPort(int port) {
            this.port = port;
            System.out.printf("Set redis.host to %d%n", port);
        }

        public String getUser() {
            return this.user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
