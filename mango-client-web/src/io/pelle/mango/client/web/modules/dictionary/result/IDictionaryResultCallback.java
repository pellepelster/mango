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
package io.pelle.mango.client.web.modules.dictionary.result;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.util.IDataSourceCallback;

/**
 * Callback for interaction with a result list
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IDictionaryResultCallback<VOType extends IBaseVO> extends IDataSourceCallback
{

	/**
	 * Gets called when a row in a result list is double clicked
	 * 
	 * @param vo
	 */
	void onDoubleClick(VOType vo);

}
