package com.igloo.blacklist.impl;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.araqne.api.Script;
import org.araqne.api.ScriptFactory;

import com.igloo.blacklist.BlackListService;

@Component(name = "blacklist-script-factory")
@Provides
public class BlackListScriptFactory implements ScriptFactory{
	
	@ServiceProperty(name = "alias", value = "blacklist")
	private String alias;
	
	@Requires
	private BlackListService blacklist;
	
	public Script createScript() {
		return new BlackListScript(blacklist);
	}
}
