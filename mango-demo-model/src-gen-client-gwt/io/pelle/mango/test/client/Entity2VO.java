package io.pelle.mango.test.client;

@SuppressWarnings("serial")
public class Entity2VO extends io.pelle.mango.client.base.vo.BaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.client.Entity2VO> ENTITY2 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.client.Entity2VO>(io.pelle.mango.test.client.Entity2VO.class);

	public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY2, "id");


	
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
		
		return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
			
			
				STRINGDATATYPE2, 
				ENTITY3DATATYPES
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
	
	private java.lang.String stringDatatype2;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE2 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY2, "stringDatatype2", java.lang.String.class, 32, -1);
	public java.lang.String getStringDatatype2() {
		return this.stringDatatype2;
	}
	public void setStringDatatype2(java.lang.String stringDatatype2) {
		getChangeTracker().addChange("stringDatatype2", stringDatatype2);
		this.stringDatatype2 = stringDatatype2;
	}
	private java.util.List<io.pelle.mango.test.client.Entity3VO> entity3Datatypes = new io.pelle.mango.client.base.vo.ChangeTrackingArrayList<io.pelle.mango.test.client.Entity3VO>();
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.util.List<io.pelle.mango.test.client.Entity3VO>> ENTITY3DATATYPES = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.util.List<io.pelle.mango.test.client.Entity3VO>>(ENTITY2, "entity3Datatypes", java.util.List.class, io.pelle.mango.test.client.Entity3VO.class, false, -1);
	public java.util.List<io.pelle.mango.test.client.Entity3VO> getEntity3Datatypes() {
		return this.entity3Datatypes;
	}
	public void setEntity3Datatypes(java.util.List<io.pelle.mango.test.client.Entity3VO> entity3Datatypes) {
		getChangeTracker().addChange("entity3Datatypes", entity3Datatypes);
		this.entity3Datatypes = entity3Datatypes;
	}
	
	public Object get(java.lang.String name) {
	
		if ("stringDatatype2".equals(name))
		{
			return this.stringDatatype2;
		}
		if ("entity3Datatypes".equals(name))
		{
			return this.entity3Datatypes;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("stringDatatype2".equals(name))
		{
			setStringDatatype2((java.lang.String) value);
			return;
		}
		if ("entity3Datatypes".equals(name))
		{
			setEntity3Datatypes((java.util.List<io.pelle.mango.test.client.Entity3VO>) value);
			return;
		}
	
		super.set(name, value);
	}
	
}
