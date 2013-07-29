package com.igloo.util.impl;

import java.util.List;
import org.araqne.api.Script;
import org.araqne.api.ScriptContext;
import org.araqne.api.ScriptUsage;
import org.araqne.api.ScriptArgument;


import com.igloo.userinfo.UserInfo;
import com.igloo.util.IglooCache;
import com.igloo.util.IglooUtilService;

public class IglooScript implements Script {
	private IglooUtilService util;
	private ScriptContext context;
	
	
	public IglooScript(IglooUtilService util){
		this.util = util;
	}
	
	public void setScriptContext(ScriptContext context) {
		this.context = context;
	}
	
	@ScriptUsage(description = "query ip blacklist", arguments = { @ScriptArgument(name = "ip", type = "string", description = "test ip or range") })
	public void isIp(String[] args){
		boolean result = util.isIp(args[0]);
		if(result){
			context.println(args[0] + " is blacklist.");
		}
		else {
			context.println(args[0] + " is not blacklist.");
		}
	}
	
	@ScriptUsage(description = "query vulnerable port", arguments = { @ScriptArgument(name = "port", type = "string", description = "test port or range") })
	public void isPort(String[] args){
		boolean result = util.isPort(args[0]);
		if(result){
			context.println(args[0] + " is vulnerable port.");
		}
		else {
			context.println(args[0] + " is not vulnerable port.");
		}
	}
	@ScriptUsage(description = "query url blacklist", arguments = { @ScriptArgument(name = "url", type = "string", description = "test url.") })
	public void isUrl(String[] args){
		boolean result = util.isUrl(args[0]);
		if(result){
			context.println(args[0] + " is blacklist.");
		}
		else {
			context.println(args[0] + " is not blacklist.");
		}
	}
	@ScriptUsage(description = "blackList ip list", arguments = { @ScriptArgument(name = "regex", type = "string", description = "regex match ip.", optional = true)})
	public void listIp(String[] args){
		printList(util.listIp(), args.length > 0 ? args[0] : null);
	}

	@ScriptUsage(description = "vulnerable port list", arguments = { @ScriptArgument(name = "regex", type = "string", description = "regex match port.", optional = true)})
	public void listPort(String[] args){
		printList(util.listPort(), args.length > 0 ? args[0] : null);
	}
	
	@ScriptUsage(description = "blackList url list", arguments = { @ScriptArgument(name = "regex", type = "string", description = "regex match url.",  optional = true)})
	public void listUrl(String[] args){
		printList(util.listUrl(), args.length > 0 ? args[0] : null);
	}

	@ScriptUsage(description = "add blacklist ip.", arguments = { @ScriptArgument(name = "ip", type = "string", description = "add blacklist ip.")})
	public void addIp(String[] args){
		if(IglooCache.getInstance().isBlackListIP(args[0])){
			context.println(args[0] + " is already added.");
		} else {
			boolean result = util.addIp(args[0]);
			printAddResult(result, args[0]);
		}
	}
	
	@ScriptUsage(description = "add vulnerable port.", arguments = { @ScriptArgument(name = "port", type = "string", description = "add vulnerable port.")})
	public void addPort(String[] args){
		if(IglooCache.getInstance().isBlackListPort(args[0])){
			context.println(args[0] + " is already added.");
		} else {
			boolean result = util.addPort(args[0]);
			printAddResult(result, args[0]);
		}
	}
	
	@ScriptUsage(description = "add blacklist url.", arguments = { @ScriptArgument(name = "url", type = "string", description = "add blacklist url.")})
	public void addUrl(String[] args){
		if(IglooCache.getInstance().isBlackListUrl(args[0])){
			context.println(args[0] + " is already added.");
		} else {
			boolean result = util.addUrl(args[0]);
			printAddResult(result, args[0]);
		}
	}
	
	@ScriptUsage(description = "remove blacklist ip.", arguments = { @ScriptArgument(name = "ip", type = "string", description = "remove blacklist ip.")})
	public void removeIp(String[] args){
		if(IglooCache.getInstance().isBlackListIP(args[0])){
			boolean result = util.removeIp(args[0]);
			printRemoveResult(result, args[0]);
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	@ScriptUsage(description = "remove vulnerable port.", arguments = { @ScriptArgument(name = "port", type = "string", description = "remove vulnerable port.")})
	public void removePort(String[] args){
		if(IglooCache.getInstance().isBlackListPort(args[0])){
			boolean result = util.removePort(args[0]);
			printRemoveResult(result, args[0]);
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	@ScriptUsage(description = "remove blackList url.", arguments = { @ScriptArgument(name = "url", type = "string", description = "remove blackList url.")})
	public void removeUrl(String[] args){
		if(IglooCache.getInstance().isBlackListUrl(args[0])){
			boolean result = util.removeUrl(args[0]);
			printRemoveResult(result, args[0]);
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	
	
	
	/* USER */
	@ScriptUsage(description = "query user info", arguments = { @ScriptArgument(name = "userinfo", type = "string", description = "userId, userName, userIp, userName")})
	public void isUser(String[] args){
		boolean result = util.isUser(args[0]);
		if(result){
			context.println(args[0] + " is user.");
		}
		else {
			context.println(args[0] + " is not user.");
		}
	}
	
	@ScriptUsage(description = "remove user.", arguments = { @ScriptArgument(name = "userId", type = "string", description = "remove userInfo")})
	public void removeUser(String[] args){
		if(IglooCache.getInstance().isUser(args[0])){
			boolean result = util.removeUrl(args[0]);
			printRemoveResult(result, args[0]);
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	
	@ScriptUsage(description = "user list.", arguments = { @ScriptArgument(name = "regex", type = "string", description = "regex match url.",  optional = true)})
	public void listUser(String[] args){
		String regex = args.length > 0 ? args[0] : null;
		
		boolean match = false;
		if(regex != null && !"".equals(regex)){
			match = true;
		}
		int index = 0;
		for(UserInfo user : util.listUser()){
			if(match){
				if(user.getName().matches(regex) ||
						user.getIp().matches(regex) ||
						user.getId().matches(regex) ||
						user.getEmail().matches(regex)){
					context.println(user.toString());
					index++;
				}
			}
			else {
				context.println(user.toString());
				index++;
			}
		}
		context.println(index + " rows.");
	}
	
	@ScriptUsage(description = "add user.", arguments = { 
			@ScriptArgument(name = "userId", type = "string", description = "user id"),
			@ScriptArgument(name = "userName", type = "string", description = "user name"),
			@ScriptArgument(name = "userEmail", type = "string", description = "user email"),
			@ScriptArgument(name = "userIp", type = "string", description = "user ip")})
	public void addUser(String[] args){
		if(IglooCache.getInstance().isUser(args[0])){
			context.println(args[0] + " is already added.");
		}
		else {
			UserInfo user = new UserInfo();
			user.setId(args[0]);
			user.setName(args[1]);
			user.setEmail(args[2]);
			user.setIp(args[3]);
			
			boolean result = util.addUser(user);
			printAddResult(result, user.toString());
		}
	}
	
	@ScriptUsage(description = "check user name.", arguments = { @ScriptArgument(name = "userInfo", type = "string", description = "userId, userName, userIp, userName")})
	public void userName(String[] args){
		if(IglooCache.getInstance().isUser(args[0])){
			context.println("userName = " + util.userName(args[0]));
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	
	@ScriptUsage(description = "check user id.", arguments = { @ScriptArgument(name = "userInfo", type = "string", description = "userId, userName, userIp, userName")})
	public void userId(String[] args){
		if(IglooCache.getInstance().isUser(args[0])){
			context.println("userId = " + util.userId(args[0]));
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	@ScriptUsage(description = "check user name.", arguments = { @ScriptArgument(name = "userInfo", type = "string", description = "userId, userName, userIp, userName")})
	public void userIp(String[] args){
		if(IglooCache.getInstance().isUser(args[0])){
			context.println("userIp = " + util.userIp(args[0]));
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	@ScriptUsage(description = "check user email.", arguments = { @ScriptArgument(name = "userInfo", type = "string", description = "userId, userName, userIp, userName")})
	public void userEmail(String[] args){
		if(IglooCache.getInstance().isUser(args[0])){
			context.println("useremail = " + util.userEmail(args[0]));
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	/* User Info End*/
	
	
	
	
	private void printAddResult(boolean result, String str) {
		if(result){
			context.println(str + " was added.");
		}
		else {
			context.println(str + " failed to add.");
		}
	}
	
	private void printRemoveResult(boolean result, String str) {
		if(result){
			context.println(str + " was removed.");
		}
		else {
			context.println(str + " failed to remove.");
		}
	}
	
	private void printList(List<String> list, String regex) {
		boolean match = false;
		if(regex != null && !"".equals(regex)){
			match = true;
		}
		int index = 0;
		for(String row : list){
			if(match){
				if(row.matches(regex)){
					context.println(row);
					index++;
				}
			}
			else {
				context.println(row);
				index++;
			}
		}
		context.println(index + " rows.");
	}
}
