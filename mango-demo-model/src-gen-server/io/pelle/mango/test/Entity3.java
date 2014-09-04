package io.pelle.mango.test;

import javax.persistence.*;

@Entity
@Table(name = "entity3")
@javax.persistence.Inheritance(strategy = javax.persistence.InheritanceType.JOINED)
@javax.persistence.PrimaryKeyJoinColumn(name="entity3_id")
public class Entity3 extends io.pelle.mango.server.base.BaseEntity {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.Entity3> ENTITY3 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.Entity3>(io.pelle.mango.test.Entity3.class);

		public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY3, "id");


		
		public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
			
			return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
				
			};
		}

	@Id
	@Column(name = "entity3_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "entity3_id_seq")
	@SequenceGenerator(name = "entity3_id_seq", sequenceName = "entity3_id_seq", allocationSize = 1)
	private long id;
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "entity3_stringdatatype3")
	private java.lang.String stringDatatype3;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE3 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY3, "stringDatatype3", java.lang.String.class, 0, -1);
	
	public java.lang.String getStringDatatype3() {
		return this.stringDatatype3;
	}
	
	public void setStringDatatype3(java.lang.String stringDatatype3) {
		getChangeTracker().addChange("stringDatatype3", stringDatatype3);
		this.stringDatatype3 = stringDatatype3;
	}
	@Column(name = "entity3_binarydatatype1")
	private byte[] binaryDatatype1;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<byte[]> BINARYDATATYPE1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<byte[]>(ENTITY3, "binaryDatatype1", byte[].class, byte[].class, false, -1);
	
	public byte[] getBinaryDatatype1() {
		return this.binaryDatatype1;
	}
	
	public void setBinaryDatatype1(byte[] binaryDatatype1) {
		getChangeTracker().addChange("binaryDatatype1", binaryDatatype1);
		this.binaryDatatype1 = binaryDatatype1;
	}

}
