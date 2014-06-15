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

public class HierarchicalTest1VO extends BaseHierarchicalTestVO {
	public HierarchicalTest1VO(IHierarchicalVO parent) {
		super(parent);
	}

	private static final long serialVersionUID = 2693790327711259957L;
}
