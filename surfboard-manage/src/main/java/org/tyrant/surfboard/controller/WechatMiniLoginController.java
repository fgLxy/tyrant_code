package org.tyrant.surfboard.controller;

import java.sql.SQLException;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tyrant.core.constant.GlobalValue;
import org.tyrant.core.utils.JSONUtils;
import org.tyrant.surfboard.consts.RetCodeConstants;
import org.tyrant.surfboard.dto.AuthInfo;
import org.tyrant.surfboard.service.AuthService;
import org.tyrant.surfboard.utils.CookieUtils;
import org.tyrant.surfboard.utils.ResponseUtils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/mini/auth")
public class WechatMiniLoginController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxMaService wxService;
	@Autowired
	private AuthService authService;

	/**
	 * 登陆接口
	 * @throws WxErrorException 
	 * @throws JsonProcessingException 
	 * @throws SQLException 
	 */
	@GetMapping("/login")
	public Map<String, Object> login(String code) throws WxErrorException, JsonProcessingException, SQLException {
		log.info("======>登陆鉴权开始.code:{}", code);
		if (StringUtils.isBlank(code)) {
			log.info("=======>code为空");
			return ResponseUtils.response(RetCodeConstants.EMPTY_CODE, null);
		}

		WxMaJscode2SessionResult session = null;
		try {
			session = this.wxService.getUserService().getSessionInfo(code);
		} catch (Throwable e) {
			log.error("======>code验证失败.异常:{}", e);
			return ResponseUtils.response(GlobalValue.EXCEPTION_STATUS, e.getMessage());
		}
				
		String unionId = session.getUnionid();
		AuthInfo auth = authService.auth(session.getOpenid(), session.getSessionKey(), unionId);
		if (!StringUtils.isEmpty(unionId)) {
			if (auth.getAuthFlag()) {
				log.info("=======>鉴权成功.AuthInfo:{}", JSONUtils.toJson(auth));
				return ResponseUtils.response(GlobalValue.SUCCESS, auth);
			}
			else {
				log.info("====>鉴权失败,用户没有在系统中注册.unionId:{}", unionId);
				return ResponseUtils.response(RetCodeConstants.NO_USER, auth);
			}
		}
		log.info("====>没有获取到unionId.需要用户授权拉取用户详情");
		return ResponseUtils.response(RetCodeConstants.NEED_UNIONID, auth);
	}

	/**
	 * <pre>
	 * 获取用户信息接口
	 * </pre>
	 * @throws JsonProcessingException 
	 * @throws SQLException 
	 */
	@GetMapping("/info")
	public Map<String, Object> info(String signature, String rawData, String encryptedData, String iv) throws JsonProcessingException, SQLException {
		CookieUtils.addCookie("TEST", "testValue");
		AuthInfo auth = authService.getAuthInfo();
		log.info("=====>info接口开始.sessionKey:{},signature:{}.rawData:{},encryptedData:{},iv:{}"
				, auth.getSessionKey(), signature, rawData, encryptedData, iv);
		// 用户信息校验
		if (!this.wxService.getUserService().checkUserInfo(auth.getSessionKey(), rawData,
				signature)) {
			return ResponseUtils.response(RetCodeConstants.USER_INFO_ERROR, null);
		}
		// 解密用户信息
		WxMaUserInfo userInfo = null;
		try {
			userInfo = this.wxService.getUserService().getUserInfo(auth.getSessionKey(), encryptedData, iv);
		} catch (Throwable e) {
			log.error("======>用户信息消息验证失败.异常:{}", e);
			return ResponseUtils.response(GlobalValue.EXCEPTION_STATUS, e.getMessage());
		}
		userInfo.setUnionId(userInfo.getOpenId());
		if (userInfo != null && !StringUtils.isEmpty(userInfo.getUnionId())) {
			auth = authService.auth(auth, userInfo.getUnionId());
			if (auth.getAuthFlag()) {
				log.info("=======>鉴权成功.AuthInfo:{}", JSONUtils.toJson(auth));
				return ResponseUtils.response(GlobalValue.SUCCESS, auth);
			}
			else {
				log.info("====>鉴权失败,unionId在系统中没有关联用户.UserInfo:{}", JSONUtils.toJson(userInfo));
				return ResponseUtils.response(RetCodeConstants.NO_USER, null);
			}
		}
		log.info("获取unionId失败.");
		return ResponseUtils.response(RetCodeConstants.FAIL_GET_UNIONID, null);
	}

	/**
	 * <pre>
	 * 获取用户绑定手机号信息.并尝试与系统用户进行关联
	 * </pre>
	 * @throws JsonProcessingException 
	 * @throws SQLException 
	 */
	@GetMapping("/phone")
	public Map<String, Object> phone(String signature, String rawData, String encryptedData, String iv) throws JsonProcessingException, SQLException {
		AuthInfo auth = authService.getAuthInfo();
		log.info("=====>phone接口开始.sessionKey:{},signature:{}.rawData:{},encryptedData:{},iv:{}"
				, auth.getSessionKey(), signature, rawData, encryptedData, iv);

		// 解密
		WxMaPhoneNumberInfo phoneNoInfo = null;
		try {
			phoneNoInfo = this.wxService.getUserService().getPhoneNoInfo(auth.getSessionKey(), encryptedData, iv);
		} catch (Throwable e) {
			log.error("======>用户手机号消息验证失败.异常:{}", e);
			return ResponseUtils.response(GlobalValue.EXCEPTION_STATUS, e.getMessage());
		}
		
		if (phoneNoInfo != null && !StringUtils.isEmpty(phoneNoInfo.getPurePhoneNumber())) {
			auth = authService.linkByPhoneNumber(auth, phoneNoInfo.getPurePhoneNumber());
			if (auth.getAuthFlag()) {
				log.info("=======>鉴权成功.AuthInfo:{}", JSONUtils.toJson(auth));
				return ResponseUtils.response(GlobalValue.SUCCESS, auth);
			}
			else {
				log.info("====>鉴权失败,手机号不是系统中注册用户.phoneNumber:{}", phoneNoInfo.getPurePhoneNumber());
				return ResponseUtils.response(RetCodeConstants.NO_USER, null);
			}
		}
		return ResponseUtils.response(RetCodeConstants.FAIL_GET_PHONE, null);
	}
}
