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

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.vo.AttributeDescriptor;
import io.pelle.mango.client.base.vo.BaseVO;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;

public abstract class BaseHierarchicalTestVO extends BaseVO implements IHierarchicalVO {

	enum ENUM1 {
		ENUM1_VALUE1, ENUM1_VALUE2, ENUM1_VALUE3
	};

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<BaseHierarchicalTestVO> ENTITY = new io.pelle.mango.client.base.vo.EntityDescriptor<BaseHierarchicalTestVO>(BaseHierarchicalTestVO.class, null, null, null);

	private static final long serialVersionUID = 5114820135502917898L;

	private long id;

	public static final IAttributeDescriptor<String> FIELD_STRING1 = new AttributeDescriptor<String>(ENTITY, "string1", String.class, String.class);

	private String string1;

	private String parentClassName;

	private Long parentId;

	private boolean hasChildren;

	private IHierarchicalVO parent;

	public BaseHierarchicalTestVO(IHierarchicalVO parent) {
		super();
		this.parent = parent;
	}

	public static IAttributeDescriptor<?>[] getFieldDescriptors() {
		return new IAttributeDescriptor[] {

		FIELD_ID, FIELD_STRING1

		};
	}

	@Override
	public Object get(String name) {

		if ("string1".equals(name)) {
			return this.string1;
		}

		if ("parent".equals(name)) {
			return this.parent;
		}

		throw new RuntimeException("getter for '" + name + "' not implemented");
	}

	@Override
	public long getId() {
		return this.id;
	}

	public String getString1() {
		return this.string1;
	}

	@Override
	public void set(String name, Object value) {

		if ("string1".equals(name)) {
			this.string1 = (String) value;
		} else if ("parent".equals(name)) {
			this.parent = (IHierarchicalVO) value;
		}

		else {
			throw new RuntimeException("setter for '" + name + "' not implemented");
		}

	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public void setString1(String string1) {
		this.string1 = string1;
	}

	@Override
	public IHierarchicalVO getParent() {
		return this.parent;
	}

	@Override
	public Boolean getHasChildren() {
		return this.hasChildren;
	}

	@Override
	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	@Override
	public void setParent(IHierarchicalVO parent) {
		this.parent = parent;
	}

	@Override
	public void setParentClassName(String parentClassName) {
		this.parentClassName = parentClassName;
	}

	@Override
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getParentClassName() {
		return this.parentClassName;
	}

	@Override
	public Long getParentId() {
		return this.parentId;
	}

}
