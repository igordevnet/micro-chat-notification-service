package com.example.microchatnotificationservice.application.gateways;

public interface CacheGateway {
    boolean isBlacklisted(String accessToken);
}
