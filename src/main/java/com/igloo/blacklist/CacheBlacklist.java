package com.igloo.blacklist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CacheBlacklist {
	private Map<String, long[]> blacklistIp;
	private Map<String, int[]> vulnPort;
	private List<String> blacklistUrl;
	
	
	
	private static CacheBlacklist instance;
	
	static{
		if(instance == null){
			instance = new CacheBlacklist();
		}
	}
	
	public static CacheBlacklist getInstance() {
		return instance;
	}
	
	public CacheBlacklist(){
		blacklistIp = new LinkedHashMap<String, long[]>();
		vulnPort = new LinkedHashMap<String, int[]>();
		blacklistUrl = new LinkedList<String>();
		
		RandomAccessFile br = null;
		try {
			br = new RandomAccessFile("blacklist.csv", "r");
			
			String line = "";
			while((line = br.readLine()) != null){
				String[] arr = line.split("\t");
				
				if("ip".equals(arr[0])){
					long[] realip = NetUtil.getRealIPWithRange(arr[1]);
					blacklistIp.put(arr[1], realip);
				}
				
				else if("port".equals(arr[0])){
					int[] rangePort = new int[2];
					if(arr[1].indexOf("~") > -1){	
						rangePort[0] = Integer.parseInt(arr[1].split("~")[0]);
						rangePort[1] = Integer.parseInt(arr[1].split("~")[1]);
					} else {
						rangePort[0] = Integer.parseInt(arr[1]);
						rangePort[1] = rangePort[0];
					}
					vulnPort.put(arr[1], rangePort);
				}
				
				else if("url".equals(arr[0])){
					blacklistUrl.add(arr[1]);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isBlackListIP(String ip){
		long[] realIp = new long[2];
		boolean flag = false;
		
		try {
			realIp = NetUtil.getRealIPWithRange(ip);
		} catch (UnknownHostException e) {
			return false;
		}
		
		Set<String> keys = blacklistIp.keySet();
		for(String key : keys){
			Long start = blacklistIp.get(key)[0];
			Long end = blacklistIp.get(key)[1];
			
			if (realIp[0] == realIp[1]) {
				if (start <= realIp[0] && end >= realIp[1]) {
					flag = true;
				}
			}
			else {
				if (start <= realIp[0] && end >= realIp[1] || start <= realIp[0] && end >= realIp[1]) {
					flag = true;
				}
			}
		}
		return flag;
	}
	
	
	public boolean isBlackListURL(String url) {
		for (String one_url : blacklistUrl) {
			if(url.contains(one_url)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isBlackListPort(String port) {
		int[] rangePort = new int[2];
		boolean flag = false;
		
		try {
			if(port.indexOf("~") > -1){
				rangePort[0] = Integer.parseInt(port.split("~")[0]);
				rangePort[1] = Integer.parseInt(port.split("~")[1]);
			} else {
				rangePort[0] = Integer.parseInt(port);
				rangePort[1] = rangePort[0];
			}

			Set<String> keys = vulnPort.keySet();
			for(String key : keys){
				Integer start = vulnPort.get(key)[0];
				Integer end = vulnPort.get(key)[1];
				
				if (rangePort[0] == rangePort[1]) {
					if (start <= rangePort[0] && end >= rangePort[1]) {
						flag = true;
					}
				}
				else {
					if (start <= rangePort[0] && end >= rangePort[1] || start <= rangePort[0] && end >= rangePort[1]) {
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public List<String> ipList(){
		List<String> list = new LinkedList<String>();
		list.addAll(blacklistIp.keySet());
		return list;
	}
	
	public List<String> portList(){
		List<String> list = new LinkedList<String>();
		list.addAll(vulnPort.keySet());
		return list;
	}
	
	public List<String> urlList(){
		return blacklistUrl;
	}
}
