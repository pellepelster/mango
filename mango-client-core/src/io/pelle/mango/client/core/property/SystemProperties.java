package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;

public class SystemProperties {

	public static IProperty<String> JAVA_VM_INFO = PropertyBuilder.createStringSystemProperty("java.vm.info", "VM information"); 
	
}
