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
package io.pelle.mango.client.base.vo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Basic VO interface
 * 
 * @author pelle
 * @version $Rev: 662 $, $Date: 2010-08-30 20:58:04 +0200 (Mon, 30 Aug 2010) $
 * 
 */
public interface IBaseVO extends Serializable, IVOEntity {

	public static long NEW_VO_ID = 0;

	public static final IEntityDescriptor<IBaseVO> ENTITY_DESCRIPTOR = new EntityDescriptor<IBaseVO>(IBaseVO.class);

	public static final IAttributeDescriptor<Long> FIELD_ID = new AttributeDescriptor<Long>(ENTITY_DESCRIPTOR, "id", Long.class, Long.class);

	Object get(String name);

	/**
	 * Generic setter to overcome the lack of reflection on the client
	 * 
	 * @param name
	 * @return
	 */
	public void set(String name, Object value);

	/**
	 * Sets the id for the VO
	 * 
	 * @return
	 */
	void setId(long id);

	/**
	 * Generic data map
	 * 
	 * @return
	 */
	HashMap<String, Object> getData();

	String getNaturalKey();

}
