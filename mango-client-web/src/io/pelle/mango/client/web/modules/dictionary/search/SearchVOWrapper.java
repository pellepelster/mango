package io.pelle.mango.client.web.modules.dictionary.search;

import io.pelle.mango.client.base.modules.dictionary.IVOWrapper;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.HashMap;
import java.util.Map;

public class SearchVOWrapper<VOType extends IBaseVO> implements IVOWrapper<VOType> {

	private Map<String, Object> filterValues = new HashMap<String, Object>();

	@Override
	public void set(String attribute, Object value) {
		this.filterValues.put(attribute, value);
	}

	@Override
	public Object get(String attribute) {
		return this.filterValues.get(attribute);
	}

	public Map<String, Object> getFilterValues() {
		return this.filterValues;
	}

	@Override
	public VOType getContent() {
		throw new RuntimeException("not implemented");
	}

}
