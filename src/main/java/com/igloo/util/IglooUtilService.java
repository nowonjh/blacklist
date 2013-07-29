package com.igloo.util;
import java.util.List;
import java.util.Map;

import com.igloo.userinfo.UserInfo;

public interface IglooUtilService {
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
	
	boolean syncIp(List<String> ipList);
	boolean syncPort(List<String> portList);
	boolean syncUrl(List<String> urlList);
	
	boolean removeUser(String userId);
	boolean addUser(UserInfo user);
	boolean isUser(String value);
	
	String userName(String value);
	String userEmail(String value);
	String userIp(String value);
	String userId(String value);
	List<UserInfo> listUser();
	boolean syncUser(List<Map<String, String>> userList);
}
