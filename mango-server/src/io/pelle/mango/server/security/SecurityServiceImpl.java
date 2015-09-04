package io.pelle.mango.server.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.security.ISecurityService;
import io.pelle.mango.client.security.MangoPermissionVO;

public class SecurityServiceImpl implements ISecurityService {

	@Autowired(required = false)
	private List<IPermissionProvider> permissionProviders = new ArrayList<>();

	@Override
	public List<MangoPermissionVO> getAvailablePermissions() {

		List<MangoPermissionVO> result = new ArrayList<>();

		for (IPermissionProvider permissionProvider : permissionProviders) {
			result.addAll(permissionProvider.getPermissions());
		}

		return result;
	}

}
