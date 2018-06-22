package org.tyrant.core.pojo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class BaseObject {
	
	@Override  
    public String toString() {  
        return ReflectionToStringBuilder.reflectionToString(this);  
    }
	
}
