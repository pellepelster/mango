package io.pelle.mango.server.system;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.db.dao.IEntityCallback;
import io.pelle.mango.server.File;

public class FileEntityCallback implements IEntityCallback {

	@Override
	public void beforeCreate(IBaseEntity entity) {

		File file = (File) entity;

		if (StringUtils.isEmpty(file.getFileUUID())) {
			file.setFileUUID(UUID.randomUUID().toString());
		}

	}

	@Override
	public boolean supports(Class<? extends IBaseEntity> entityClass) {
		return File.class.isAssignableFrom(entityClass);
	}

	@Override
	public void beforeSave(IBaseEntity entity) {
		beforeCreate(entity);
	}

}
