package io.pelle.mango.test.client;

@SuppressWarnings("serial")
public class Entity4VO extends io.pelle.mango.test.client.Entity3VO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.client.Entity4VO> ENTITY4 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.client.Entity4VO>(io.pelle.mango.test.client.Entity4VO.class);

	public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY4, "id");

	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE3 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY4, "stringDatatype3", java.lang.String.class, 0, -1);
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<byte[]> BINARYDATATYPE1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<byte[]>(ENTITY4, "binaryDatatype1", byte[].class, byte[].class, false, -1);

	
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
		
		return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
			
			
				STRINGDATATYPE4
				,
				STRINGDATATYPE3, 
				BINARYDATATYPE1
		};
	}

	private long id;
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private java.lang.String stringDatatype4;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE4 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY4, "stringDatatype4", java.lang.String.class, 0, -1);
	public java.lang.String getStringDatatype4() {
		return this.stringDatatype4;
	}
	public void setStringDatatype4(java.lang.String stringDatatype4) {
		getChangeTracker().addChange("stringDatatype4", stringDatatype4);
		this.stringDatatype4 = stringDatatype4;
	}
	
	public Object get(java.lang.String name) {
	
		if ("stringDatatype4".equals(name))
		{
			return this.stringDatatype4;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("stringDatatype4".equals(name))
		{
			setStringDatatype4((java.lang.String) value);
			return;
		}
	
		super.set(name, value);
	}
	
}
