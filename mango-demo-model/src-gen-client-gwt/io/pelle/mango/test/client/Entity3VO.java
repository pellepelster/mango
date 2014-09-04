package io.pelle.mango.test.client;

@SuppressWarnings("serial")
public class Entity3VO extends io.pelle.mango.client.base.vo.BaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.client.Entity3VO> ENTITY3 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.client.Entity3VO>(io.pelle.mango.test.client.Entity3VO.class);

	public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY3, "id");


	
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
	
	private java.lang.String stringDatatype3;
	public java.lang.String getStringDatatype3() {
		return this.stringDatatype3;
	}
	public void setStringDatatype3(java.lang.String stringDatatype3) {
		getChangeTracker().addChange("stringDatatype3", stringDatatype3);
		this.stringDatatype3 = stringDatatype3;
	}
	private byte[] binaryDatatype1;
	public byte[] getBinaryDatatype1() {
		return this.binaryDatatype1;
	}
	public void setBinaryDatatype1(byte[] binaryDatatype1) {
		getChangeTracker().addChange("binaryDatatype1", binaryDatatype1);
		this.binaryDatatype1 = binaryDatatype1;
	}
	
	public Object get(java.lang.String name) {
	
		if ("stringDatatype3".equals(name))
		{
			return this.stringDatatype3;
		}
		if ("binaryDatatype1".equals(name))
		{
			return this.binaryDatatype1;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("stringDatatype3".equals(name))
		{
			setStringDatatype3((java.lang.String) value);
			return;
		}
		if ("binaryDatatype1".equals(name))
		{
			setBinaryDatatype1((byte[]) value);
			return;
		}
	
		super.set(name, value);
	}
	
}
