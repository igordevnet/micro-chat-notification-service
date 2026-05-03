package com.example.microchatnotificationservice.infrastructure.gateways;

import com.example.microchatnotificationservice.application.gateways.CacheGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisCacheGateway implements CacheGateway {

    private final StringRedisTemplate redisTemplate;

    private static final String TOKEN_PREFIX = "user:token:";

    @Override
    public boolean isBlacklisted(String accessToken) {
        String result = redisTemplate.opsForValue().get(TOKEN_PREFIX + accessToken);
        return result != null;
    }
}