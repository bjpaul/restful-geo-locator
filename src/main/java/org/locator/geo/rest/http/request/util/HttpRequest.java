package org.locator.geo.rest.http.request.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpRequest {
	private @Autowired HttpServletRequest request;
	
	public String getClientIP() {
		
		String ip = "";
		if (request.getHeader("my-ip") != null) {
			ip = request.getHeader("my-ip");
		} else {
			ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
					if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
						ip = request.getHeader("HTTP_X_FORWARDED_FOR");
						if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
							ip = request.getHeader("HTTP_X_FORWARDED");
							if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
								ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
								if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
									ip = request.getHeader("HTTP_CLIENT_IP");
									if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
										ip = request.getHeader("HTTP_FORWARDED_FOR");
										if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
											ip = request.getHeader("HTTP_FORWARDED");
											if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
												ip = request.getHeader("HTTP_VIA");
												if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
													ip = request.getHeader("REMOTE_ADDR");
													if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
														ip = request.getRemoteAddr();
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return ip != null ? ip.split(",")[0] : "";
	}

}
