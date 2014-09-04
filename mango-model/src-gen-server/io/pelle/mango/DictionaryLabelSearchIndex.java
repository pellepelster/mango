package io.pelle.mango;

import javax.persistence.*;

@Entity
@Table(name = "dictionarylabelsearchindex")
public class DictionaryLabelSearchIndex extends io.pelle.mango.server.base.BaseEntity {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.DictionaryLabelSearchIndex> DICTIONARYLABELSEARCHINDEX = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.DictionaryLabelSearchIndex>(io.pelle.mango.DictionaryLabelSearchIndex.class);

		public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(DICTIONARYLABELSEARCHINDEX, "id");


		
		public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
			
			return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
				
				
					DICTIONARYNAME, 
					VOCLASSNAME, 
					VOID, 
					TEXT
					,
			};
		}

	@Id
	@Column(name = "dictionarylabelsearchindex_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "dictionarylabelsearchindex_id_seq")
	@SequenceGenerator(name = "dictionarylabelsearchindex_id_seq", sequenceName = "dictionarylabelsearchindex_id_seq", allocationSize = 1)
	private long id;
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "dictionarylabelsearchindex_dictionaryname")
	private java.lang.String dictionaryName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor DICTIONARYNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYLABELSEARCHINDEX, "dictionaryName", java.lang.String.class, -1, -1);
	
	public java.lang.String getDictionaryName() {
		return this.dictionaryName;
	}
	
	public void setDictionaryName(java.lang.String dictionaryName) {
		getChangeTracker().addChange("dictionaryName", dictionaryName);
		this.dictionaryName = dictionaryName;
	}
	@Column(name = "dictionarylabelsearchindex_voclassname")
	private java.lang.String voClassName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor VOCLASSNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYLABELSEARCHINDEX, "voClassName", java.lang.String.class, -1, -1);
	
	public java.lang.String getVoClassName() {
		return this.voClassName;
	}
	
	public void setVoClassName(java.lang.String voClassName) {
		getChangeTracker().addChange("voClassName", voClassName);
		this.voClassName = voClassName;
	}
	@Column(name = "dictionarylabelsearchindex_void")
	private java.lang.Long voId;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Long> VOID = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Long>(DICTIONARYLABELSEARCHINDEX, "voId", java.lang.Long.class, java.lang.Long.class, false, -1);
	
	public java.lang.Long getVoId() {
		return this.voId;
	}
	
	public void setVoId(java.lang.Long voId) {
		getChangeTracker().addChange("voId", voId);
		this.voId = voId;
	}
	@Column(name = "dictionarylabelsearchindex_text")
	private java.lang.String text;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor TEXT = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYLABELSEARCHINDEX, "text", java.lang.String.class, -1, -1);
	
	public java.lang.String getText() {
		return this.text;
	}
	
	public void setText(java.lang.String text) {
		getChangeTracker().addChange("text", text);
		this.text = text;
	}

}
