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
package io.pelle.mango.db.test.mockup.vos;

import io.pelle.mango.client.base.vo.AttributeDescriptor;
import io.pelle.mango.client.base.vo.BaseVO;
import io.pelle.mango.client.base.vo.ChangeTrackingArrayList;
import io.pelle.mango.client.base.vo.EntityDescriptor;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IEntityDescriptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBTest1VO extends BaseVO {

	public enum TEST_ENUM_VO {
		ENUM1, ENUM2
	}

	private static final long serialVersionUID = 6869411076738783234L;

	public static final IEntityDescriptor<DBTest1VO> DB_TEST1 = new EntityDescriptor<DBTest1VO>(DBTest1VO.class, null, null, null);

	public static final IAttributeDescriptor<List<DBTest2VO>> TEST2S = new AttributeDescriptor<List<DBTest2VO>>(DB_TEST1, "test2s", List.class, DBTest2VO.class);

	public static final IAttributeDescriptor<Integer> TESTINTEGER = new AttributeDescriptor<Integer>(DB_TEST1, "testInteger", Integer.class);

	public static final IAttributeDescriptor<String> TESTSTRING = new AttributeDescriptor<String>(DB_TEST1, "testString", String.class, String.class, false, 0);

	public static final IAttributeDescriptor<TEST_ENUM_VO> TESTENUM = new AttributeDescriptor<TEST_ENUM_VO>(DB_TEST1, "testEnum", TEST_ENUM_VO.class);

	public static final IAttributeDescriptor<String> MAP = new AttributeDescriptor<String>(DB_TEST1, "map", Map.class);

	public static IAttributeDescriptor<?>[] getFieldDescriptors() {
		return new IAttributeDescriptor[] { TEST2S, TESTINTEGER, TESTSTRING, TESTENUM, MAP };
	}

	private Map<String, String> map = new HashMap<String, String>();

	private long id;

	private List<DBTest2VO> test2s = new ChangeTrackingArrayList<DBTest2VO>();

	private TEST_ENUM_VO testEnum;;

	private int testInteger;

	private String testString;

	/** {@inheritDoc} */
	@Override
	public Object get(String name) {
		throw new RuntimeException("not implemented");
	}

	/** {@inheritDoc} */
	@Override
	public long getId() {
		return this.id;
	}

	public List<DBTest2VO> getTest2s() {
		return this.test2s;
	}

	public TEST_ENUM_VO getTestEnum() {
		return this.testEnum;
	}

	public int getTestInteger() {
		return this.testInteger;
	}

	public String getTestString() {
		return this.testString;
	}

	/** {@inheritDoc} */
	@Override
	public void set(String name, Object value) {
		throw new RuntimeException("not implemented");
	}

	/** {@inheritDoc} */
	@Override
	public void setId(long id) {
		getChangeTracker().addChange("id", id);

		this.id = id;
	}

	public Map<String, String> getMap() {
		return this.map;
	}

	public void setMap(Map<String, String> map) {
		getChangeTracker().addChange("map", map);

		this.map = map;
	}

	public void setTestEnum(TEST_ENUM_VO testEnum) {
		getChangeTracker().addChange("testEnum", testEnum);

		this.testEnum = testEnum;
	}

	public void setTestInteger(int testInteger) {
		getChangeTracker().addChange("testInteger", testInteger);

		this.testInteger = testInteger;
	}

	public void setTestString(String testString) {
		getChangeTracker().addChange("testString", testString);

		this.testString = testString;
	}

}
