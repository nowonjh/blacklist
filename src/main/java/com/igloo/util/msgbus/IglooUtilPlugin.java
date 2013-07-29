package com.igloo.util.msgbus;

import java.util.List;
import java.util.Map;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Requires;
import org.araqne.msgbus.Request;
import org.araqne.msgbus.Response;
import org.araqne.msgbus.handler.MsgbusMethod;
import org.araqne.msgbus.handler.MsgbusPlugin;

import com.igloo.userinfo.UserInfo;
import com.igloo.util.IglooUtilService;

@MsgbusPlugin
@Component(name = "iglooutil-plugin")
public class IglooUtilPlugin {

	@Requires
	private IglooUtilService util;
	
	/* 
	 * blacklist test 
	 */
	@MsgbusMethod
	public void isIp(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.isIp(value.toString()));
	}
	
	@MsgbusMethod
	public void isPort(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.isPort(value.toString()));
	}
	
	@MsgbusMethod
	public void isUrl(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.isUrl(value.toString()));
	}
	
	/* 
	 * blacklist getList 
	 */
	@MsgbusMethod
	public void listIp(Request req, Response resp){
		resp.put("result", util.listIp());
	}
	
	@MsgbusMethod
	public void listPort(Request req, Response resp){
		resp.put("result", util.listPort());
	}
	
	@MsgbusMethod
	public void listUrl(Request req, Response resp){
		resp.put("result", util.listUrl());
	}
	
	/* 
	 * add blacklist 
	 */
	@MsgbusMethod
	public void addIp(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.addIp(value));
	}
	
	@MsgbusMethod
	public void addPort(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.addPort(value));
	}
	
	@MsgbusMethod
	public void addUrl(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.addUrl(value));
	}
	
	/* 
	 * remove blacklist 
	 */
	@MsgbusMethod
	public void removeIp(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.removeIp(value));
	}
	
	@MsgbusMethod
	public void removePort(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.removePort(value));
	}
	
	@MsgbusMethod
	public void removeUrl(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.removeUrl(value));
	}
	
	/*
	 * sync blacklist
	*/
	@MsgbusMethod
	public void syncIp(Request req, Response resp){
		List<String> ipList = (List<String>)req.get("value");
		resp.put("result", util.syncIp(ipList));
	}
	
	@MsgbusMethod
	public void syncPort(Request req, Response resp){
		List<String> portList = (List<String>)req.get("value");
		resp.put("result", util.syncPort(portList));
	}
	
	@MsgbusMethod
	public void syncUrl(Request req, Response resp){
		List<String> urlList = (List<String>)req.get("value");
		resp.put("result", util.syncUrl(urlList));
	}
	
	
	/* user */
	@MsgbusMethod
	public void isUser(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.isUser(value));
	}
	
	@MsgbusMethod
	public void addUser(Request req, Response resp){
		UserInfo value = (UserInfo) req.get("value");
		resp.put("result", util.addUser(value));
	}
	
	@MsgbusMethod
	public void removeUser(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.removeUser(value));
	}
	
	@MsgbusMethod
	public void listUser(Request req, Response resp){
		resp.put("result", util.listIp());
	}
	
	@MsgbusMethod
	public void userName(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.userName(value));
	}
	
	@MsgbusMethod
	public void userIp(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.userIp(value));
	}
	
	@MsgbusMethod
	public void userId(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.userId(value));
	}
	
	@MsgbusMethod
	public void userEmail(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", util.userEmail(value));
	}
	
	@MsgbusMethod
	public void syncUser(Request req, Response resp){
		List<Map<String, String>> userList = (List<Map<String, String>>)req.get("value");
		resp.put("result", util.syncUser(userList));
	}
}
