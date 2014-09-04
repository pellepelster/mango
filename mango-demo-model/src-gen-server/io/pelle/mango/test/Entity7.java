package io.pelle.mango.test;

import javax.persistence.*;

@Entity
@Table(name = "entity7")
public class Entity7 extends io.pelle.mango.test.Entity6 {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.test.Entity7> ENTITY7 = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.test.Entity7>(io.pelle.mango.test.Entity7.class);

		public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(ENTITY7, "id");

		public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRING1 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY7, "string1", java.lang.String.class, -1, -1);

		
		public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
			
			return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
				
				
					STRING2
					,
					STRING1
			};
		}

	
	@Column(name = "entity7_string2")
	private java.lang.String string2;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor STRING2 = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(ENTITY7, "string2", java.lang.String.class, -1, -1);
	
	public java.lang.String getString2() {
		return this.string2;
	}
	
	public void setString2(java.lang.String string2) {
		getChangeTracker().addChange("string2", string2);
		this.string2 = string2;
	}

}
