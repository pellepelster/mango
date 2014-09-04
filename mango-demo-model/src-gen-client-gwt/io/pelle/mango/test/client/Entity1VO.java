package io.pelle.mango.test.client;

@SuppressWarnings("serial")
public class Entity1VO extends io.pelle.mango.client.base.vo.BaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.client.Entity1VO> ENTITY1 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.client.Entity1VO>(io.pelle.mango.test.client.Entity1VO.class);

	public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY1, "id");


	
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
		
		return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
			
			
				STRINGDATATYPE1, 
				BOOLEANDATATYPE1, 
				STRINGDATATYPE1LIST, 
				ENTITY2DATATYPE, 
				ENUMERATION1DATATYPE
				,
		};
	}

	private long id;
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private java.lang.String stringDatatype1;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE1 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY1, "stringDatatype1", java.lang.String.class, 42, 0);
	public java.lang.String getStringDatatype1() {
		return this.stringDatatype1;
	}
	public void setStringDatatype1(java.lang.String stringDatatype1) {
		getChangeTracker().addChange("stringDatatype1", stringDatatype1);
		this.stringDatatype1 = stringDatatype1;
	}
	private java.lang.Boolean booleanDatatype1 = false;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Boolean> BOOLEANDATATYPE1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Boolean>(ENTITY1, "booleanDatatype1", java.lang.Boolean.class, java.lang.Boolean.class, false, -1);
	public java.lang.Boolean getBooleanDatatype1() {
		return this.booleanDatatype1;
	}
	public void setBooleanDatatype1(java.lang.Boolean booleanDatatype1) {
		getChangeTracker().addChange("booleanDatatype1", booleanDatatype1);
		this.booleanDatatype1 = booleanDatatype1;
	}
	private java.util.List<java.lang.String> stringDatatype1List = new io.pelle.mango.client.base.vo.ChangeTrackingArrayList<java.lang.String>();
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE1LIST = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY1, "stringDatatype1List", java.util.List.class, 42, -1);
	public java.util.List<java.lang.String> getStringDatatype1List() {
		return this.stringDatatype1List;
	}
	public void setStringDatatype1List(java.util.List<java.lang.String> stringDatatype1List) {
		getChangeTracker().addChange("stringDatatype1List", stringDatatype1List);
		this.stringDatatype1List = stringDatatype1List;
	}
	private io.pelle.mango.test.client.Entity2VO entity2Datatype;
	public static io.pelle.mango.client.base.vo.EntityAttributeDescriptor<io.pelle.mango.test.client.Entity2VO> ENTITY2DATATYPE = new io.pelle.mango.client.base.vo.EntityAttributeDescriptor<io.pelle.mango.test.client.Entity2VO>(ENTITY1, "entity2Datatype", io.pelle.mango.test.client.Entity2VO.class);
	public io.pelle.mango.test.client.Entity2VO getEntity2Datatype() {
		return this.entity2Datatype;
	}
	public void setEntity2Datatype(io.pelle.mango.test.client.Entity2VO entity2Datatype) {
		getChangeTracker().addChange("entity2Datatype", entity2Datatype);
		this.entity2Datatype = entity2Datatype;
	}
	private io.pelle.mango.test.client.ENUMERATION1 enumeration1Datatype;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<io.pelle.mango.test.client.ENUMERATION1> ENUMERATION1DATATYPE = new io.pelle.mango.client.base.vo.AttributeDescriptor<io.pelle.mango.test.client.ENUMERATION1>(ENTITY1, "enumeration1Datatype", io.pelle.mango.test.client.ENUMERATION1.class, io.pelle.mango.test.client.ENUMERATION1.class, false, -1);
	public io.pelle.mango.test.client.ENUMERATION1 getEnumeration1Datatype() {
		return this.enumeration1Datatype;
	}
	public void setEnumeration1Datatype(io.pelle.mango.test.client.ENUMERATION1 enumeration1Datatype) {
		getChangeTracker().addChange("enumeration1Datatype", enumeration1Datatype);
		this.enumeration1Datatype = enumeration1Datatype;
	}
	
	public Object get(java.lang.String name) {
	
		if ("stringDatatype1".equals(name))
		{
			return this.stringDatatype1;
		}
		if ("booleanDatatype1".equals(name))
		{
			return this.booleanDatatype1;
		}
		if ("stringDatatype1List".equals(name))
		{
			return this.stringDatatype1List;
		}
		if ("entity2Datatype".equals(name))
		{
			return this.entity2Datatype;
		}
		if ("enumeration1Datatype".equals(name))
		{
			return this.enumeration1Datatype;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("stringDatatype1".equals(name))
		{
			setStringDatatype1((java.lang.String) value);
			return;
		}
		if ("booleanDatatype1".equals(name))
		{
			setBooleanDatatype1((java.lang.Boolean) value);
			return;
		}
		if ("stringDatatype1List".equals(name))
		{
			setStringDatatype1List((java.util.List<java.lang.String>) value);
			return;
		}
		if ("entity2Datatype".equals(name))
		{
			setEntity2Datatype((io.pelle.mango.test.client.Entity2VO) value);
			return;
		}
		if ("enumeration1Datatype".equals(name))
		{
			if (value instanceof java.lang.String)
			{
				setEnumeration1Datatype(io.pelle.mango.test.client.ENUMERATION1.valueOf((java.lang.String) value));
			}
			else
			{
				setEnumeration1Datatype((io.pelle.mango.test.client.ENUMERATION1) value);
			}
			return;
		}
	
		super.set(name, value);
	}
	
	@java.lang.Override
	public String getNaturalKey() 
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();
		sb.append(this.getStringDatatype1());
		return sb.toString();
	}
	
}
