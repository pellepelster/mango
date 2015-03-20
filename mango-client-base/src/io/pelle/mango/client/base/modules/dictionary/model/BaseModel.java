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
package io.pelle.mango.client.base.modules.dictionary.model;

import io.pelle.mango.client.base.modules.dictionary.BaseDictionaryElementUtil;

public abstract class BaseModel<ElementType> implements IBaseModel {
	private static final long serialVersionUID = -564541020450388681L;

	private String name;

	private IBaseModel parent;

	private String helpText;

	public BaseModel(String name, IBaseModel parent) {
		super();
		this.name = name;
		this.parent = parent;
	}

	/** {@inheritDoc} */
	@Override
	public IBaseModel getParent() {
		return this.parent;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.parent == null) ? 0 : this.parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		BaseModel other = (BaseModel) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		if (this.parent == null) {
			if (other.parent != null)
				return false;
		} else if (!this.parent.equals(other.parent))
			return false;
		return true;
	}

	@Override
	public String getFullQualifiedName() {
		return BaseDictionaryElementUtil.getModelId(this);
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	@Override
	public String getHelpText() {
		return helpText;
	}
}
