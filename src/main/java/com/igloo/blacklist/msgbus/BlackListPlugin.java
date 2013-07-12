package com.igloo.blacklist.msgbus;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Requires;
import org.araqne.msgbus.Request;
import org.araqne.msgbus.Response;
import org.araqne.msgbus.handler.MsgbusMethod;
import org.araqne.msgbus.handler.MsgbusPlugin;

import com.igloo.blacklist.BlackListService;

@MsgbusPlugin
@Component(name = "blacklist-plugin")
public class BlackListPlugin {

	@Requires
	private BlackListService blacklist;
	
	/* 
	 * blacklist test 
	 */
	@MsgbusMethod
	public void isIp(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", blacklist.isIp(value.toString()));
	}
	
	@MsgbusMethod
	public void isPort(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", blacklist.isPort(value.toString()));
	}
	
	@MsgbusMethod
	public void isUrl(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", blacklist.isUrl(value.toString()));
	}
	
	/* 
	 * blacklist getList 
	 */
	@MsgbusMethod
	public void listIp(Request req, Response resp){
		resp.put("result", blacklist.listIp());
	}
	
	@MsgbusMethod
	public void listPort(Request req, Response resp){
		resp.put("result", blacklist.listPort());
	}
	
	@MsgbusMethod
	public void listUrl(Request req, Response resp){
		resp.put("result", blacklist.listUrl());
	}
	
	/* 
	 * add blacklist 
	 */
	@MsgbusMethod
	public void addIp(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", blacklist.addIp(value));
	}
	
	@MsgbusMethod
	public void addPort(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", blacklist.addPort(value));
	}
	
	@MsgbusMethod
	public void addUrl(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", blacklist.addUrl(value));
	}
	
	/* 
	 * remove blacklist 
	 */
	@MsgbusMethod
	public void removeIp(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", blacklist.removeIp(value));
	}
	
	@MsgbusMethod
	public void removePort(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", blacklist.removePort(value));
	}
	
	@MsgbusMethod
	public void removeUrl(Request req, Response resp){
		String value = req.getString("value");
		resp.put("result", blacklist.removeUrl(value));
	}
}
