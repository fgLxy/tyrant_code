package org.tyrant.surfboard.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tyrant.core.utils.JSONUtils;
import org.tyrant.dao.Idao;
import org.tyrant.surfboard.consts.Constants;
import org.tyrant.surfboard.consts.DictConstants;
import org.tyrant.surfboard.dto.AuthInfo;
import org.tyrant.surfboard.entity.UserEntity;
import org.tyrant.surfboard.utils.CookieUtils;

import redis.clients.jedis.JedisCommands;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Sets;

/**
 * 鉴权服务
 * @author xiaoyangliu
 *
 */
@Service
public class AuthService {
	Logger log = LoggerFactory.getLogger(AuthService.class);
	@Autowired
	Idao dao;
	@Autowired
	JedisCommands jedisService;

	public AuthInfo auth(String openId, String sessionKey, String unionId) throws SQLException {
		String token = UUID.randomUUID().toString();
		CookieUtils.addCookie(DictConstants.COOKIE_TOKEN_KEY, token);
		AuthInfo auth = new AuthInfo();
		auth.setToken(token);
		auth.setOpenId(openId);
		auth.setSessionKey(sessionKey);
		auth.setUnionId(unionId);
		if (StringUtils.isEmpty(unionId)) {
			auth.setAuthFlag(false);
			setAuthToRedis(auth);
			return auth;
		}
		return auth(auth, unionId);
	}
	
	public AuthInfo auth(AuthInfo auth, String unionId) throws SQLException {
		String sql = "select * from sb_user where union_id=?";
		UserEntity user = dao.queryObject(UserEntity.class, sql, unionId);
		auth.setAuthFlag(user != null);
		if (auth.getAuthFlag()) {
			auth = doLogin(auth, user);
		}
		setAuthToRedis(auth);
		return auth;
	}

	private void setAuthToRedis(AuthInfo auth) {
		jedisService.set(DictConstants.SESSION_PREFFIX + auth.getToken(), JSONUtils.toJson(auth));
		refreshValidTime();
	}

	private AuthInfo doLogin(AuthInfo auth, UserEntity user) throws SQLException {
		BeanUtils.copyProperties(user, auth);
		auth.setUserId(user.getId());
		auth.setAuthFlag(true);
		List<String> transCodes = loadUserTransCode(user.getId());
		auth.setTransCodes(transCodes != null ? Sets.newHashSet(transCodes) : Sets.newHashSet());
		return auth;
	}

	public AuthInfo linkByPhoneNumber(AuthInfo auth, String purePhoneNumber) throws SQLException {
		if (StringUtils.isEmpty(auth.getToken())) {
			return auth;
		}
		String selectSql = "select * from sb_user where phone_number=?";
		UserEntity user = dao.queryObject(UserEntity.class, selectSql, purePhoneNumber);
		if (user == null) {
			return auth;
		}
		if (StringUtils.isEmpty(auth.getUnionId())) {
			log.error("====>通过token获取unionId失败.token:{}", auth.getToken());
			return auth;
		}
		String updateSql = "update sb_user set union_id=?";
		dao.update(updateSql, user.getId(), auth.getUnionId());
		auth = doLogin(auth, user);
		setAuthToRedis(auth);
		return auth;
	}


	public boolean checkUserPower(String transCode) throws Exception {
		AuthInfo auth = getAuthInfo(getToken());
		return auth.getTransCodes().contains(transCode);
	}
	
	private AuthInfo getAuthInfo(String token) {
		String authJson = jedisService.get(DictConstants.SESSION_PREFFIX + token);
		if (StringUtils.isEmpty(authJson)) {
			CookieUtils.removeCookie(DictConstants.COOKIE_TOKEN_KEY);
			AuthInfo auth = new AuthInfo();
			return auth;
		}
		return JSONUtils.parse(authJson, AuthInfo.class);
	}
	
	public AuthInfo getAuthInfo() {
		String token = getToken();
		log.info("======>token:{}", token);
		String authJson = jedisService.get(DictConstants.SESSION_PREFFIX + token);
		if (StringUtils.isEmpty(authJson)) {
			AuthInfo auth = new AuthInfo();
			auth.setToken(token);
			return auth;
		}
		return JSONUtils.parse(authJson, AuthInfo.class);
	}

	private String getToken() {
		CookieUtils.printCookie();
		return CookieUtils.getCookie(DictConstants.COOKIE_TOKEN_KEY);
	}

	public void refreshValidTime() {
		String token = CookieUtils.getCookie(DictConstants.COOKIE_TOKEN_KEY);
		jedisService.expire(DictConstants.SESSION_PREFFIX + token, Constants.LOGIN_VALID_TIME);
	}
	
	private List<String> loadUserTransCode(String userId) throws SQLException {
		String sql = "select t.name from sb_trans t,sb_product p,sb_user_product up,sb_user u "
				+ "where u.id=? and up.user_id=u.id and p.name=up.product_name and t.product_name=p.name";
		return dao.queryOneColumnList(sql, userId);
	}

	public boolean checkLoginState(String transCode) {
		String token = CookieUtils.getCookie(DictConstants.COOKIE_TOKEN_KEY);
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		AuthInfo auth = getAuthInfo(token);
		if (StringUtils.isEmpty(auth.getUnionId())) {
			log.error("====>通过token获取unionId失败.token:{}", token);
			return false;
		}
		return true;
	}

}
