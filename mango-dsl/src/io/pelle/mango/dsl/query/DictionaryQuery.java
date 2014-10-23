package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.Dictionary;

public class DictionaryQuery extends BaseEObjectQuery<Dictionary> {

	private DictionaryQuery(Dictionary dictionary) {
		super(dictionary);
	}

	public static DictionaryQuery createQuery(Dictionary dictionary) {
		return new DictionaryQuery(dictionary);
	}
	
}
