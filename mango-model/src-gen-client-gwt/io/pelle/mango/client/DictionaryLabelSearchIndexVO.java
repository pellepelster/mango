package io.pelle.mango.client;

@SuppressWarnings("serial")
public class DictionaryLabelSearchIndexVO extends io.pelle.mango.client.base.vo.BaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.client.DictionaryLabelSearchIndexVO> DICTIONARYLABELSEARCHINDEX = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.client.DictionaryLabelSearchIndexVO>(io.pelle.mango.client.DictionaryLabelSearchIndexVO.class);

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

	private long id;
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private java.lang.String dictionaryName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor DICTIONARYNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYLABELSEARCHINDEX, "dictionaryName", java.lang.String.class, -1, -1);
	public java.lang.String getDictionaryName() {
		return this.dictionaryName;
	}
	public void setDictionaryName(java.lang.String dictionaryName) {
		getChangeTracker().addChange("dictionaryName", dictionaryName);
		this.dictionaryName = dictionaryName;
	}
	private java.lang.String voClassName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor VOCLASSNAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYLABELSEARCHINDEX, "voClassName", java.lang.String.class, -1, -1);
	public java.lang.String getVoClassName() {
		return this.voClassName;
	}
	public void setVoClassName(java.lang.String voClassName) {
		getChangeTracker().addChange("voClassName", voClassName);
		this.voClassName = voClassName;
	}
	private java.lang.Long voId;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<java.lang.Long> VOID = new io.pelle.mango.client.base.vo.AttributeDescriptor<java.lang.Long>(DICTIONARYLABELSEARCHINDEX, "voId", java.lang.Long.class, java.lang.Long.class, false, -1);
	public java.lang.Long getVoId() {
		return this.voId;
	}
	public void setVoId(java.lang.Long voId) {
		getChangeTracker().addChange("voId", voId);
		this.voId = voId;
	}
	private java.lang.String text;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor TEXT = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(DICTIONARYLABELSEARCHINDEX, "text", java.lang.String.class, -1, -1);
	public java.lang.String getText() {
		return this.text;
	}
	public void setText(java.lang.String text) {
		getChangeTracker().addChange("text", text);
		this.text = text;
	}
	
	public Object get(java.lang.String name) {
	
		if ("dictionaryName".equals(name))
		{
			return this.dictionaryName;
		}
		if ("voClassName".equals(name))
		{
			return this.voClassName;
		}
		if ("voId".equals(name))
		{
			return this.voId;
		}
		if ("text".equals(name))
		{
			return this.text;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("dictionaryName".equals(name))
		{
			setDictionaryName((java.lang.String) value);
			return;
		}
		if ("voClassName".equals(name))
		{
			setVoClassName((java.lang.String) value);
			return;
		}
		if ("voId".equals(name))
		{
			setVoId((java.lang.Long) value);
			return;
		}
		if ("text".equals(name))
		{
			setText((java.lang.String) value);
			return;
		}
	
		super.set(name, value);
	}
	
}
