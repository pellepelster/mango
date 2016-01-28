package io.pelle.mango.demo.server.client;

import java.util.List;

import io.pelle.mango.client.PermissionsImpl;
import io.pelle.mango.client.security.MangoPermissionVO;
import io.pelle.mango.client.web.test.AsyncCallbackFuture;

public class PermissionsCustomCompositeTest {

	private PermissionsImpl permissions;

	public PermissionsCustomCompositeTest(PermissionsImpl permissions) {
		super();
		this.permissions = permissions;
	}
	
	
	public List<MangoPermissionVO> getAvailablePermissions() {
		
		AsyncCallbackFuture<List<MangoPermissionVO>> future = AsyncCallbackFuture.create();
		
		permissions.getAvailablePermissions(future.getCallback());
		
		return future.get();
	}
	
}
