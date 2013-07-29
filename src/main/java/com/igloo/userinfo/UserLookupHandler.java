package com.igloo.userinfo;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.araqne.logdb.LookupHandler;
import org.araqne.logdb.LookupHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.igloo.util.IglooCache;

@Component(name = "userinfo-lookup-handler")
public class UserLookupHandler implements LookupHandler {
	Logger logger = LoggerFactory.getLogger(UserLookupHandler.class.getName());
	
	@Requires
	private LookupHandlerRegistry handlerRegistry;
	private IglooCache cache;
	
	@Validate
	public void start() {
		if(handlerRegistry.getLookupHandler("userinfo") != null){
			handlerRegistry.removeLookupHandler("userinfo");
		}
		handlerRegistry.addLookupHandler("userinfo", this);
		cache = IglooCache.getInstance();
	}
	
	@Invalidate
	public void stop() {
		if (handlerRegistry != null){
			handlerRegistry.removeLookupHandler("userinfo");
		}
			
	}
	
	public Object lookup(String srcField, String dstField, Object value) {
		if (dstField.equals("email")) {
			if(value != null){
				return cache.getEmail(value.toString());
			}
		}
		if (dstField.equals("ip")) {
			if(value != null){
				return cache.getIp(value.toString());
			}
			
		}	
		if (dstField.equals("id")) {
			if(value != null){
				return cache.getId(value.toString());
			}
		}
		if (dstField.equals("name")) {
			if(value != null){
				return cache.getName(value.toString());
			}
		}
		return null;
	}
}

