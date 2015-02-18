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
package io.pelle.mango.db.test.mockup.entities;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.server.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "test3")
public class DBTest3 extends BaseEntity implements IBaseEntity {

	@Id
	@Column(name = "test3_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "test3_id_seq")
	@SequenceGenerator(name = "test3_id_seq", sequenceName = "test3_id_seq", allocationSize = 1)
	private long id;

	private String testString;

	@OneToOne
	private DBTest1 test1;

	/** {@inheritDoc} */
	@Override
	public long getId() {
		return this.id;
	}

	public DBTest1 getTest1() {
		return this.test1;
	}

	public String getTestString() {
		return this.testString;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTest1(DBTest1 test1) {
		this.test1 = test1;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

}
