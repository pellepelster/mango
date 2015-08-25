package io.pelle.mango.server.file;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.db.copy.ObjectFieldDescriptor;
import io.pelle.mango.db.copy.ObjectFieldIterator;
import io.pelle.mango.db.dao.IEntityCallback;
import io.pelle.mango.server.File;
import io.pelle.mango.server.base.BaseEntity;

public class EntityFileUUIDCallback implements IEntityCallback {

	@Autowired
	private FileStorage fileStorage;

	@Override
	public void beforeCreate(IBaseEntity entity) {

		for (ObjectFieldDescriptor fieldDescriptor : new ObjectFieldIterator(entity)) {

			if (File.class.isAssignableFrom(fieldDescriptor.getSourceType())) {

				File file = (File) fieldDescriptor.getSourceValue(entity);

				if (file != null) {
					fieldDescriptor.setSourceValue(entity, fileStorage.getFile(file.getFileUUID()));
				}
			} else if (List.class.isAssignableFrom(fieldDescriptor.getSourceType())) {

				List list = ((List) fieldDescriptor.getSourceValue(entity));

				ListIterator it = list.listIterator();

				while (it.hasNext()) {

					Object fileObject = it.next();

					if (File.class.isAssignableFrom(fileObject.getClass())) {
						File file = (File) fileObject;

						if (file != null) {
							it.set(fileStorage.getFile(file.getFileUUID()));
						}
					}
				}
			}

		}
	}

	@Override
	public boolean supports(Class<? extends IBaseEntity> entityClass) {
		return BaseEntity.class.isAssignableFrom(entityClass);
	}

	@Override
	public void beforeSave(IBaseEntity entity) {
		beforeCreate(entity);
	}

}
