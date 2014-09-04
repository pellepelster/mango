package io.pelle.mango;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class File extends io.pelle.mango.server.base.BaseEntity {

		public static final io.pelle.mango.client.base.vo.IEntityDescriptor<io.pelle.mango.File> FILE = new io.pelle.mango.client.base.vo.EntityDescriptor<io.pelle.mango.File>(io.pelle.mango.File.class);

		public static io.pelle.mango.client.base.vo.LongAttributeDescriptor ID = new io.pelle.mango.client.base.vo.LongAttributeDescriptor(FILE, "id");


		
		public static io.pelle.mango.client.base.vo.IAttributeDescriptor<?>[] getAttributeDescriptors() {
			
			return new io.pelle.mango.client.base.vo.IAttributeDescriptor[]{
				
				
					FILENAME, 
					FILECONTENT, 
					FILEUUID
					,
			};
		}

	@Id
	@Column(name = "file_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "file_id_seq")
	@SequenceGenerator(name = "file_id_seq", sequenceName = "file_id_seq", allocationSize = 1)
	private long id;
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "file_filename")
	private java.lang.String fileName;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor FILENAME = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(FILE, "fileName", java.lang.String.class, 0, -1);
	
	public java.lang.String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(java.lang.String fileName) {
		getChangeTracker().addChange("fileName", fileName);
		this.fileName = fileName;
	}
	@Column(name = "file_filecontent")
	private byte[] fileContent;
	public static io.pelle.mango.client.base.vo.IAttributeDescriptor<byte[]> FILECONTENT = new io.pelle.mango.client.base.vo.AttributeDescriptor<byte[]>(FILE, "fileContent", byte[].class, byte[].class, false, -1);
	
	public byte[] getFileContent() {
		return this.fileContent;
	}
	
	public void setFileContent(byte[] fileContent) {
		getChangeTracker().addChange("fileContent", fileContent);
		this.fileContent = fileContent;
	}
	@Column(name = "file_fileuuid")
	private java.lang.String fileUUID;
	public static io.pelle.mango.client.base.vo.StringAttributeDescriptor FILEUUID = new io.pelle.mango.client.base.vo.StringAttributeDescriptor(FILE, "fileUUID", java.lang.String.class, 0, -1);
	
	public java.lang.String getFileUUID() {
		return this.fileUUID;
	}
	
	public void setFileUUID(java.lang.String fileUUID) {
		getChangeTracker().addChange("fileUUID", fileUUID);
		this.fileUUID = fileUUID;
	}

}
