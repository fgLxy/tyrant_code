package org.tyrant.core.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInfoUtils {

	private static final Logger logger = LoggerFactory.getLogger(ServerInfoUtils.class);

	/**
	 * 此方法描述的是：获得服务器的IP地址
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				NetworkInterface ni = (NetworkInterface) netInterfaces
						.nextElement();

				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = (InetAddress) ips.nextElement();
					if (!ip.isLoopbackAddress()
							&& ip.getHostAddress().matches(
									"(\\d{1,3}\\.){3}\\d{1,3}")) {
						bFindIP = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("获得服务器的IP地址出错:{}", e.getMessage());
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}

	/**
	 * 此方法描述的是：获得服务器的IP地址(多网卡)
	 */
	public static List<String> getLocalIPs() {
		InetAddress ip = null;
		List<String> ipList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces
						.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = ips.nextElement();
					if (!ip.isLoopbackAddress()
							&& ip.getHostAddress().matches(
									"(\\d{1,3}\\.){3}\\d{1,3}")) {
						ipList.add(ip.getHostAddress());
					}
				}
			}
		} catch (Exception e) {
			logger.error("获得服务器的IP地址(多网卡)出错:{}", e.getMessage());
		}
		return ipList;
	}
	
	public static byte[] getMacId() {
		InetAddress ip = null;
		NetworkInterface ni = null;
		try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				ni = netInterfaces.nextElement();
				// ----------特定情况，可以考虑用ni.getName判断
				// 遍历所有ip
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = ips.nextElement();
					if (!ip.isLoopbackAddress() // 非127.0.0.1
							&& ip.getHostAddress().matches(
									"(\\d{1,3}\\.){3}\\d{1,3}")) {
						bFindIP = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("获得服务器的MAC地址出错:{}", e.getMessage());
		}
		if (null != ip) {
			try {
				return ni.getHardwareAddress();
			} catch (SocketException e) {
				logger.error("获得服务器的MAC地址出错:{}", e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 此方法描述的是：获得服务器的MAC地址
	 */
	public static String getMacIdString() {
		return getMacFromBytes(getMacId());
	}
	
	public static List<byte[]> getMacIds() {
		InetAddress ip = null;
		NetworkInterface ni = null;
		List<byte[]> macList = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				ni = netInterfaces.nextElement();
				// ----------特定情况，可以考虑用ni.getName判断
				// 遍历所有ip
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = ips.nextElement();
					if (!ip.isLoopbackAddress() // 非127.0.0.1
							&& ip.getHostAddress().matches(
									"(\\d{1,3}\\.){3}\\d{1,3}")) {
						macList.add(ni.getHardwareAddress());
					}
				}
			}
		} catch (Exception e) {
			logger.error("获得服务器的MAC地址(多网卡)出错:{}", e.getMessage());
		}
		return macList;
	}
	
	/**
	 * 此方法描述的是：获得服务器的MAC地址(多网卡)
	 */
	public static List<String> getMacIdsString() {
		List<String> macList = new ArrayList<>();
		for (byte[] bytes : getMacIds()) {
			macList.add(getMacFromBytes(bytes));
		}
		return macList;
	}

	private static String getMacFromBytes(byte[] bytes) {
		StringBuffer mac = new StringBuffer();
		byte currentByte;
		boolean first = false;
		for (byte b : bytes) {
			if (first) {
				mac.append("-");
			}
			currentByte = (byte) ((b & 240) >> 4);
			mac.append(Integer.toHexString(currentByte));
			currentByte = (byte) (b & 15);
			mac.append(Integer.toHexString(currentByte));
			first = true;
		}
		return mac.toString().toUpperCase();
	}
	
	public static Integer getPid() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	    return Integer.valueOf(runtimeMXBean.getName().split("@")[0]) 
	        .intValue(); 
	}

}