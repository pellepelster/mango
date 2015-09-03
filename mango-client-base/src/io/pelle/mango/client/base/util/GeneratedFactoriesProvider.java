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
package io.pelle.mango.client.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GeneratedFactoriesProvider {

	private static List<BaseGeneratedFactory> generatedFactories = new ArrayList<BaseGeneratedFactory>();

	private static Logger LOG = Logger.getLogger(GeneratedFactoriesProvider.class.getName());

	public static void registerBaseGeneratedFactory(BaseGeneratedFactory baseGeneratedFactory) {
		generatedFactories.add(baseGeneratedFactory);
	}

}
