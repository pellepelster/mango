package io.pelle.mango.test;

import javax.persistence.*;

@Entity
@Table(name = "entity2")
public class Entity2 extends io.pelle.mango.server.base.BaseEntity {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.Entity2> ENTITY2 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.Entity2>(io.pelle.mango.test.Entity2.class);

		public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY2, "id");


		
		public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
			
			return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
				
				
					STRINGDATATYPE2, 
					ENTITY3DATATYPES
					,
			};
		}

	@Id
	@Column(name = "entity2_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "entity2_id_seq")
	@SequenceGenerator(name = "entity2_id_seq", sequenceName = "entity2_id_seq", allocationSize = 1)
	private long id;
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "entity2_stringdatatype2")
	private java.lang.String stringDatatype2;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRINGDATATYPE2 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY2, "stringDatatype2", java.lang.String.class, 32, -1);
	
	public java.lang.String getStringDatatype2() {
		return this.stringDatatype2;
	}
	
	public void setStringDatatype2(java.lang.String stringDatatype2) {
		getChangeTracker().addChange("stringDatatype2", stringDatatype2);
		this.stringDatatype2 = stringDatatype2;
	}
	@OneToMany()
	private java.util.List<io.pelle.mango.test.Entity3> entity3Datatypes = new io.pelle.mango.client.base.vo.ChangeTrackingArrayList<io.pelle.mango.test.Entity3>();
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.util.List<io.pelle.mango.test.Entity3>> ENTITY3DATATYPES = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.util.List<io.pelle.mango.test.Entity3>>(ENTITY2, "entity3Datatypes", java.util.List.class, io.pelle.mango.test.Entity3.class, false, -1);
	
	public java.util.List<io.pelle.mango.test.Entity3> getEntity3Datatypes() {
		return this.entity3Datatypes;
	}
	
	public void setEntity3Datatypes(java.util.List<io.pelle.mango.test.Entity3> entity3Datatypes) {
		getChangeTracker().addChange("entity3Datatypes", entity3Datatypes);
		this.entity3Datatypes = entity3Datatypes;
	}

}
