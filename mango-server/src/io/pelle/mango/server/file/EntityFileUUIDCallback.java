package io.pelle.mango.server.file;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.db.dao.IEntityCallback;
import io.pelle.mango.db.voquery.AttributesDescriptorQuery;
import io.pelle.mango.server.File;
import io.pelle.mango.server.base.BaseEntity;

public class EntityFileUUIDCallback implements IEntityCallback {

	@Autowired
	private FileStorage fileStorage;

	@Override
	public void beforeCreate(IBaseEntity entity) {

		for (Map.Entry<String, Object> mapEntry : entity.getData().entrySet()) {

			AttributesDescriptorQuery<?> query = AttributesDescriptorQuery.createQuery(entity.getClass()).byName(mapEntry.getKey());

			if (query.hasExactlyOne()) {

				IAttributeDescriptor<?> attributeDescriptor = query.getSingleResult();

				if (File.class.isAssignableFrom(attributeDescriptor.getListAttributeType())) {

					if (List.class.isAssignableFrom(attributeDescriptor.getAttributeType())) {

						List<String> fileUUIDs = (List<String>) mapEntry.getValue();

						try {
							BeanUtils.setProperty(entity, attributeDescriptor.getAttributeName(), fileStorage.getFiles(fileUUIDs));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}

					} else {
						String fileUUID = mapEntry.getValue().toString();
						try {
							BeanUtils.setProperty(entity, attributeDescriptor.getAttributeName(), fileStorage.getFile(fileUUID));
						} catch (Exception e) {
							throw new RuntimeException(e);
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
