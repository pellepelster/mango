package io.pelle.mango.client.base.modules.dictionary.hooks;

import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;

public interface IFileControlHook
{
	void onLinkActivate(IFileControl fileControl);
}
