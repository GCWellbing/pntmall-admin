package com.pntmall.admin.config;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.pntmall.common.utils.Utils;

@Configuration
@EnableCaching
public class CacheConfig {

	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	private transient CacheKeyPrefix cacheKeyPrefix;
	private String springCacheRedisKeyPrefix;
	
	@PostConstruct
	private void onPostConstruct() {
	    if(StringUtils.isEmpty(springCacheRedisKeyPrefix)) {
	        springCacheRedisKeyPrefix = Utils.getActiveProfile();
	    }
        cacheKeyPrefix = cacheName -> springCacheRedisKeyPrefix + "::" + cacheName + "::";
	}
	
	@Bean(name="frequent")
	@Primary
	public CacheManager frequentCacheManager() {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
				.computePrefixWith(cacheKeyPrefix)
				.entryTtl(Duration.ofSeconds(5));
		
		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(conf).build();

		return redisCacheManager;
	}
	
	@Bean(name="everyMinute")
	public CacheManager everyMinuteCacheManager() {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
				.computePrefixWith(cacheKeyPrefix)
				.entryTtl(Duration.ofMinutes(1));
		
		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(conf).build();

		return redisCacheManager;
	}
	
	@Bean(name="every5Minutes")
	public CacheManager every5MinutesCacheManager() {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
				.computePrefixWith(cacheKeyPrefix)
				.entryTtl(Duration.ofMinutes(5));
		
		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(conf).build();

		return redisCacheManager;
	}
	
	@Bean(name="every10Minutes")
	public CacheManager every10MinutesCacheManager() {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
				.computePrefixWith(cacheKeyPrefix)
				.entryTtl(Duration.ofMinutes(10));
		
		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(conf).build();

		return redisCacheManager;
	}
	
	@Bean(name="every15Minutes")
	public CacheManager every15MinutesCacheManager() {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
				.computePrefixWith(cacheKeyPrefix)
				.entryTtl(Duration.ofMinutes(15));
		
		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(conf).build();

		return redisCacheManager;
	}
	
	@Bean(name="everyHalfHour")
	public CacheManager everyHalfHourCacheManager() {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
				.computePrefixWith(cacheKeyPrefix)
				.entryTtl(Duration.ofMinutes(30));
		
		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(conf).build();

		return redisCacheManager;
	}
	
	@Bean(name="everyHour")
	public CacheManager everyHourCacheManager() {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
				.computePrefixWith(cacheKeyPrefix)
				.entryTtl(Duration.ofHours(1));
		
		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(conf).build();

		return redisCacheManager;
	}
	
	@Bean(name="everyDay")
	public CacheManager everyDayCacheManager() {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
				.computePrefixWith(cacheKeyPrefix)
				.entryTtl(Duration.ofDays(1));
		
		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(conf).build();

		return redisCacheManager;
	}
	
	@Bean(name="eternal")
	public CacheManager eternalCacheManager() {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
				.computePrefixWith(cacheKeyPrefix);
		
		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(conf).build();

		return redisCacheManager;
	}
	
}
