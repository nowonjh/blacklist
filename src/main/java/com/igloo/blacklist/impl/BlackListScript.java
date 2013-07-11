package com.igloo.blacklist.impl;

import java.util.List;
import org.araqne.api.Script;
import org.araqne.api.ScriptContext;
import org.araqne.api.ScriptUsage;
import org.araqne.api.ScriptArgument;

import com.igloo.blacklist.BlackListService;
import com.igloo.blacklist.CacheBlacklist;

public class BlackListScript implements Script {
	private BlackListService blacklist;
	private ScriptContext context;
	
	
	public BlackListScript(BlackListService blacklist){
		this.blacklist = blacklist;
	}
	
	public void setScriptContext(ScriptContext context) {
		this.context = context;
	}
	
	@ScriptUsage(description = "query ip blacklist", arguments = { @ScriptArgument(name = "ip", type = "string", description = "test ip or range") })
	public void isIp(String[] args){
		boolean result = blacklist.isIp(args[0]);
		if(result){
			context.println(args[0] + " is blacklist.");
		}
		else {
			context.println(args[0] + " is not blacklist.");
		}
	}
	
	@ScriptUsage(description = "query vulnerable port", arguments = { @ScriptArgument(name = "port", type = "string", description = "test port or range") })
	public void isPort(String[] args){
		boolean result = blacklist.isPort(args[0]);
		if(result){
			context.println(args[0] + " is vulnerable port.");
		}
		else {
			context.println(args[0] + " is not vulnerable port.");
		}
	}
	@ScriptUsage(description = "query url blacklist", arguments = { @ScriptArgument(name = "url", type = "string", description = "test url.") })
	public void isUrl(String[] args){
		boolean result = blacklist.isUrl(args[0]);
		if(result){
			context.println(args[0] + " is blacklist.");
		}
		else {
			context.println(args[0] + " is not blacklist.");
		}
	}
	@ScriptUsage(description = "blackList ip list", arguments = { @ScriptArgument(name = "regex", type = "string", description = "regex match ip.", optional = true)})
	public void ipList(String[] args){
		printList(blacklist.listIp(), args.length > 0 ? args[0] : null);
	}

	@ScriptUsage(description = "vulnerable port list", arguments = { @ScriptArgument(name = "regex", type = "string", description = "regex match port.", optional = true)})
	public void portList(String[] args){
		printList(blacklist.listPort(), args.length > 0 ? args[0] : null);
	}
	
	@ScriptUsage(description = "blackList url list", arguments = { @ScriptArgument(name = "regex", type = "string", description = "regex match url.",  optional = true)})
	public void urlList(String[] args){
		printList(blacklist.listUrl(), args.length > 0 ? args[0] : null);
	}
	
	
	
	
	
	
	
	
	@ScriptUsage(description = "add blacklist ip.", arguments = { @ScriptArgument(name = "ip", type = "string", description = "add blacklist ip.")})
	public void addIp(String[] args){
		if(CacheBlacklist.getInstance().isBlackListIP(args[0])){
			context.println(args[0] + " is already added.");
		} else {
			boolean result = blacklist.addIp(args[0]);
			printAddResult(result, args[0]);
		}
	}
	
	@ScriptUsage(description = "add vulnerable port.", arguments = { @ScriptArgument(name = "port", type = "string", description = "add vulnerable port.")})
	public void addPort(String[] args){
		if(CacheBlacklist.getInstance().isBlackListPort(args[0])){
			context.println(args[0] + " is already added.");
		} else {
			boolean result = blacklist.addPort(args[0]);
			printAddResult(result, args[0]);
		}
	}
	
	@ScriptUsage(description = "add blacklist url.", arguments = { @ScriptArgument(name = "url", type = "string", description = "add blacklist url.")})
	public void addUrl(String[] args){
		if(CacheBlacklist.getInstance().isBlackListUrl(args[0])){
			context.println(args[0] + " is already added.");
		} else {
			boolean result = blacklist.addUrl(args[0]);
			printAddResult(result, args[0]);
		}
	}
	
	@ScriptUsage(description = "remove blacklist ip.", arguments = { @ScriptArgument(name = "ip", type = "string", description = "remove blacklist ip.")})
	public void removeIp(String[] args){
		if(CacheBlacklist.getInstance().isBlackListIP(args[0])){
			boolean result = blacklist.removeIp(args[0]);
			printRemoveResult(result, args[0]);
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	@ScriptUsage(description = "remove vulnerable port.", arguments = { @ScriptArgument(name = "port", type = "string", description = "remove vulnerable port.")})
	public void removePort(String[] args){
		if(CacheBlacklist.getInstance().isBlackListPort(args[0])){
			boolean result = blacklist.removePort(args[0]);
			printRemoveResult(result, args[0]);
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	@ScriptUsage(description = "remove blackList url.", arguments = { @ScriptArgument(name = "regex", type = "string", description = "remove blackList url.")})
	public void removeUrl(String[] args){
		if(CacheBlacklist.getInstance().isBlackListUrl(args[0])){
			boolean result = blacklist.removeUrl(args[0]);
			printRemoveResult(result, args[0]);
		}
		else {
			context.println(args[0] + " is not exists.");
		}
	}
	
	
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
