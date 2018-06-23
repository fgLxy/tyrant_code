package org.tyrant.surfboard.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;


public class CookieUtils {
	
	static Logger log = LoggerFactory.getLogger(CookieUtils.class);

	public static Cookie[] getCookies() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Cookie[] c = request.getCookies();
		return c;
	}

	public static void saveCookie(Cookie cookie) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = attributes.getResponse();
		response.addCookie(cookie);
	}

	/**
	 * 添加cookie
	 * 
	 * @param name
	 * @param object
	 */
	public static void addCookie(String name, String value) {
		try {

			String v = URLEncoder.encode(value, "UTF-8");

			Cookie cookie = new Cookie(name, v);
			cookie.setPath("/");
			cookie.setMaxAge(Integer.MAX_VALUE);// 设置保存cookie最大时长
			saveCookie(cookie);

		} catch (Exception e) {
			log.error(" -------添加cookie 失败！--------" + e.getMessage());
		}
	}

	/**
	 * 获取cookie
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static <T> T getCookie(String name, Class<T> clazz) {
		try {

			Cookie[] cookies = getCookies();
			String v = null;
			for (int i = 0; i < (cookies == null ? 0 : cookies.length); i++) {
				if ((name).equalsIgnoreCase(cookies[i].getName())) {
					v = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
				}
			}
			if (v != null) {
				return new Gson().fromJson(v, clazz);
			}
		} catch (Exception e) {
			log.error("------获取 clazz Cookie 失败----- " + e.getMessage());
		}
		return null;
	}

	/**
	 * 获取cookie
	 * 
	 * @param name
	 * @return
	 */
	public static String getCookie(String name) {
		try {

			Cookie[] cookies = getCookies();

			for (int i = 0; i < (cookies == null ? 0 : cookies.length); i++) {
				if ((name).equalsIgnoreCase(cookies[i].getName())) {
					return URLDecoder.decode(cookies[i].getValue(), "UTF-8");
				}
			}
		} catch (Exception e) {
			log.error(" --------获取String cookie 失败--------   " + e.getMessage());
		}
		return null;
	}

	/**
	 * 删除cookie
	 * 
	 * @param name
	 */
	public static void removeCookie(String name) {
		try {

			Cookie[] cookies = getCookies();

			for (int i = 0; i < (cookies == null ? 0 : cookies.length); i++) {
				if ((name).equalsIgnoreCase(cookies[i].getName())) {

					Cookie cookie = new Cookie(name, "");
					cookie.setPath("/");
					cookie.setMaxAge(0);// 设置保存cookie最大时长为0，即使其失效
					saveCookie(cookie);
				}
			}

		} catch (Exception e) {
			log.error(" -------删除cookie失败！--------" + e.getMessage());
		}
	}

	public static void printCookie() {
		Cookie[] cookies = getCookies();
		if (cookies == null) {
			log.info("empty cookie");
			return;
		}
		for (Cookie cookie : cookies) {
			log.info("cookie.key:{};value:{}", cookie.getName(),cookie.getValue());
		}
	}
}