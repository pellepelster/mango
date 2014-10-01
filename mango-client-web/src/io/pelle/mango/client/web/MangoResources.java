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
package io.pelle.mango.client.web;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface MangoResources extends ClientBundle {
	@Source("delete.png")
	ImageResource delete();

	@Source("ok.png")
	ImageResource ok();

	@Source("cancel.png")
	ImageResource cancel();

	@Source("back.png")
	ImageResource back();

	@Source("add.png")
	ImageResource add();

	@Source("editorSave.png")
	ImageResource editorSave();

	@Source("dictionaryCreate.png")
	ImageResource dictionaryCreate();

	@Source("dictionaryEdit.png")
	ImageResource dictionaryEdit();

	@Source("dictionaryInfo.png")
	ImageResource dictionaryInfo();

	@Source("searchSearch.png")
	ImageResource searchSearch();

	@Source("editorRefresh.png")
	ImageResource editorRefresh();

	@Source("more.png")
	ImageResource more();

	@Source("hierarchy.png")
	ImageResource hierarchy();

}