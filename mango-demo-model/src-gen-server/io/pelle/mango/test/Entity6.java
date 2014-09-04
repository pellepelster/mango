package io.pelle.mango.test;

import javax.persistence.*;

@Entity
@Table(name = "entity6")
@javax.persistence.Inheritance(strategy = javax.persistence.InheritanceType.JOINED)
@javax.persistence.PrimaryKeyJoinColumn(name="entity6_id")
public class Entity6 extends io.pelle.mango.server.base.BaseEntity {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.Entity6> ENTITY6 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.Entity6>(io.pelle.mango.test.Entity6.class);

		public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY6, "id");


		
		public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
			
			return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
				
			};
		}

	@Id
	@Column(name = "entity6_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "entity6_id_seq")
	@SequenceGenerator(name = "entity6_id_seq", sequenceName = "entity6_id_seq", allocationSize = 1)
	private long id;
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "entity6_string1")
	private java.lang.String string1;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRING1 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY6, "string1", java.lang.String.class, -1, -1);
	
	public java.lang.String getString1() {
		return this.string1;
	}
	
	public void setString1(java.lang.String string1) {
		getChangeTracker().addChange("string1", string1);
		this.string1 = string1;
	}

}
