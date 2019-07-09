package com.recommendation.cache;

import org.springframework.stereotype.Component;

@Component
public interface CacheManager {
    void save(Object o);

}
