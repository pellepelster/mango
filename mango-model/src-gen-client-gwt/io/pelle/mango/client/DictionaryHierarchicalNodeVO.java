package io.pelle.mango.client;

@SuppressWarnings("serial")
public class DictionaryHierarchicalNodeVO extends io.pelle.mango.client.base.vo.BaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.client.DictionaryHierarchicalNodeVO> DICTIONARYHIERARCHICALNODE = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.client.DictionaryHierarchicalNodeVO>(io.pelle.mango.client.DictionaryHierarchicalNodeVO.class);

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

	private long id;
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private java.lang.String label;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor LABEL = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYHIERARCHICALNODE, "label", java.lang.String.class, -1, -1);
	public java.lang.String getLabel() {
		return this.label;
	}
	public void setLabel(java.lang.String label) {
		getChangeTracker().addChange("label", label);
		this.label = label;
	}
	private java.lang.String dictionaryName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor DICTIONARYNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYHIERARCHICALNODE, "dictionaryName", java.lang.String.class, -1, -1);
	public java.lang.String getDictionaryName() {
		return this.dictionaryName;
	}
	public void setDictionaryName(java.lang.String dictionaryName) {
		getChangeTracker().addChange("dictionaryName", dictionaryName);
		this.dictionaryName = dictionaryName;
	}
	private java.lang.Long voId;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Long> VOID = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Long>(DICTIONARYHIERARCHICALNODE, "voId", java.lang.Long.class, java.lang.Long.class, false, -1);
	public java.lang.Long getVoId() {
		return this.voId;
	}
	public void setVoId(java.lang.Long voId) {
		getChangeTracker().addChange("voId", voId);
		this.voId = voId;
	}
	private java.lang.String voClassName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor VOCLASSNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYHIERARCHICALNODE, "voClassName", java.lang.String.class, -1, -1);
	public java.lang.String getVoClassName() {
		return this.voClassName;
	}
	public void setVoClassName(java.lang.String voClassName) {
		getChangeTracker().addChange("voClassName", voClassName);
		this.voClassName = voClassName;
	}
	private java.lang.String parentClassName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor PARENTCLASSNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYHIERARCHICALNODE, "parentClassName", java.lang.String.class, -1, -1);
	public java.lang.String getParentClassName() {
		return this.parentClassName;
	}
	public void setParentClassName(java.lang.String parentClassName) {
		getChangeTracker().addChange("parentClassName", parentClassName);
		this.parentClassName = parentClassName;
	}
	private java.lang.Long parentVOId;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Long> PARENTVOID = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Long>(DICTIONARYHIERARCHICALNODE, "parentVOId", java.lang.Long.class, java.lang.Long.class, false, -1);
	public java.lang.Long getParentVOId() {
		return this.parentVOId;
	}
	public void setParentVOId(java.lang.Long parentVOId) {
		getChangeTracker().addChange("parentVOId", parentVOId);
		this.parentVOId = parentVOId;
	}
	private java.lang.Boolean hasChildren = false;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Boolean> HASCHILDREN = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Boolean>(DICTIONARYHIERARCHICALNODE, "hasChildren", java.lang.Boolean.class, java.lang.Boolean.class, false, -1);
	public java.lang.Boolean getHasChildren() {
		return this.hasChildren;
	}
	public void setHasChildren(java.lang.Boolean hasChildren) {
		getChangeTracker().addChange("hasChildren", hasChildren);
		this.hasChildren = hasChildren;
	}
	private java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO> children = new io.pelle.mango.client.base.vo.ChangeTrackingArrayList<io.pelle.mango.client.DictionaryHierarchicalNodeVO>();
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO>> CHILDREN = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO>>(DICTIONARYHIERARCHICALNODE, "children", java.util.List.class, io.pelle.mango.client.DictionaryHierarchicalNodeVO.class, false, -1);
	public java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO> getChildren() {
		return this.children;
	}
	public void setChildren(java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO> children) {
		getChangeTracker().addChange("children", children);
		this.children = children;
	}
	
	public Object get(java.lang.String name) {
	
		if ("label".equals(name))
		{
			return this.label;
		}
		if ("dictionaryName".equals(name))
		{
			return this.dictionaryName;
		}
		if ("voId".equals(name))
		{
			return this.voId;
		}
		if ("voClassName".equals(name))
		{
			return this.voClassName;
		}
		if ("parentClassName".equals(name))
		{
			return this.parentClassName;
		}
		if ("parentVOId".equals(name))
		{
			return this.parentVOId;
		}
		if ("hasChildren".equals(name))
		{
			return this.hasChildren;
		}
		if ("children".equals(name))
		{
			return this.children;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("label".equals(name))
		{
			setLabel((java.lang.String) value);
			return;
		}
		if ("dictionaryName".equals(name))
		{
			setDictionaryName((java.lang.String) value);
			return;
		}
		if ("voId".equals(name))
		{
			setVoId((java.lang.Long) value);
			return;
		}
		if ("voClassName".equals(name))
		{
			setVoClassName((java.lang.String) value);
			return;
		}
		if ("parentClassName".equals(name))
		{
			setParentClassName((java.lang.String) value);
			return;
		}
		if ("parentVOId".equals(name))
		{
			setParentVOId((java.lang.Long) value);
			return;
		}
		if ("hasChildren".equals(name))
		{
			setHasChildren((java.lang.Boolean) value);
			return;
		}
		if ("children".equals(name))
		{
			setChildren((java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO>) value);
			return;
		}
	
		super.set(name, value);
	}
	
}
