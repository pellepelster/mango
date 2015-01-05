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
package io.pelle.mango.client.base.db.vos;

import io.pelle.mango.client.base.vo.AttributeDescriptor;
import io.pelle.mango.client.base.vo.EntityDescriptor;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IEntityDescriptor;

/**
 * Interface for nodes in trees
 * 
 * @author pelle
 * 
 */
public interface IHierarchicalVO extends IBaseHierarchical, IBaseVO {

	public static final IEntityDescriptor<IHierarchicalVO> ENTITY_DESCRIPTOR = new EntityDescriptor<IHierarchicalVO>(IHierarchicalVO.class, null, null);

	public static final IAttributeDescriptor<IHierarchicalVO> FIELD_PARENT = new AttributeDescriptor<IHierarchicalVO>(ENTITY_DESCRIPTOR, "parent", IHierarchicalVO.class);

	public static final IAttributeDescriptor<String> FIELD_PARENT_CLASSNAME = new AttributeDescriptor<String>(ENTITY_DESCRIPTOR, "parentClassName", String.class);

	public static final IAttributeDescriptor<Long> FIELD_PARENT_ID = new AttributeDescriptor<Long>(ENTITY_DESCRIPTOR, "parentId", Long.class);

	/**
	 * Returns whether this node has children
	 * 
	 * @return
	 */
	boolean getHasChildren();

	/**
	 * Sets the children for this node
	 * 
	 * @param hasChildren
	 */
	void setHasChildren(boolean hasChildren);

	/**
	 * Sets the parent
	 * 
	 * @param parent
	 */
	void setParent(IHierarchicalVO parent);

	/**
	 * Returns the parent specified by
	 * {@link IBaseHierarchical#getParentClassName()} and
	 * {@link IBaseHierarchical#getParentId()}
	 * 
	 * @return
	 */
	IHierarchicalVO getParent();

}
