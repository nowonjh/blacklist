package com.igloo.util.impl;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.araqne.api.Script;
import org.araqne.api.ScriptFactory;

import com.igloo.util.IglooUtilService;


@Component(name = "iglooutil-script-factory")
@Provides
public class IglooUtilScriptFactory implements ScriptFactory {
	
	@ServiceProperty(name = "alias", value = "iglooutil")
	private String alias;
	
	@Requires
	private IglooUtilService util;
	
	public Script createScript() {
		return new IglooScript(util);
	}
}
