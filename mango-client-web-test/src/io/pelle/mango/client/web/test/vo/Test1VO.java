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
package io.pelle.mango.client.web.test.vo;

import io.pelle.mango.client.base.vo.AttributeDescriptor;
import io.pelle.mango.client.base.vo.BaseVO;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test1VO extends BaseVO implements IBaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<Test1VO> ENTITY = new io.pelle.mango.client.base.vo.EntityDescriptor<Test1VO>(Test1VO.class);

	public enum ENUM1 {
		ENUM1_VALUE1, ENUM1_VALUE2, ENUM1_VALUE3
	};

	private static final long serialVersionUID = 5114820135502917898L;

	private long id;

	public static final IAttributeDescriptor<String> FIELD_STRING1 = new AttributeDescriptor<String>(ENTITY, "string1", String.class, String.class);

	private String string1;

	public static final IAttributeDescriptor<Integer> FIELD_INTEGER1 = new AttributeDescriptor<Integer>(ENTITY, "integer1", Integer.class, Integer.class);

	private Integer integer1;

	public static final IAttributeDescriptor<BigDecimal> FIELD_BIGDECIMAL1 = new AttributeDescriptor<BigDecimal>(ENTITY, "bigDecimal1", BigDecimal.class, BigDecimal.class);

	private BigDecimal bigDecimal1;

	public static final IAttributeDescriptor<Boolean> FIELD_BOOLEAN1 = new AttributeDescriptor<Boolean>(ENTITY, "boolean1", Boolean.class, Boolean.class);

	private Boolean boolean1;

	public static final IAttributeDescriptor<Date> FIELD_DATE1 = new AttributeDescriptor<Date>(ENTITY, "date1", Date.class, Date.class);

	private Date date1;

	public static final IAttributeDescriptor<ENUM1> FIELD_ENUMERATION1 = new AttributeDescriptor<ENUM1>(ENTITY, "enumeration1", ENUM1.class, ENUM1.class);

	private ENUM1 enumeration1;

	public static final IAttributeDescriptor<Test3VO> FIELD_TEST3VO = new AttributeDescriptor<Test3VO>(ENTITY, "test3VO", Test3VO.class, Test3VO.class);

	private Test3VO test3VO;

	public static final IAttributeDescriptor<List<Test2VO>> FIELD_TEST2VOS = new AttributeDescriptor<List<Test2VO>>(ENTITY, "test2VOs", List.class, Test2VO.class);

	private List<Test2VO> test2VOs = new ArrayList<Test2VO>();

	public static IAttributeDescriptor<?>[] getFieldDescriptors() {
		return new IAttributeDescriptor[] {

		FIELD_BIGDECIMAL1, FIELD_BOOLEAN1, FIELD_DATE1, FIELD_ENUMERATION1, FIELD_ID, FIELD_INTEGER1, FIELD_STRING1, FIELD_TEST2VOS, FIELD_TEST3VO

		};
	}

	public IAttributeDescriptor<?> getAttributeDescriptor(String name) {

		for (IAttributeDescriptor<?> attributeDescriptor : getFieldDescriptors()) {
			if (attributeDescriptor.getAttributeName().equals(name)) {
				return attributeDescriptor;
			}
		}

		throw new RuntimeException("unsupported attribute '" + name + "'");
	}

	@Override
	public Object get(String name) {

		if ("string1".equals(name)) {
			return string1;
		}

		if ("integer1".equals(name)) {
			return integer1;
		}

		if ("bigDecimal1".equals(name)) {
			return bigDecimal1;
		}

		if ("boolean1".equals(name)) {
			return boolean1;
		}

		if ("date1".equals(name)) {
			return date1;
		}

		if ("enumeration1".equals(name)) {
			return enumeration1;
		}

		if ("test2VOs".equals(name)) {
			return test2VOs;
		}

		if ("test3VO".equals(name)) {
			return test3VO;
		}

		throw new RuntimeException("getter for '" + name + "' not implemented");
	}

	public BigDecimal getBigDecimal1() {
		return bigDecimal1;
	}

	public Boolean getBoolean1() {
		return boolean1;
	}

	public Date getDate1() {
		return date1;
	}

	public ENUM1 getEnumeration1() {
		return enumeration1;
	}

	@Override
	public long getId() {
		return id;
	}

	public Integer getInteger1() {
		return integer1;
	}

	public String getString1() {
		return string1;
	}

	public List<Test2VO> getTest2VOs() {
		return test2VOs;
	}

	public Test3VO getTest3VO() {
		return test3VO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(String name, Object value) {

		if ("string1".equals(name)) {
			string1 = (String) value;
		} else if ("integer1".equals(name)) {
			integer1 = (Integer) value;
		} else if ("bigDecimal1".equals(name)) {
			bigDecimal1 = (BigDecimal) value;
		} else if ("boolean1".equals(name)) {
			boolean1 = (Boolean) value;
		} else if ("date1".equals(name)) {
			date1 = (Date) value;
		} else if ("enumeration1".equals(name)) {
			if (value instanceof String) {
				enumeration1 = ENUM1.valueOf((String) value);
			} else {
				enumeration1 = (ENUM1) value;
			}
		} else if ("test2VOs".equals(name)) {
			test2VOs = (List<Test2VO>) value;
		} else if ("test3VO".equals(name)) {
			test3VO = (Test3VO) value;
		} else {
			throw new RuntimeException("setter for '" + name + "' not implemented");
		}

	}

	public void setBigDecimal1(BigDecimal bigDecimal1) {
		this.bigDecimal1 = bigDecimal1;
	}

	public void setBoolean1(Boolean boolean1) {
		this.boolean1 = boolean1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public void setEnumeration1(ENUM1 enumeration1) {
		this.enumeration1 = enumeration1;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public void setInteger1(Integer integer1) {
		this.integer1 = integer1;
	}

	public void setString1(String string1) {
		this.string1 = string1;
	}

	public void setTest2VOs(List<Test2VO> test2VOs) {
		this.test2VOs = test2VOs;
	}

	public void setTest3VO(Test3VO test3vo) {
		test3VO = test3vo;
	}

}
