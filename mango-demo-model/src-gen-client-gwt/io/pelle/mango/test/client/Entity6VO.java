package io.pelle.mango.test.client;

@SuppressWarnings("serial")
public class Entity6VO extends io.pelle.mango.client.base.vo.BaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.client.Entity6VO> ENTITY6 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.client.Entity6VO>(io.pelle.mango.test.client.Entity6VO.class);

	public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY6, "id");


	
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
		
		return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
			
		};
	}

	private long id;
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private java.lang.String string1;
	public java.lang.String getString1() {
		return this.string1;
	}
	public void setString1(java.lang.String string1) {
		getChangeTracker().addChange("string1", string1);
		this.string1 = string1;
	}
	
	public Object get(java.lang.String name) {
	
		if ("string1".equals(name))
		{
			return this.string1;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("string1".equals(name))
		{
			setString1((java.lang.String) value);
			return;
		}
	
		super.set(name, value);
	}
	
}
