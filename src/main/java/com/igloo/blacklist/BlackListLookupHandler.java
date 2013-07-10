package com.igloo.blacklist;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.araqne.logdb.LookupHandler;
import org.araqne.logdb.LookupHandlerRegistry;

@Component(name = "blacklist-lookup-handler")
public class BlackListLookupHandler implements LookupHandler {

	@Requires
	private LookupHandlerRegistry handlerRegistry;
	private CacheBlacklist cache;
	
	@Validate
	public void start() {
		handlerRegistry.addLookupHandler("blacklist", this);
		cache = CacheBlacklist.getInstance();
	}

	@Invalidate
	public void stop() {
		if (handlerRegistry != null)
			handlerRegistry.removeLookupHandler("blacklist");
	}

	public Object lookup(String srcField, String dstField, Object value) {
		if (dstField.equals("ip")) {
			if(value != null){
				return cache.isBlackListIP(value.toString());
			}
		}
		if (dstField.equals("url")) {
			if(value != null){
				return cache.isBlackListURL(value.toString());
			}
			
		}	
		if (dstField.equals("port")) {
			if(value != null){
				return cache.isBlackListPort(value.toString());
			}
		}
		return false;
	}

}