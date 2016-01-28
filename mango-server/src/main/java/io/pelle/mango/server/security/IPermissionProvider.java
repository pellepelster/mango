package io.pelle.mango.server.security;

import java.util.Collection;

import io.pelle.mango.client.security.MangoPermissionVO;

public interface IPermissionProvider {

	Collection<MangoPermissionVO> getPermissions();

}
