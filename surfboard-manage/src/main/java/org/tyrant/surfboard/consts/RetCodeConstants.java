package org.tyrant.surfboard.consts;

public class RetCodeConstants {
	//需要继续请求unionId
	public static final int NEED_UNIONID= 10000;
	//小程序登陆所需code为空
	public static final int EMPTY_CODE = 10001;
	//用户信息校验错误
	public static final int USER_INFO_ERROR = 10002;
	//系统中不存在指定unionId的用户
	public static final int NO_USER = 10003;
	//获取unionId失败
	public static final int FAIL_GET_UNIONID = 10004;
	//获取手机号失败
	public static final int FAIL_GET_PHONE = 10005;
	//没有接口权限
	public static final int NO_POWER = 10006;
	//登陆失效
	public static final int SESSION_TIMEOUT = 10007;
	//用户拒绝授权
	public static final int REJECT_AUTH = 10008;
}
