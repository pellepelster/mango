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

import io.pelle.mango.client.base.vo.AttributeDescriptor;
import io.pelle.mango.client.base.vo.EntityDescriptor;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IEntityDescriptor;
import io.pelle.mango.server.base.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "test1")
public class DBTest1 extends BaseEntity implements IBaseEntity {

	public static final IEntityDescriptor<DBTest1> DBTEST1 = new EntityDescriptor<DBTest1>(DBTest1.class);

	public static final IAttributeDescriptor<String> TESTSTRING = new AttributeDescriptor<String>(DBTEST1, "testString", String.class, String.class, false, 0);

	public enum TEST_ENUM {
		ENUM1, ENUM2
	}

	public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(DBTEST1, "id");

	public static IAttributeDescriptor<?>[] getFieldDescriptors() {
		return new IAttributeDescriptor[] { TEST2S, TESTSTRING };
	}

	@Id
	@Column(name = "test1_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "test1_id_seq")
	@SequenceGenerator(name = "test1_id_seq", sequenceName = "test1_id_seq", allocationSize = 1)
	private long id;

	@Column(name = "test1_testinteger")
	private int testInteger;

	@Column(name = "test1_teststring")
	private String testString;

	public static IAttributeDescriptor<List<DBTest2>> TEST2S = new AttributeDescriptor<>(DBTEST1, "test2s", List.class, DBTest2.class);

	@javax.persistence.OneToMany()
	@Column(name = "test1_test2s")
	private List<DBTest2> test2s = new ArrayList<DBTest2>();

	@Column(name = "test1_testenum")
	private TEST_ENUM testEnum;

	@javax.persistence.ElementCollection
	private Map<String, String> map = new HashMap<String, String>();

	public Map<String, String> getMap() {
		return this.map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public List<DBTest2> getTest2s() {
		return this.test2s;
	}

	public TEST_ENUM getTestEnum() {
		return this.testEnum;
	}

	public int getTestInteger() {
		return this.testInteger;
	}

	public String getTestString() {
		return this.testString;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTest2s(List<DBTest2> test2s) {
		this.test2s = test2s;
	}

	public void setTestEnum(TEST_ENUM testEnum) {
		this.testEnum = testEnum;
	}

	public void setTestInteger(int testInteger) {
		this.testInteger = testInteger;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

}
