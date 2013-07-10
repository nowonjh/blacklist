package com.igloo.blacklist.impl;

import java.util.List;
import org.araqne.api.Script;
import org.araqne.api.ScriptContext;
import org.araqne.api.ScriptUsage;
import org.araqne.api.ScriptArgument;

import com.igloo.blacklist.BlackListService;

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
		printList(blacklist.ipList(), args.length > 0 ? args[0] : null);
	}

	@ScriptUsage(description = "vulnerable port list", arguments = { @ScriptArgument(name = "regex", type = "string", description = "regex match port.", optional = true)})
	public void portList(String[] args){
		printList(blacklist.portList(), args.length > 0 ? args[0] : null);
	}
	
	@ScriptUsage(description = "blackList url list", arguments = { @ScriptArgument(name = "regex", type = "string", description = "regex match url.",  optional = true)})
	public void urlList(String[] args){
		printList(blacklist.urlList(), args.length > 0 ? args[0] : null);
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
