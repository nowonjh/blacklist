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
	
	@MsgbusMethod
	public void ipList(Request req, Response resp){
		resp.put("result", blacklist.ipList());
	}
	
	@MsgbusMethod
	public void portList(Request req, Response resp){
		resp.put("result", blacklist.portList());
	}
	
	@MsgbusMethod
	public void urlList(Request req, Response resp){
		resp.put("result", blacklist.urlList());
	}
}
