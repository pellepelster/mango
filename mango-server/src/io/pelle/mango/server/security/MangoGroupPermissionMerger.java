package io.pelle.mango.server.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.db.dao.IEntityCallback;

public class MangoGroupPermissionMerger implements IEntityCallback {

	class DuplicateRemover implements Predicate<MangoOperation> {

		private final Set<String> set = new HashSet<>();

		@Override
		public boolean apply(MangoOperation input) {

			boolean contains = set.contains(input.getOperationId());

			if (!contains) {
				set.add(input.getOperationId());
			}

			return !contains;
		}

	}

	@Override
	public void beforeCreate(IBaseEntity entity) {

		MangoGroup group = (MangoGroup) entity;

		Map<String, MangoPermission> permissions = new HashMap<>();
		List<MangoPermission> mergedPermissions = new ArrayList<>();

		for (MangoPermission permission : group.getPermissions()) {

			MangoPermission currentPermission = permissions.get(permission.getPermissionId());

			if (currentPermission == null) {
				permissions.put(permission.getPermissionId(), permission);
				currentPermission = permission;
			} else {
				currentPermission.getOperations().addAll(permission.getOperations());
				mergedPermissions.add(permission);
			}
		}

		group.getPermissions().removeAll(mergedPermissions);

		for (MangoPermission permission : group.getPermissions()) {

			Collection<MangoOperation> filteredOpertations = new ArrayList(Collections2.filter(permission.getOperations(), new DuplicateRemover()));

			permission.getOperations().clear();
			permission.getOperations().addAll(filteredOpertations);
		}

	}

	@Override
	public boolean supports(Class<? extends IBaseEntity> entityClass) {
		return MangoGroup.class.isAssignableFrom(entityClass);
	}

	@Override
	public void beforeSave(IBaseEntity entity) {
		beforeCreate(entity);
	}

}
