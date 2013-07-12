package com.igloo.blacklist.impl;

import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;

import com.igloo.blacklist.BlackListService;
import com.igloo.blacklist.CacheBlacklist;

@Component(name = "blacklist-service")
@Provides
public class BlackListServiceImpl implements BlackListService{

	private CacheBlacklist cache;
	
	public BlackListServiceImpl(){
		cache = CacheBlacklist.getInstance();
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
}
