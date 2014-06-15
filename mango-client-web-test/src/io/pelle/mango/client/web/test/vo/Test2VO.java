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
import java.util.Date;

public class Test2VO extends BaseVO implements IBaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<Test2VO> ENTITY = new io.pelle.mango.client.base.vo.EntityDescriptor<Test2VO>(Test2VO.class);

	public enum ENUM2 {
		ENUM2_VALUE1, ENUM2_VALUE2, ENUM2_VALUE3
	}

	private static final long serialVersionUID = -665701982873718230L;;

	private long id;

	public static final IAttributeDescriptor<String> FIELD_STRING2 = new AttributeDescriptor<String>(ENTITY, "string2", String.class, String.class);

	private String string2;

	public static final IAttributeDescriptor<Integer> FIELD_INTEGER2 = new AttributeDescriptor<Integer>(ENTITY, "integer2", Integer.class, Integer.class);

	private Integer integer2;

	public static final IAttributeDescriptor<BigDecimal> FIELD_BIGDECIMAL2 = new AttributeDescriptor<BigDecimal>(ENTITY, "bigDecimal2", BigDecimal.class, BigDecimal.class);

	private BigDecimal bigDecimal2;

	public static final IAttributeDescriptor<Boolean> FIELD_BOOLEAN2 = new AttributeDescriptor<Boolean>(ENTITY, "boolean2", Boolean.class, Boolean.class);

	private Boolean boolean2;

	public static final IAttributeDescriptor<Date> FIELD_DATE2 = new AttributeDescriptor<Date>(ENTITY, "date2", Date.class, Date.class);

	private Date date2;

	public static final IAttributeDescriptor<ENUM2> FIELD_ENUMERATION2 = new AttributeDescriptor<ENUM2>(ENTITY, "enumeration2", ENUM2.class, ENUM2.class);

	private ENUM2 enumeration2;

	public static final IAttributeDescriptor<Test3VO> FIELD_TEST3VO = new AttributeDescriptor<Test3VO>(ENTITY, "test3VO", Test3VO.class, Test3VO.class);

	public static IAttributeDescriptor<?>[] getFieldDescriptors() {
		return new IAttributeDescriptor[] {

		FIELD_BIGDECIMAL2, FIELD_BOOLEAN2, FIELD_DATE2, FIELD_ENUMERATION2, FIELD_ID, FIELD_INTEGER2, FIELD_STRING2, FIELD_TEST3VO

		};
	}

	private Test3VO test3VO;

	@Override
	public Object get(String name) {

		if ("string2".equals(name)) {
			return string2;
		}

		if ("integer2".equals(name)) {
			return integer2;
		}

		if ("bigDecimal2".equals(name)) {
			return bigDecimal2;
		}

		if ("boolean2".equals(name)) {
			return boolean2;
		}

		if ("date2".equals(name)) {
			return date2;
		}

		if ("enumeration2".equals(name)) {
			return enumeration2;
		}

		if ("oid".equals(name)) {
			return getOid();
		}

		if ("test3VO".equals(name)) {
			return test3VO;
		}

		throw new RuntimeException("getter for '" + name + "' not implemented");
	}

	public BigDecimal getBigDecimal2() {
		return bigDecimal2;
	}

	public Boolean getBoolean2() {
		return boolean2;
	}

	public Date getDate2() {
		return date2;
	}

	public ENUM2 getEnumeration2() {
		return enumeration2;
	}

	@Override
	public long getId() {
		return id;
	}

	public Integer getInteger2() {
		return integer2;
	}

	public String getString2() {
		return string2;
	}

	public Test3VO getTest3VO() {
		return test3VO;
	}

	@Override
	public void set(String name, Object value) {

		if ("string2".equals(name)) {
			string2 = (String) value;
		} else if ("integer2".equals(name)) {
			integer2 = (Integer) value;
		} else if ("bigDecimal2".equals(name)) {
			bigDecimal2 = (BigDecimal) value;
		} else if ("boolean2".equals(name)) {
			boolean2 = (Boolean) value;
		} else if ("date2".equals(name)) {
			date2 = (Date) value;
		} else if ("enumeration2".equals(name)) {
			if (value instanceof String) {
				enumeration2 = ENUM2.valueOf((String) value);
			} else {
				enumeration2 = (ENUM2) value;
			}

		} else if ("test3VO".equals(name)) {
			test3VO = (Test3VO) value;
		} else {
			throw new RuntimeException("setter for '" + name + "' not implemented");
		}

	}

	public void setBigDecimal2(BigDecimal bigDecimal2) {
		this.bigDecimal2 = bigDecimal2;
	}

	public void setBoolean2(Boolean boolean2) {
		this.boolean2 = boolean2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public void setEnumeration2(ENUM2 enumeration2) {
		this.enumeration2 = enumeration2;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public void setInteger2(Integer integer2) {
		this.integer2 = integer2;
	}

	public void setString2(String string2) {
		this.string2 = string2;
	}

	public void setTest3VO(Test3VO test3VO) {
		this.test3VO = test3VO;
	}

}
