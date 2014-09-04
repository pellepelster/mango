package io.pelle.mango.test;

import javax.persistence.*;

@Entity
@Table(name = "entity1")
public class Entity1 extends io.pelle.mango.server.base.BaseEntity {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.Entity1> ENTITY1 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.Entity1>(io.pelle.mango.test.Entity1.class);

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

	@Id
	@Column(name = "entity1_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "entity1_id_seq")
	@SequenceGenerator(name = "entity1_id_seq", sequenceName = "entity1_id_seq", allocationSize = 1)
	private long id;
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "entity1_stringdatatype1")
	private java.lang.String stringDatatype1;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE1 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY1, "stringDatatype1", java.lang.String.class, 42, 0);
	
	public java.lang.String getStringDatatype1() {
		return this.stringDatatype1;
	}
	
	public void setStringDatatype1(java.lang.String stringDatatype1) {
		getChangeTracker().addChange("stringDatatype1", stringDatatype1);
		this.stringDatatype1 = stringDatatype1;
	}
	@Column(name = "entity1_booleandatatype1")
	private java.lang.Boolean booleanDatatype1 = false;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Boolean> BOOLEANDATATYPE1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Boolean>(ENTITY1, "booleanDatatype1", java.lang.Boolean.class, java.lang.Boolean.class, false, -1);
	
	public java.lang.Boolean getBooleanDatatype1() {
		return this.booleanDatatype1;
	}
	
	public void setBooleanDatatype1(java.lang.Boolean booleanDatatype1) {
		getChangeTracker().addChange("booleanDatatype1", booleanDatatype1);
		this.booleanDatatype1 = booleanDatatype1;
	}
	@javax.persistence.ElementCollection(fetch=javax.persistence.FetchType.EAGER)
	private java.util.List<java.lang.String> stringDatatype1List = new io.pelle.mango.client.base.vo.ChangeTrackingArrayList<java.lang.String>();
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE1LIST = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY1, "stringDatatype1List", java.util.List.class, 42, -1);
	
	public java.util.List<java.lang.String> getStringDatatype1List() {
		return this.stringDatatype1List;
	}
	
	public void setStringDatatype1List(java.util.List<java.lang.String> stringDatatype1List) {
		getChangeTracker().addChange("stringDatatype1List", stringDatatype1List);
		this.stringDatatype1List = stringDatatype1List;
	}
	@OneToOne()
	private io.pelle.mango.test.Entity2 entity2Datatype;
	public static io.pelle.mango.client.base.vo.EntityAttributeDescriptor<io.pelle.mango.test.Entity2> ENTITY2DATATYPE = new io.pelle.mango.client.base.vo.EntityAttributeDescriptor<io.pelle.mango.test.Entity2>(ENTITY1, "entity2Datatype", io.pelle.mango.test.Entity2.class);
	
	public io.pelle.mango.test.Entity2 getEntity2Datatype() {
		return this.entity2Datatype;
	}
	
	public void setEntity2Datatype(io.pelle.mango.test.Entity2 entity2Datatype) {
		getChangeTracker().addChange("entity2Datatype", entity2Datatype);
		this.entity2Datatype = entity2Datatype;
	}
	@Column(name = "entity1_enumeration1datatype")
	private io.pelle.mango.test.client.ENUMERATION1 enumeration1Datatype;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<io.pelle.mango.test.client.ENUMERATION1> ENUMERATION1DATATYPE = new io.pelle.mango.client.base.vo.AttributeDescriptor<io.pelle.mango.test.client.ENUMERATION1>(ENTITY1, "enumeration1Datatype", io.pelle.mango.test.client.ENUMERATION1.class, io.pelle.mango.test.client.ENUMERATION1.class, false, -1);
	
	public io.pelle.mango.test.client.ENUMERATION1 getEnumeration1Datatype() {
		return this.enumeration1Datatype;
	}
	
	public void setEnumeration1Datatype(io.pelle.mango.test.client.ENUMERATION1 enumeration1Datatype) {
		getChangeTracker().addChange("enumeration1Datatype", enumeration1Datatype);
		this.enumeration1Datatype = enumeration1Datatype;
	}

}
