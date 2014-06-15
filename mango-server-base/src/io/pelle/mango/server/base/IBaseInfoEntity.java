package io.pelle.mango.server.base;

import io.pelle.mango.client.base.vo.IBaseEntity;

import java.util.Date;

/**
 * General info attributes for an entity
 * 
 * @author pelle
 * @version $Rev: 1030 $, $Date: 2011-04-27 17:34:07 +0200 (Wed, 27 Apr 2011) $
 * 
 */
public interface IBaseInfoEntity extends IBaseEntity {
	/**
	 * The date of entity creation
	 * 
	 * @return
	 */
	Date getCreateDate();

	/**
	 * Create username
	 * 
	 * @return
	 */
	String getCreateUser();

	/**
	 * Last update date
	 * 
	 * @return
	 */
	Date getUpdateDate();

	/**
	 * Last update username
	 * 
	 * @return
	 */
	String getUpdateUser();

	/**
	 * Sets create date
	 * 
	 * @param createDate
	 */
	void setCreateDate(Date createDate);

	/**
	 * Sets create username
	 * 
	 * @param createUser
	 */
	void setCreateUser(String createUser);

	/**
	 * Sets update date
	 * 
	 * @param updateDate
	 */
	void setUpdateDate(Date updateDate);

	/**
	 * Sets update username
	 * 
	 * @param updateUser
	 */
	void setUpdateUser(String updateUser);
}