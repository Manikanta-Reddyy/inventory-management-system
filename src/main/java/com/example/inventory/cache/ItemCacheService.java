package com.example.inventory.cache;

import com.example.inventory.dto.response.ItemResponseDto;
import com.example.inventory.utils.ItemRedisMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class ItemCacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final String PREFIX_ID = "product:id:";

    public void putItem(ItemResponseDto dto) {
        Map<String, String> hash = ItemRedisMapper.toHash(dto);

        try {
            redisTemplate.opsForHash().putAll(PREFIX_ID + dto.getId(), hash);
            log.info("Redis cached the item with key {}: ", PREFIX_ID + dto.getId());
        } catch (Exception e) {
            log.warn("Redis not cached the item with id {} : ", dto.getId());
        }
    }

    public ItemResponseDto getItem(UUID itemId) {

        try {
            Map<Object, Object> hash = redisTemplate.opsForHash().entries(PREFIX_ID + itemId);
            if (!hash.isEmpty()) {
                log.info("Fetched item with id: {} from Redis cache", itemId);
                return ItemRedisMapper.fromHash(hash);
            }
            log.info("Item id: {} not found in Redis cache", itemId);
        } catch (Exception e) {
            log.warn("Redis unavailable for key {} : {}", PREFIX_ID + itemId, e.getMessage());
        }

        return null;
    }
}
