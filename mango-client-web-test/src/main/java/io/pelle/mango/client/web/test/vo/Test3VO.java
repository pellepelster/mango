/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.web.test.vo;

import io.pelle.mango.client.base.vo.AttributeDescriptor;
import io.pelle.mango.client.base.vo.BaseVO;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;

public class Test3VO extends BaseVO implements IBaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<Test1VO> ENTITY = new io.pelle.mango.client.base.vo.EntityDescriptor<Test1VO>(Test1VO.class, null, null, null);

	private static final long serialVersionUID = -8497877063767382205L;

	private long id;

	public static final IAttributeDescriptor<String> FIELD_STRING3 = new AttributeDescriptor<String>(ENTITY, "string3", String.class, String.class);

	public static IAttributeDescriptor<?>[] getFieldDescriptors() {
		return new IAttributeDescriptor[] {

		FIELD_ID, FIELD_STRING3

		};
	}

	private String string3;

	@Override
	public Object get(String name) {

		if ("string3".equals(name)) {
			return string3;
		}

		throw new RuntimeException("getter for '" + name + "' not implemented");
	}

	@Override
	public long getId() {
		return id;
	}

	public String getString3() {
		return string3;
	}

	@Override
	public void set(String name, Object value) {

		if ("string3".equals(name)) {
			string3 = (String) value;
		} else {
			throw new RuntimeException("setter for '" + name + "' not implemented");
		}

	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public void setString3(String string3) {
		this.string3 = string3;
	}

}
