package io.pelle.mango.server.base;

import io.pelle.mango.client.base.vo.IBaseEntity;

public interface IBaseClientEntity extends IBaseEntity {
	/**
	 * The client associated with this entity
	 * 
	 * @return
	 */
	public IClient getClient();

	/**
	 * Associates a client with this entity
	 * 
	 * @param client
	 */
	public void setClient(IClient client);
}