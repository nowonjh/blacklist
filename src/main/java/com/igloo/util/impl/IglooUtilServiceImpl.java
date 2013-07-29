package com.igloo.util.impl;

import java.util.List;
import java.util.Map;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;

import com.igloo.userinfo.UserInfo;
import com.igloo.util.IglooCache;
import com.igloo.util.IglooUtilService;

@Component(name = "iglooutil-service")
@Provides
public class IglooUtilServiceImpl implements IglooUtilService {

	private IglooCache cache;
	
	public IglooUtilServiceImpl(){
		cache = IglooCache.getInstance();
	}

	public boolean isIp(String ip) {
		return cache.isBlackListIP(ip);
	}

	public boolean isPort(String port) {
		return cache.isBlackListPort(port);
	}

	public boolean isUrl(String url) {
		return cache.isBlackListUrl(url);
	}
	
	public List<String> listIp() {
		return cache.listIp();
	}


	public List<String> listPort() {
		return cache.listPort();
	}


	public List<String> listUrl() {
		return cache.listUrl();
	}


	public boolean addIp(String ip) {
		return cache.addIp(ip);
	}


	public boolean addPort(String port) {
		return cache.addPort(port);
	}


	public boolean addUrl(String url) {
		return cache.addUrl(url);
	}


	public boolean removeIp(String ip) { 
		return cache.removeIp(ip);
	}


	public boolean removePort(String port) {
		return cache.removePort(port);
	}


	public boolean removeUrl(String url) {
		return cache.removeUrl(url);
	}


	public boolean syncIp(List<String> ipList) {
		return cache.syncIp(ipList);
	}


	public boolean syncPort(List<String> portList) {
		return cache.syncPort(portList);
	}


	public boolean syncUrl(List<String> urlList) {
		return cache.syncUrl(urlList);
	}


	public boolean removeUser(String userId) {
		return cache.removeUser(userId);
	}


	public boolean addUser(UserInfo user) {
		return cache.addUser(user);
	}


	public boolean isUser(String value) {
		return cache.isUser(value);
	}


	public String userName(String value) {
		return cache.getName(value);
	}


	public String userEmail(String value) {
		return cache.getEmail(value);
	}


	public String userIp(String value) {
		return cache.getIp(value);
	}


	public String userId(String value) {
		return cache.getId(value);
	}

	public List<UserInfo> listUser() {
		return cache.listUser();
	}

	public boolean syncUser(List<Map<String, String>> userList) {
		return cache.syncUser(userList);
	}
}
