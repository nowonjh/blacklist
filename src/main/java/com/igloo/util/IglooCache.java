package com.igloo.util;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.igloo.blacklist.NetUtil;
import com.igloo.userinfo.UserInfo;

/**
 * 유해정보를 메모리에 보관하고 있는 싱글턴 클래스
 * 
 * @author JH
 *
 */
public class IglooCache {
	Logger logger = LoggerFactory.getLogger(IglooCache.class.getName()); 
	
	/* ip 대역을 첫번째, 두번째로 나누어 트리구조로 데이터를 가지고있는다.*/
	private Map<String, LinkedHashMap<String, LinkedHashMap<String, long[]>>> blacklistIp;
	private Map<Integer, LinkedHashMap<String, int[]>> vulnPort;
	private List<String> blacklistUrl;
	
	/* 사용자 정보 */
	private Map<String, List<Integer>> userKeys;
	private Map<Integer, LinkedHashMap<Integer, UserInfo>> userMap;
	private int userIndex;
	private final byte USER_HASH = 7;
	
	private static IglooCache instance;
	
	
	/**
	 * 해당 싱글톤 인스턴스를 반환
	 * @return
	 */
	public static IglooCache getInstance() {
		if(instance == null){
			synchronized (IglooCache.class) {
				instance = new IglooCache();
			}
		}
		return instance;
	}
	
	/**
	 * 기본 생성자
	 */
	public IglooCache(){
		initCache();
	}

	/* cache instance 를 초기화 한다. */
	public void initCache(){
		/* thread safety map */
		blacklistIp = Collections.synchronizedMap(new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, long[]>>>());
		vulnPort = Collections.synchronizedMap(new LinkedHashMap<Integer, LinkedHashMap<String, int[]>>());
		blacklistUrl = Collections.synchronizedList(new LinkedList<String>());
		
		/* 사용자 정보 */
		userKeys = Collections.synchronizedMap(new LinkedHashMap<String, List<Integer>>());
		userMap = Collections.synchronizedMap(new LinkedHashMap<Integer, LinkedHashMap<Integer, UserInfo>>());
		userIndex = 0;
	}
	
	
	public boolean addUser(UserInfo user){
		if(user.getId() == null || "".equals(user.getId())){
			return false;
		}
		
		int user_key = userIndex++;
		int hash_key = user_key % USER_HASH;
		
		if(userMap.get(hash_key) == null){
			userMap.put(hash_key, new LinkedHashMap<Integer, UserInfo>());
		}
		userMap.get(hash_key).put(user_key, user);
		
		/* 사용자 이름은 중복이 있을수 있음 */
		List<Integer> keyList = new LinkedList<Integer>();
		if(userKeys.get(user.getName()) != null){
			keyList = userKeys.get(user.getName());
		}
		keyList.add(user_key);
		
		userKeys.put(user.getId(), keyList);
		userKeys.put(user.getName(), keyList);
		userKeys.put(user.getEmail(), keyList);
		
		if(user.getIp() != null){
			userKeys.put(user.getIp(), keyList);
		}
		return true;
	}
	
	public boolean removeUser(String userId){
		List<Integer> user_keys = userKeys.get(userId);
		if(user_keys.size() == 0){
			return false;
		}
		
		int user_key = user_keys.get(0);
		int hash_key = user_key % USER_HASH;
		
		UserInfo userInfo = null;
		
		/* 사용자정보 맵에서 삭제 */
		if(userMap.get(hash_key).get(user_key) != null){
			userInfo = userMap.get(hash_key).get(user_key);
			userMap.get(hash_key).remove(user_key);
		}
		if(userMap.get(hash_key).size() == 0){
			userMap.remove(hash_key);
		}
		
		/* 사용자키 맵에서 삭제 */
		if(userKeys.get(userInfo.getName()) != null){
			userKeys.remove(userInfo.getName());
		}
		if(userKeys.get(userInfo.getEmail()) != null){
			userKeys.remove(userInfo.getEmail());
		}
		if(userKeys.get(userInfo.getId()) != null){
			userKeys.remove(userInfo.getId());
		}
		if(userKeys.get(userInfo.getIp()) != null){
			userKeys.remove(userInfo.getIp());
		}
		return true;
	}
	
	
	public boolean isUser(String value){
		if(userKeys.get(value) != null){
			return true;
		}
		return false;
	}
	
	public String getEmail(String value){
		if(!isUser(value)){
			return null;
		}
		
		int user_key = userKeys.get(value).get(0);
		int hash_key = user_key % USER_HASH;
		
		if(userMap.get(hash_key) == null){
			return null;
		}
		else {
			return userMap.get(hash_key).get(user_key).getEmail();
		}
	}
	
	public String getId(String value){
		if(!isUser(value)){
			return null;
		}
		
		int user_key = userKeys.get(value).get(0);
		int hash_key = user_key % USER_HASH;
		
		if(userMap.get(hash_key) == null){
			return null;
		}
		else {
			return userMap.get(hash_key).get(user_key).getId();
		}
	}
	
	public String getIp(String value){
		if(!isUser(value)){
			return null;
		}
		
		int user_key = userKeys.get(value).get(0);
		int hash_key = user_key % USER_HASH;
		
		if(userMap.get(hash_key) == null){
			return null;
		}
		else {
			return userMap.get(hash_key).get(user_key).getIp();
		}
	}
	
	public String getName(String value){
		if(!isUser(value)){
			return null;
		}
		
		int user_key = userKeys.get(value).get(0);
		int hash_key = user_key % USER_HASH;
		
		if(userMap.get(hash_key) == null){
			return null;
		}
		else {
			return userMap.get(hash_key).get(user_key).getName();
		}
	}
	
	
	/**
	 * IP를 cache에 추가한다.
	 * @param ip
	 */
	public boolean addIp(String ip) {
		if(ip == null || "".equals(ip)){
			return false;
		}
		
		try {
			long[] realip = NetUtil.getRealIPWithRange(ip);
			String[] key = ip.split("\\.");
			
			if(blacklistIp.get(key[0]) == null){
				blacklistIp.put(key[0], new LinkedHashMap<String, LinkedHashMap<String, long[]>>());
			}
			if(blacklistIp.get(key[0]).get(key[1]) == null){
				blacklistIp.get(key[0]).put(key[1], new LinkedHashMap<String, long[]>());
			}
			blacklistIp.get(key[0]).get(key[1]).put(ip, realip);
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 취약포트를 cache에 추가한다.
	 * @param port
	 */
	public boolean addPort(String port) {
		if(port == null || "".equals(port)){
			return false;
		}
		
		try {
			int[] rangePort = new int[2];
			
			if(port.indexOf("~") > -1){	
				rangePort[0] = Integer.parseInt(port.split("~")[0]);
				rangePort[1] = Integer.parseInt(port.split("~")[1]);
			} else {
				rangePort[0] = Integer.parseInt(port);
				rangePort[1] = rangePort[0];
			}
			
			List<Integer> address = new LinkedList<Integer>();
			if(rangePort[0] / 1000 != rangePort[1] / 1000){
				int startKey = rangePort[0] / 1000;
				int endKey = rangePort[1] / 1000;
				while(startKey <= endKey){
					address.add(startKey);
					startKey++;
				}
			}
			else {
				address.add(rangePort[0] / 1000);
			}

			for(Integer i : address){
				if(vulnPort.get(i) == null){
					vulnPort.put(i, new LinkedHashMap<String, int[]>());
				}
				vulnPort.get(i).put(port, rangePort);
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * url을 cache에 추가한다.
	 * @param url
	 */
	public boolean addUrl(String url) {
		if(url == null || "".equals(url)){
			return false;
		}
		blacklistUrl.add(url);
		return true;
	}
	
	
	/**
	 * 해당 ip가 유해ip인지 확인한다.
	 * @param ip
	 * @return
	 */
	public boolean isBlackListIP(String ip){
		long[] realIp = new long[2];
		String[] address = null;
		try {
			realIp = NetUtil.getRealIPWithRange(ip);
			
			address = ip.split("\\.");
		} catch (UnknownHostException e) {
			return false;
		}
		
		if(!blacklistIp.containsKey(address[0])){
			return false;
		}
		if(!blacklistIp.get(address[0]).containsKey(address[1])){
			return false;
		}
		
		Set<String> keys = blacklistIp.get(address[0]).get(address[1]).keySet();
		for(String key : keys){
			long[] cache_realip = blacklistIp.get(address[0]).get(address[1]).get(key);
			Long start = cache_realip[0];
			Long end = cache_realip[1];
			if (start <= realIp[0] && end >= realIp[1]) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 해당 url이 유해url인지 확인한다.
	 * @param url
	 * @return
	 */
	public boolean isBlackListUrl(String url) {
		for (String one_url : blacklistUrl) {
			if(url.contains(one_url)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 해당 port가 취약포트인지 확인한다.
	 * @param port
	 * @return
	 */
	public boolean isBlackListPort(String port) {
		int[] rangePort = new int[2];
		try {
			if(port.indexOf("~") > -1){	
				rangePort[0] = Integer.parseInt(port.split("~")[0]);
				rangePort[1] = Integer.parseInt(port.split("~")[1]);
			} else {
				rangePort[0] = Integer.parseInt(port);
				rangePort[1] = rangePort[0];
			}
			
			List<Integer> address = new LinkedList<Integer>();
			if(rangePort[0] / 1000 != rangePort[1] / 1000){
				int startKey = rangePort[0] / 1000;
				int endKey = rangePort[1] / 1000;
				while(startKey <= endKey){
					address.add(startKey);
					startKey++;
				}
			}
			else {
				address.add(rangePort[0] / 1000);
			}
			
			for(Integer i : address){
				if(vulnPort.containsKey(i)){
					for(String key : vulnPort.get(i).keySet()){
						Integer start = vulnPort.get(i).get(key)[0];
						Integer end = vulnPort.get(i).get(key)[1];
						if (start <= rangePort[0] && end >= rangePort[1]) {
							return true;
						}
					}
				}
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	}
	
	/**
	 * 유해IP 리스트를 반환
	 * @return
	 */
	public List<String> listIp(){
		List<String> list = new LinkedList<String>();
		for(String first_key : blacklistIp.keySet()){
			for(String second_key : blacklistIp.get(first_key).keySet()){
				list.addAll(blacklistIp.get(first_key).get(second_key).keySet());
			}
		}
		return list;
	}
	
	/**
	 * 유해 port 리스트를 반환
	 * @return
	 */
	public List<String> listPort(){
		List<String> list = new LinkedList<String>();
		for(Integer key : vulnPort.keySet()){
			list.addAll(vulnPort.get(key).keySet());
		}
		return list;
	}
	
	public List<UserInfo> listUser(){
		List<UserInfo> list = new LinkedList<UserInfo>();
		
		for(int first_key : userMap.keySet()){
			list.addAll(userMap.get(first_key).values());
		}
		return list;
	}
	
	/**
	 * 유해url 리스트를 반환
	 * @return
	 */
	public List<String> listUrl(){
		return blacklistUrl;
	}
	
	
	/**
	 * cache 에서 ip 를 삭제한다.
	 * @param ip
	 * @return
	 */
	public boolean removeIp(String ip){
		String[] key = ip.split("\\.");
		
		if(!blacklistIp.containsKey(key[0])){
			return false;
		}
		if(!blacklistIp.get(key[0]).containsKey(key[1])){
			return false;
		}
		if(!blacklistIp.get(key[0]).get(key[1]).containsKey(ip)){
			return false;
		}
		
		blacklistIp.get(key[0]).get(key[1]).remove(ip);
		
		if(blacklistIp.get(key[0]).get(key[1]).size() == 0){
			blacklistIp.get(key[0]).remove(key[1]);
		}
		if(blacklistIp.get(key[0]).size() == 0){
			blacklistIp.remove(key[0]);
		}
		return true;
	}
	
	public boolean removePort(String port){
		if(port == null || "".equals(port)){
			return false;
		}
		
		try {
			int[] rangePort = new int[2];
			
			if(port.indexOf("~") > -1){	
				rangePort[0] = Integer.parseInt(port.split("~")[0]);
				rangePort[1] = Integer.parseInt(port.split("~")[1]);
			} else {
				rangePort[0] = Integer.parseInt(port);
				rangePort[1] = rangePort[0];
			}
			
			List<Integer> address = new LinkedList<Integer>();
			if(rangePort[0] / 1000 != rangePort[1] / 1000){
				int startKey = rangePort[0] / 1000;
				int endKey = rangePort[1] / 1000;
				while(startKey <= endKey){
					address.add(startKey);
					startKey++;
				}
			}
			else {
				address.add(rangePort[0] / 1000);
			}

			for(Integer i : address){
				if(vulnPort.get(i) != null){
					if(vulnPort.get(i).containsKey(port)){
						vulnPort.get(i).remove(port);
					}
					if(vulnPort.get(i).size() == 0){
						vulnPort.remove(i);
					}
				}
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	public boolean removeUrl(String url){
		int i = blacklistUrl.indexOf(url);
		if(i == -1){
			return false;
		}
		else {
			blacklistUrl.remove(i);
		}
		return true;
	}
	
	public boolean syncIp(List<String> ipList){
		blacklistIp = Collections.synchronizedMap(new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, long[]>>>());
		
		for(String ip : ipList){
			this.addIp(ip);
		}
		return true;
	}
	
	public boolean syncPort(List<String> portList){
		vulnPort = Collections.synchronizedMap(new LinkedHashMap<Integer, LinkedHashMap<String, int[]>>());
		
		for(String port : portList){
			this.addPort(port);
		}
		return true;
	}
	
	public boolean syncUrl(List<String> urlList){
		blacklistUrl = Collections.synchronizedList(new LinkedList<String>());
		
		for(String url : urlList){
			this.addUrl(url);
		}
		return true;
	}

	public boolean syncUser(List<Map<String, String>> userList) {
		userKeys = Collections.synchronizedMap(new LinkedHashMap<String, List<Integer>>());
		userMap = Collections.synchronizedMap(new LinkedHashMap<Integer, LinkedHashMap<Integer, UserInfo>>());
		userIndex = 0;
		for(Map<String, String> user : userList){
			UserInfo userinfo = new UserInfo();
			userinfo.setEmail(user.get("email"));
			userinfo.setName(user.get("name"));
			userinfo.setId(user.get("id"));
			userinfo.setIp(user.get("ip"));
			this.addUser(userinfo);
		}
		return true;
	}
}
