package io.pelle.mango.test.client;

@SuppressWarnings("serial")
public class Entity7VO extends io.pelle.mango.test.client.Entity6VO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.client.Entity7VO> ENTITY7 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.client.Entity7VO>(io.pelle.mango.test.client.Entity7VO.class);

	public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY7, "id");

	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRING1 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY7, "string1", java.lang.String.class, -1, -1);

	
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
		
		return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
			
			
				STRING2
				,
				STRING1
		};
	}

	private long id;
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private java.lang.String string2;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRING2 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY7, "string2", java.lang.String.class, -1, -1);
	public java.lang.String getString2() {
		return this.string2;
	}
	public void setString2(java.lang.String string2) {
		getChangeTracker().addChange("string2", string2);
		this.string2 = string2;
	}
	
	public Object get(java.lang.String name) {
	
		if ("string2".equals(name))
		{
			return this.string2;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("string2".equals(name))
		{
			setString2((java.lang.String) value);
			return;
		}
	
		super.set(name, value);
	}
	
}
