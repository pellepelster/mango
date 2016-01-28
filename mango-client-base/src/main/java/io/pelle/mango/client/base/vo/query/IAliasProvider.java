package io.pelle.mango.client.base.vo.query;


public interface IAliasProvider {

	/**
	 * Returns the alias
	 * 
	 * @return
	 */
	String getAliasFor(Object key);

}