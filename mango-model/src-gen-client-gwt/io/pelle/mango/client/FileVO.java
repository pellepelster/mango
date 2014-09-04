package io.pelle.mango.client;

@SuppressWarnings("serial")
public class FileVO extends io.pelle.mango.client.base.vo.BaseVO {

	public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.client.FileVO> FILE = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.client.FileVO>(io.pelle.mango.client.FileVO.class);

	public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(FILE, "id");


	
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
		
		return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
			
			
				FILENAME, 
				FILECONTENT, 
				FILEUUID
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
	
	private java.lang.String fileName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor FILENAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(FILE, "fileName", java.lang.String.class, 0, -1);
	public java.lang.String getFileName() {
		return this.fileName;
	}
	public void setFileName(java.lang.String fileName) {
		getChangeTracker().addChange("fileName", fileName);
		this.fileName = fileName;
	}
	private byte[] fileContent;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<byte[]> FILECONTENT = new io.pelle.mango.client.base.vo.AttributeDescriptor<byte[]>(FILE, "fileContent", byte[].class, byte[].class, false, -1);
	public byte[] getFileContent() {
		return this.fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		getChangeTracker().addChange("fileContent", fileContent);
		this.fileContent = fileContent;
	}
	private java.lang.String fileUUID;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor FILEUUID = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(FILE, "fileUUID", java.lang.String.class, 0, -1);
	public java.lang.String getFileUUID() {
		return this.fileUUID;
	}
	public void setFileUUID(java.lang.String fileUUID) {
		getChangeTracker().addChange("fileUUID", fileUUID);
		this.fileUUID = fileUUID;
	}
	
	public Object get(java.lang.String name) {
	
		if ("fileName".equals(name))
		{
			return this.fileName;
		}
		if ("fileContent".equals(name))
		{
			return this.fileContent;
		}
		if ("fileUUID".equals(name))
		{
			return this.fileUUID;
		}
	
		return super.get(name);
	}
	
	public void set(java.lang.String name, java.lang.Object value) {
	
		getChangeTracker().addChange(name, value);
	
		if ("fileName".equals(name))
		{
			setFileName((java.lang.String) value);
			return;
		}
		if ("fileContent".equals(name))
		{
			setFileContent((byte[]) value);
			return;
		}
		if ("fileUUID".equals(name))
		{
			setFileUUID((java.lang.String) value);
			return;
		}
	
		super.set(name, value);
	}
	
}
