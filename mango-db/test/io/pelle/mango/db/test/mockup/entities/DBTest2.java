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

import io.pelle.mango.client.base.vo.EntityAttributeDescriptor;
import io.pelle.mango.client.base.vo.EntityDescriptor;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IEntityDescriptor;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
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
@Table(name = "test2")
public class DBTest2 extends BaseEntity implements IBaseEntity {

	public static final IEntityDescriptor<DBTest2> DB_TEST2 = new EntityDescriptor<DBTest2>(DBTest2.class, null, null, null);

	public static final StringAttributeDescriptor TEST1 = new StringAttributeDescriptor(DB_TEST2, "test1");

	public static final StringAttributeDescriptor TESTSTRING = new StringAttributeDescriptor(DB_TEST2, "testString");

	@Id
	@Column(name = "test2_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "test2_id_seq")
	@SequenceGenerator(name = "test2_id_seq", sequenceName = "test2_id_seq", allocationSize = 1)
	private int id;

	@OneToOne
	private DBTest1 test1;

	public static EntityAttributeDescriptor<DBTest3> TEST3 = new EntityAttributeDescriptor<>(DB_TEST2, "test3", DBTest3.class);

	@OneToOne
	private DBTest3 test3;

	@Column(name = "test2_testString")
	private String testString;

	@Override
	public long getId() {
		return this.id;
	}

	public DBTest1 getTest1() {
		return this.test1;
	}

	public DBTest3 getTest3() {
		return this.test3;
	}

	public String getTestString() {
		return this.testString;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTest1(DBTest1 test1) {
		this.test1 = test1;
	}

	public void setTest3(DBTest3 test3) {
		this.test3 = test3;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

}
