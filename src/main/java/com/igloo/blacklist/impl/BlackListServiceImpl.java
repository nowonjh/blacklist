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
		return cache.isBlackListURL(url);
	}


	public List<String> ipList() {
		return cache.ipList();
	}


	public List<String> portList() {
		return cache.portList();
	}


	public List<String> urlList() {
		return cache.urlList();
	}


	public boolean addIp(String ip) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean addPort(String port) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean addUrl(String url) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean removeIp(String ip) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean removePort(String port) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean removeUrl(String url) {
		// TODO Auto-generated method stub
		return false;
	}

}
