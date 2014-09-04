package io.pelle.mango.test;

import javax.persistence.*;

@Entity
@Table(name = "entity4")
public class Entity4 extends io.pelle.mango.test.Entity3 {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.Entity4> ENTITY4 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.Entity4>(io.pelle.mango.test.Entity4.class);

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

	
	@Column(name = "entity4_stringdatatype4")
	private java.lang.String stringDatatype4;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE4 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY4, "stringDatatype4", java.lang.String.class, 0, -1);
	
	public java.lang.String getStringDatatype4() {
		return this.stringDatatype4;
	}
	
	public void setStringDatatype4(java.lang.String stringDatatype4) {
		getChangeTracker().addChange("stringDatatype4", stringDatatype4);
		this.stringDatatype4 = stringDatatype4;
	}

}
