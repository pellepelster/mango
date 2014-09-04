package io.pelle.mango;

import javax.persistence.*;

@Entity
@Table(name = "dictionaryhierarchicalnode")
public class DictionaryHierarchicalNode extends io.pelle.mango.server.base.BaseEntity {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.DictionaryHierarchicalNode> DICTIONARYHIERARCHICALNODE = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.DictionaryHierarchicalNode>(io.pelle.mango.DictionaryHierarchicalNode.class);

		public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(DICTIONARYHIERARCHICALNODE, "id");


		
		public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
			
			return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
				
				
					LABEL, 
					DICTIONARYNAME, 
					VOID, 
					VOCLASSNAME, 
					PARENTCLASSNAME, 
					PARENTVOID, 
					HASCHILDREN, 
					CHILDREN
					,
			};
		}

	@Id
	@Column(name = "dictionaryhierarchicalnode_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "dictionaryhierarchicalnode_id_seq")
	@SequenceGenerator(name = "dictionaryhierarchicalnode_id_seq", sequenceName = "dictionaryhierarchicalnode_id_seq", allocationSize = 1)
	private long id;
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "dictionaryhierarchicalnode_label")
	private java.lang.String label;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor LABEL = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYHIERARCHICALNODE, "label", java.lang.String.class, -1, -1);
	
	public java.lang.String getLabel() {
		return this.label;
	}
	
	public void setLabel(java.lang.String label) {
		getChangeTracker().addChange("label", label);
		this.label = label;
	}
	@Column(name = "dictionaryhierarchicalnode_dictionaryname")
	private java.lang.String dictionaryName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor DICTIONARYNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYHIERARCHICALNODE, "dictionaryName", java.lang.String.class, -1, -1);
	
	public java.lang.String getDictionaryName() {
		return this.dictionaryName;
	}
	
	public void setDictionaryName(java.lang.String dictionaryName) {
		getChangeTracker().addChange("dictionaryName", dictionaryName);
		this.dictionaryName = dictionaryName;
	}
	@Column(name = "dictionaryhierarchicalnode_void")
	private java.lang.Long voId;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Long> VOID = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Long>(DICTIONARYHIERARCHICALNODE, "voId", java.lang.Long.class, java.lang.Long.class, false, -1);
	
	public java.lang.Long getVoId() {
		return this.voId;
	}
	
	public void setVoId(java.lang.Long voId) {
		getChangeTracker().addChange("voId", voId);
		this.voId = voId;
	}
	@Column(name = "dictionaryhierarchicalnode_voclassname")
	private java.lang.String voClassName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor VOCLASSNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYHIERARCHICALNODE, "voClassName", java.lang.String.class, -1, -1);
	
	public java.lang.String getVoClassName() {
		return this.voClassName;
	}
	
	public void setVoClassName(java.lang.String voClassName) {
		getChangeTracker().addChange("voClassName", voClassName);
		this.voClassName = voClassName;
	}
	@Column(name = "dictionaryhierarchicalnode_parentclassname")
	private java.lang.String parentClassName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor PARENTCLASSNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYHIERARCHICALNODE, "parentClassName", java.lang.String.class, -1, -1);
	
	public java.lang.String getParentClassName() {
		return this.parentClassName;
	}
	
	public void setParentClassName(java.lang.String parentClassName) {
		getChangeTracker().addChange("parentClassName", parentClassName);
		this.parentClassName = parentClassName;
	}
	@Column(name = "dictionaryhierarchicalnode_parentvoid")
	private java.lang.Long parentVOId;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Long> PARENTVOID = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Long>(DICTIONARYHIERARCHICALNODE, "parentVOId", java.lang.Long.class, java.lang.Long.class, false, -1);
	
	public java.lang.Long getParentVOId() {
		return this.parentVOId;
	}
	
	public void setParentVOId(java.lang.Long parentVOId) {
		getChangeTracker().addChange("parentVOId", parentVOId);
		this.parentVOId = parentVOId;
	}
	@Column(name = "dictionaryhierarchicalnode_haschildren")
	private java.lang.Boolean hasChildren = false;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Boolean> HASCHILDREN = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Boolean>(DICTIONARYHIERARCHICALNODE, "hasChildren", java.lang.Boolean.class, java.lang.Boolean.class, false, -1);
	
	public java.lang.Boolean getHasChildren() {
		return this.hasChildren;
	}
	
	public void setHasChildren(java.lang.Boolean hasChildren) {
		getChangeTracker().addChange("hasChildren", hasChildren);
		this.hasChildren = hasChildren;
	}
	@OneToMany()
	private java.util.List<io.pelle.mango.DictionaryHierarchicalNode> children = new io.pelle.mango.client.base.vo.ChangeTrackingArrayList<io.pelle.mango.DictionaryHierarchicalNode>();
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.util.List<io.pelle.mango.DictionaryHierarchicalNode>> CHILDREN = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.util.List<io.pelle.mango.DictionaryHierarchicalNode>>(DICTIONARYHIERARCHICALNODE, "children", java.util.List.class, io.pelle.mango.DictionaryHierarchicalNode.class, false, -1);
	
	public java.util.List<io.pelle.mango.DictionaryHierarchicalNode> getChildren() {
		return this.children;
	}
	
	public void setChildren(java.util.List<io.pelle.mango.DictionaryHierarchicalNode> children) {
		getChangeTracker().addChange("children", children);
		this.children = children;
	}

}
