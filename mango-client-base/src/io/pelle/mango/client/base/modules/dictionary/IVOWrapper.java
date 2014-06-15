package io.pelle.mango.client.base.modules.dictionary;

import io.pelle.mango.client.base.vo.IBaseVO;

public interface IVOWrapper<VOType extends IBaseVO>
{
	void set(String attribute, Object value);

	Object get(String attribute);

	VOType getContent();
}
