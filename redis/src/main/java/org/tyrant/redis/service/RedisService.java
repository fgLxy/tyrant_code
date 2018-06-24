package org.tyrant.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

@Service
public class RedisService implements JedisCommands {
	@Autowired
	JedisPool pool;

	public Jedis getJedis() {
		return this.pool.getResource();
	}

	public void release(Jedis jedis) {
		jedis.close();
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.set(key, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String set(String key, String value, String nxxx, String expx,
			long time) {
		Jedis jedis = getJedis();
		try {
			return jedis.set(key, value, nxxx, expx, time);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String set(String key, String value, String nxxx) {
		Jedis jedis = getJedis();
		try {
			return jedis.set(key, value, nxxx);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String get(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.get(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Boolean exists(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.exists(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long persist(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.persist(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String type(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.type(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = getJedis();
		try {
			return jedis.expire(key, seconds);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long pexpire(String key, long milliseconds) {
		Jedis jedis = getJedis();
		try {
			return jedis.pexpire(key, milliseconds);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		Jedis jedis = getJedis();
		try {
			return jedis.expireAt(key, unixTime);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		Jedis jedis = getJedis();
		try {
			return jedis.pexpireAt(key, millisecondsTimestamp);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.ttl(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long pttl(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.pttl(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		Jedis jedis = getJedis();
		try {
			return jedis.setbit(key, offset, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.setbit(key, offset, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Boolean getbit(String key, long offset) {
		Jedis jedis = getJedis();
		try {
			return jedis.getbit(key, offset);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.setrange(key, offset, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		Jedis jedis = getJedis();
		try {
			return jedis.getrange(key, startOffset, endOffset);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String getSet(String key, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.getSet(key, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long setnx(String key, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.setnx(key, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String setex(String key, int seconds, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.setex(key, seconds, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String psetex(String key, long milliseconds, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.psetex(key, milliseconds, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long decrBy(String key, long integer) {
		Jedis jedis = getJedis();
		try {
			return jedis.decrBy(key, integer);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long decr(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.decr(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long incrBy(String key, long integer) {
		Jedis jedis = getJedis();
		try {
			return jedis.incrBy(key, integer);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Double incrByFloat(String key, double value) {
		Jedis jedis = getJedis();
		try {
			return jedis.incrByFloat(key, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.incr(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long append(String key, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.append(key, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String substr(String key, int start, int end) {
		Jedis jedis = getJedis();
		try {
			return jedis.substr(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.hset(key, field, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = getJedis();
		try {
			return jedis.hget(key, field);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.hsetnx(key, field, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = getJedis();
		try {
			return jedis.hmset(key, hash);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = getJedis();
		try {
			return jedis.hmget(key, fields);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		Jedis jedis = getJedis();
		try {
			return jedis.hincrBy(key, field, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Double hincrByFloat(String key, String field, double value) {
		Jedis jedis = getJedis();
		try {
			return jedis.hincrByFloat(key, field, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Boolean hexists(String key, String field) {
		Jedis jedis = getJedis();
		try {
			return jedis.hexists(key, field);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long hdel(String key, String... field) {
		Jedis jedis = getJedis();
		try {
			return jedis.hdel(key, field);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long hlen(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.hlen(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> hkeys(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.hkeys(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public List<String> hvals(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.hvals(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.hgetAll(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long rpush(String key, String... string) {
		Jedis jedis = getJedis();
		try {
			return jedis.rpush(key, string);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long lpush(String key, String... string) {
		Jedis jedis = getJedis();
		try {
			return jedis.lpush(key, string);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long llen(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.llen(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = getJedis();
		try {
			return jedis.lrange(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String ltrim(String key, long start, long end) {
		Jedis jedis = getJedis();
		try {
			return jedis.ltrim(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String lindex(String key, long index) {
		Jedis jedis = getJedis();
		try {
			return jedis.lindex(key, index);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String lset(String key, long index, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.lset(key, index, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long lrem(String key, long count, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.lrem(key, count, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String lpop(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.lpop(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String rpop(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.rpop(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long sadd(String key, String... member) {
		Jedis jedis = getJedis();
		try {
			return jedis.sadd(key, member);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> smembers(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.smembers(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long srem(String key, String... member) {
		Jedis jedis = getJedis();
		try {
			return jedis.srem(key, member);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String spop(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.spop(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> spop(String key, long count) {
		Jedis jedis = getJedis();
		try {
			return jedis.spop(key, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long scard(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.scard(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Boolean sismember(String key, String member) {
		Jedis jedis = getJedis();
		try {
			return jedis.sismember(key, member);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String srandmember(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.srandmember(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public List<String> srandmember(String key, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.srandmember(key, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long strlen(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.strlen(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zadd(String key, double score, String member) {
		Jedis jedis = getJedis();
		try {
			return jedis.zadd(key, score, member);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zadd(String key, double score, String member, ZAddParams params) {
		Jedis jedis = getJedis();
		try {
			return jedis.zadd(key, score, member, params);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		Jedis jedis = getJedis();
		try {
			return jedis.zadd(key, scoreMembers);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers,
			ZAddParams params) {
		Jedis jedis = getJedis();
		try {
			return jedis.zadd(key, scoreMembers, params);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrange(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zrem(String key, String... member) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrem(key, member);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		Jedis jedis = getJedis();
		try {
			return jedis.zincrby(key, score, member);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Double zincrby(String key, double score, String member,
			ZIncrByParams params) {
		Jedis jedis = getJedis();
		try {
			return jedis.zincrby(key, score, member, params);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zrank(String key, String member) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrank(key, member);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zrevrank(String key, String member) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrank(key, member);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrange(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeWithScores(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeWithScores(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zcard(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.zcard(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Double zscore(String key, String member) {
		Jedis jedis = getJedis();
		try {
			return jedis.zscore(key, member);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public List<String> sort(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.sort(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		Jedis jedis = getJedis();
		try {
			return jedis.sort(key, sortingParameters);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zcount(String key, double min, double max) {
		Jedis jedis = getJedis();
		try {
			return jedis.zcount(key, min, max);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zcount(String key, String min, String max) {
		Jedis jedis = getJedis();
		try {
			return jedis.zcount(key, min, max);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByScore(key, min, max);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByScore(key, min, max);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByScore(key, max, min);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByScore(key, min, max, offset, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByScore(key, max, min);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max,
			int offset, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByScore(key, min, max, offset, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min,
			int offset, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByScore(key, max, min, offset, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByScoreWithScores(key, min, max);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByScoreWithScores(key, max, min);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min,
			double max, int offset, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min,
			int offset, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByScore(key, max, min, offset, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByScoreWithScores(key, min, max);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByScoreWithScores(key, max, min);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min,
			String max, int offset, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min, int offset, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByScoreWithScores(key, max, min, offset,
					count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min, int offset, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByScoreWithScores(key, max, min, offset,
					count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		Jedis jedis = getJedis();
		try {
			return jedis.zremrangeByRank(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		Jedis jedis = getJedis();
		try {
			return jedis.zremrangeByScore(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		Jedis jedis = getJedis();
		try {
			return jedis.zremrangeByScore(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zlexcount(final String key, final String min, final String max) {
		Jedis jedis = getJedis();
		try {
			return jedis.zlexcount(key, min, max);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min,
			final String max) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByLex(key, min, max);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min,
			final String max, final int offset, final int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrangeByLex(key, min, max, offset, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByLex(key, max, min);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min,
			int offset, int count) {
		Jedis jedis = getJedis();
		try {
			return jedis.zrevrangeByLex(key, max, min, offset, count);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long zremrangeByLex(final String key, final String min,
			final String max) {
		Jedis jedis = getJedis();
		try {
			return jedis.zremrangeByLex(key, min, max);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.linsert(key, where, pivot, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long lpushx(String key, String... string) {
		Jedis jedis = getJedis();
		try {
			return jedis.lpushx(key, string);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long rpushx(String key, String... string) {
		Jedis jedis = getJedis();
		try {
			return jedis.rpushx(key, string);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> blpop(String arg) {
		Jedis jedis = getJedis();
		try {
			return jedis.blpop(arg);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public List<String> blpop(int timeout, String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.blpop(timeout, key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> brpop(String arg) {
		Jedis jedis = getJedis();
		try {
			return jedis.brpop(arg);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public List<String> brpop(int timeout, String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.brpop(timeout, key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long del(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.del(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public String echo(String string) {
		Jedis jedis = getJedis();
		try {
			return jedis.echo(string);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long move(String key, int dbIndex) {
		Jedis jedis = getJedis();
		try {
			return jedis.move(key, dbIndex);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long bitcount(final String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.bitcount(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long bitcount(final String key, long start, long end) {
		Jedis jedis = getJedis();
		try {
			return jedis.bitcount(key, start, end);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long bitpos(String key, boolean value) {
		Jedis jedis = getJedis();
		try {
			return jedis.bitpos(key, value);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long bitpos(String key, boolean value, BitPosParams params) {
		Jedis jedis = getJedis();
		try {
			return jedis.bitpos(key, value, params);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
		Jedis jedis = getJedis();
		try {
			return jedis.hscan(key, cursor);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public ScanResult<String> sscan(String key, int cursor) {
		Jedis jedis = getJedis();
		try {
			return jedis.sscan(key, cursor);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public ScanResult<Tuple> zscan(String key, int cursor) {
		Jedis jedis = getJedis();
		try {
			return jedis.zscan(key, cursor);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public ScanResult<Map.Entry<String, String>> hscan(final String key,
			final String cursor) {
		Jedis jedis = getJedis();
		try {
			return jedis.hscan(key, cursor);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public ScanResult<Map.Entry<String, String>> hscan(String key,
			String cursor, ScanParams params) {
		Jedis jedis = getJedis();
		try {
			return jedis.hscan(key, cursor, params);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public ScanResult<String> sscan(final String key, final String cursor) {
		Jedis jedis = getJedis();
		try {
			return jedis.sscan(key, cursor);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
		Jedis jedis = getJedis();
		try {
			return jedis.sscan(key, cursor, params);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public ScanResult<Tuple> zscan(final String key, final String cursor) {
		Jedis jedis = getJedis();
		try {
			return jedis.zscan(key, cursor);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
		Jedis jedis = getJedis();
		try {
			return jedis.zscan(key, cursor, params);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long pfadd(final String key, final String... elements) {
		Jedis jedis = getJedis();
		try {
			return jedis.pfadd(key, elements);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public long pfcount(final String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.pfcount(key);
		} finally {
			release(jedis);// 释放jedis
		}
	}

	@Override
	public Long geoadd(String key, double longitude, double latitude,
			String member) {
		return null;
	}

	@Override
	public Long geoadd(String key,
			Map<String, GeoCoordinate> memberCoordinateMap) {
		return null;
	}

	@Override
	public Double geodist(String key, String member1, String member2) {
		return null;
	}

	@Override
	public Double geodist(String key, String member1, String member2,
			GeoUnit unit) {
		return null;
	}

	@Override
	public List<String> geohash(String key, String... members) {
		return null;
	}

	@Override
	public List<GeoCoordinate> geopos(String key, String... members) {
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude,
			double latitude, double radius, GeoUnit unit) {
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude,
			double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member,
			double radius, GeoUnit unit) {
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member,
			double radius, GeoUnit unit, GeoRadiusParam param) {
		return null;
	}

	@Override
	public List<Long> bitfield(String key, String... arguments) {
		Jedis jedis = getJedis();
		try {
			return jedis.bitfield(key, arguments);
		} finally {
			release(jedis);// 释放jedis
		}
	}
}
