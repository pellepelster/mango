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
package io.pelle.mango.client.web.module;

import io.pelle.mango.client.base.module.IModule;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Describes a factory for module instantiation
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public interface IModuleFactory {

	boolean supports(String moduleUrl);

	void getNewInstance(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters);

}
