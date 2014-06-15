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
package io.pelle.mango.db.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * This merges all JPA entities from multiple jars. To use it, all entities must
 * be listed in their respective persistence.xml files using the <class> tag.
 * 
 * @see http://forum.springsource.org/showthread.php?t=61763
 */
public class MergingPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor
{
	private String targetPersistenceUnitName;

	private final Logger LOG = Logger.getLogger(MergingPersistenceUnitPostProcessor.class);

	Map<String, List<String>> puiClasses = new HashMap<String, List<String>>();

	MutablePersistenceUnitInfo targetPui;

	List<MutablePersistenceUnitInfo> puisToMerge = new ArrayList<MutablePersistenceUnitInfo>();

	private void mergeManagedClasses(MutablePersistenceUnitInfo sourcePui)
	{
		LOG.info(String.format("merging persistence unit '%s' to '%s'", sourcePui.getPersistenceUnitName(), targetPui.getPersistenceUnitName()));

		for (String managedClassName : sourcePui.getManagedClassNames())
		{
			targetPui.addManagedClassName(managedClassName);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui)
	{
		if (pui.getPersistenceUnitName().endsWith(targetPersistenceUnitName))
		{
			LOG.info(String.format("target persistence unit '%s' (%s)", pui.getPersistenceUnitName(),
					StringUtils.join(pui.getManagedClassNames().listIterator(), ",")));
			targetPui = pui;

			for (MutablePersistenceUnitInfo puiToMerge : puisToMerge)
			{
				mergeManagedClasses(puiToMerge);
			}
		}
		else
		{
			if (targetPui == null)
			{
				LOG.info(String.format("persistence unit '%s' marked for later merging (%s)", pui.getPersistenceUnitName(),
						StringUtils.join(pui.getManagedClassNames().listIterator(), ",")));
				puisToMerge.add(pui);
			}
			else
			{
				mergeManagedClasses(pui);
			}
		}
	}

	public void setTargetPersistenceUnitName(String targetPersistenceUnitName)
	{
		this.targetPersistenceUnitName = targetPersistenceUnitName;
	}

}
