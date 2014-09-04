package io.pelle.mango.test.client;

@SuppressWarnings("serial")
public class Entity5VO extends io.pelle.mango.client.base.vo.BaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.client.Entity5VO> ENTITY5 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.client.Entity5VO>(io.pelle.mango.test.client.Entity5VO.class);

	public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY5, "id");


	
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
		
		return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
			
			
				ENTITY4, 
				ENTITY4S, 
				STRING1, 
				BINARY1, 
				BOOLEAN1, 
				ENUMERATION1
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
	
	private io.pelle.mango.test.client.Entity4VO entity4;
	public static io.pelle.mango.client.base.vo.EntityAttributeDescriptor<io.pelle.mango.test.client.Entity4VO> ENTITY4 = new io.pelle.mango.client.base.vo.EntityAttributeDescriptor<io.pelle.mango.test.client.Entity4VO>(ENTITY5, "entity4", io.pelle.mango.test.client.Entity4VO.class);
	public io.pelle.mango.test.client.Entity4VO getEntity4() {
		return this.entity4;
	}
	public void setEntity4(io.pelle.mango.test.client.Entity4VO entity4) {
		getChangeTracker().addChange("entity4", entity4);
		this.entity4 = entity4;
	}
	private java.util.List<io.pelle.mango.test.client.Entity4VO> entity4s = new io.pelle.mango.client.base.vo.ChangeTrackingArrayList<io.pelle.mango.test.client.Entity4VO>();
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.util.List<io.pelle.mango.test.client.Entity4VO>> ENTITY4S = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.util.List<io.pelle.mango.test.client.Entity4VO>>(ENTITY5, "entity4s", java.util.List.class, io.pelle.mango.test.client.Entity4VO.class, false, -1);
	public java.util.List<io.pelle.mango.test.client.Entity4VO> getEntity4s() {
		return this.entity4s;
	}
	public void setEntity4s(java.util.List<io.pelle.mango.test.client.Entity4VO> entity4s) {
		getChangeTracker().addChange("entity4s", entity4s);
		this.entity4s = entity4s;
	}
	private java.lang.String string1;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRING1 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY5, "string1", java.lang.String.class, -1, -1);
	public java.lang.String getString1() {
		return this.string1;
	}
	public void setString1(java.lang.String string1) {
		getChangeTracker().addChange("string1", string1);
		this.string1 = string1;
	}
	private byte[] binary1;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<byte[]> BINARY1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<byte[]>(ENTITY5, "binary1", byte[].class, byte[].class, false, -1);
	public byte[] getBinary1() {
		return this.binary1;
	}
	public void setBinary1(byte[] binary1) {
		getChangeTracker().addChange("binary1", binary1);
		this.binary1 = binary1;
	}
	private java.lang.Boolean boolean1 = false;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Boolean> BOOLEAN1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Boolean>(ENTITY5, "boolean1", java.lang.Boolean.class, java.lang.Boolean.class, false, -1);
	public java.lang.Boolean getBoolean1() {
		return this.boolean1;
	}
	public void setBoolean1(java.lang.Boolean boolean1) {
		getChangeTracker().addChange("boolean1", boolean1);
		this.boolean1 = boolean1;
	}
	private io.pelle.mango.test.client.ENUMERATION1 enumeration1;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<io.pelle.mango.test.client.ENUMERATION1> ENUMERATION1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<io.pelle.mango.test.client.ENUMERATION1>(ENTITY5, "enumeration1", io.pelle.mango.test.client.ENUMERATION1.class, io.pelle.mango.test.client.ENUMERATION1.class, false, -1);
	public io.pelle.mango.test.client.ENUMERATION1 getEnumeration1() {
		return this.enumeration1;
	}
	public void setEnumeration1(io.pelle.mango.test.client.ENUMERATION1 enumeration1) {
		getChangeTracker().addChange("enumeration1", enumeration1);
		this.enumeration1 = enumeration1;
	}
	
	public Object get(java.lang.String name) {
	
		if ("entity4".equals(name))
		{
			return this.entity4;
		}
		if ("entity4s".equals(name))
		{
			return this.entity4s;
		}
		if ("string1".equals(name))
		{
			return this.string1;
		}
		if ("binary1".equals(name))
		{
			return this.binary1;
		}
		if ("boolean1".equals(name))
		{
			return this.boolean1;
		}
		if ("enumeration1".equals(name))
		{
			return this.enumeration1;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("entity4".equals(name))
		{
			setEntity4((io.pelle.mango.test.client.Entity4VO) value);
			return;
		}
		if ("entity4s".equals(name))
		{
			setEntity4s((java.util.List<io.pelle.mango.test.client.Entity4VO>) value);
			return;
		}
		if ("string1".equals(name))
		{
			setString1((java.lang.String) value);
			return;
		}
		if ("binary1".equals(name))
		{
			setBinary1((byte[]) value);
			return;
		}
		if ("boolean1".equals(name))
		{
			setBoolean1((java.lang.Boolean) value);
			return;
		}
		if ("enumeration1".equals(name))
		{
			if (value instanceof java.lang.String)
			{
				setEnumeration1(io.pelle.mango.test.client.ENUMERATION1.valueOf((java.lang.String) value));
			}
			else
			{
				setEnumeration1((io.pelle.mango.test.client.ENUMERATION1) value);
			}
			return;
		}
	
		super.set(name, value);
	}
	
}
