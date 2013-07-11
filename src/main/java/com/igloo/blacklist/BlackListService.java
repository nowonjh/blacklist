package com.igloo.blacklist;

import java.util.List;

public interface BlackListService {
	boolean isIp(String ip);
	boolean isPort(String port);
	boolean isUrl(String url);
	
	List<String> listIp();
	List<String> listPort();
	List<String> listUrl();
	
	boolean addIp(String ip);
	boolean addPort(String port);
	boolean addUrl(String url);
	
	boolean removeIp(String ip);
	boolean removePort(String port);
	boolean removeUrl(String url);
}
