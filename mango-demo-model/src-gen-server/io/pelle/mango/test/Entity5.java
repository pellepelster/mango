package io.pelle.mango.test;

import javax.persistence.*;

@Entity
@Table(name = "entity5")
public class Entity5 extends io.pelle.mango.server.base.BaseEntity {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.Entity5> ENTITY5 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.Entity5>(io.pelle.mango.test.Entity5.class);

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

	@Id
	@Column(name = "entity5_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "entity5_id_seq")
	@SequenceGenerator(name = "entity5_id_seq", sequenceName = "entity5_id_seq", allocationSize = 1)
	private long id;
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@OneToOne()
	private io.pelle.mango.test.Entity4 entity4;
	public static io.pelle.mango.client.base.vo.EntityAttributeDescriptor<io.pelle.mango.test.Entity4> ENTITY4 = new io.pelle.mango.client.base.vo.EntityAttributeDescriptor<io.pelle.mango.test.Entity4>(ENTITY5, "entity4", io.pelle.mango.test.Entity4.class);
	
	public io.pelle.mango.test.Entity4 getEntity4() {
		return this.entity4;
	}
	
	public void setEntity4(io.pelle.mango.test.Entity4 entity4) {
		getChangeTracker().addChange("entity4", entity4);
		this.entity4 = entity4;
	}
	@OneToMany()
	private java.util.List<io.pelle.mango.test.Entity4> entity4s = new io.pelle.mango.client.base.vo.ChangeTrackingArrayList<io.pelle.mango.test.Entity4>();
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.util.List<io.pelle.mango.test.Entity4>> ENTITY4S = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.util.List<io.pelle.mango.test.Entity4>>(ENTITY5, "entity4s", java.util.List.class, io.pelle.mango.test.Entity4.class, false, -1);
	
	public java.util.List<io.pelle.mango.test.Entity4> getEntity4s() {
		return this.entity4s;
	}
	
	public void setEntity4s(java.util.List<io.pelle.mango.test.Entity4> entity4s) {
		getChangeTracker().addChange("entity4s", entity4s);
		this.entity4s = entity4s;
	}
	@Column(name = "entity5_string1")
	private java.lang.String string1;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRING1 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY5, "string1", java.lang.String.class, -1, -1);
	
	public java.lang.String getString1() {
		return this.string1;
	}
	
	public void setString1(java.lang.String string1) {
		getChangeTracker().addChange("string1", string1);
		this.string1 = string1;
	}
	@Column(name = "entity5_binary1")
	private byte[] binary1;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<byte[]> BINARY1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<byte[]>(ENTITY5, "binary1", byte[].class, byte[].class, false, -1);
	
	public byte[] getBinary1() {
		return this.binary1;
	}
	
	public void setBinary1(byte[] binary1) {
		getChangeTracker().addChange("binary1", binary1);
		this.binary1 = binary1;
	}
	@Column(name = "entity5_boolean1")
	private java.lang.Boolean boolean1 = false;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Boolean> BOOLEAN1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Boolean>(ENTITY5, "boolean1", java.lang.Boolean.class, java.lang.Boolean.class, false, -1);
	
	public java.lang.Boolean getBoolean1() {
		return this.boolean1;
	}
	
	public void setBoolean1(java.lang.Boolean boolean1) {
		getChangeTracker().addChange("boolean1", boolean1);
		this.boolean1 = boolean1;
	}
	@Column(name = "entity5_enumeration1")
	private io.pelle.mango.test.client.ENUMERATION1 enumeration1;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<io.pelle.mango.test.client.ENUMERATION1> ENUMERATION1 = new io.pelle.mango.client.base.vo.AttributeDescriptor<io.pelle.mango.test.client.ENUMERATION1>(ENTITY5, "enumeration1", io.pelle.mango.test.client.ENUMERATION1.class, io.pelle.mango.test.client.ENUMERATION1.class, false, -1);
	
	public io.pelle.mango.test.client.ENUMERATION1 getEnumeration1() {
		return this.enumeration1;
	}
	
	public void setEnumeration1(io.pelle.mango.test.client.ENUMERATION1 enumeration1) {
		getChangeTracker().addChange("enumeration1", enumeration1);
		this.enumeration1 = enumeration1;
	}

}
