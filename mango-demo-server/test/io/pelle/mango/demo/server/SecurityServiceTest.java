package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.security.ISecurityService;
import io.pelle.mango.client.security.MangoOperationVO;
import io.pelle.mango.client.security.MangoPermissionVO;
import io.pelle.mango.db.voquery.Predicates;

public class SecurityServiceTest extends BaseDemoTest {

	@Autowired
	private ISecurityService securityService;

	@Test
	public void testGetAvailablePermissions() {

		List<MangoPermissionVO> result = securityService.getAvailablePermissions();
		
		assertEquals(24, result.size());
		
		Optional<MangoPermissionVO> countryPermission = Iterables.tryFind(result, Predicates.attributeEquals(MangoPermissionVO.PERMISSIONID, "io.pelle.mango.demo.client.showcase.CountryVO"));
		assertTrue(countryPermission.isPresent());
		
		assertEquals(4, countryPermission.get().getOperations().size());

		assertTrue(Iterables.any(countryPermission.get().getOperations(),Predicates.attributeEquals(MangoOperationVO.OPERATIONID, IVOEntity.OPERATION_CREATE)));
		assertTrue(Iterables.any(countryPermission.get().getOperations(),Predicates.attributeEquals(MangoOperationVO.OPERATIONID, IVOEntity.OPERATION_UPDATE)));
		assertTrue(Iterables.any(countryPermission.get().getOperations(),Predicates.attributeEquals(MangoOperationVO.OPERATIONID, IVOEntity.OPERATION_DELETE)));
		assertTrue(Iterables.any(countryPermission.get().getOperations(),Predicates.attributeEquals(MangoOperationVO.OPERATIONID, IVOEntity.OPERATION_READ)));

	}

}