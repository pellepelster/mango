package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.security.ISecurityService;
import io.pelle.mango.client.security.MangoGroupVO;
import io.pelle.mango.client.security.MangoOperationVO;
import io.pelle.mango.client.security.MangoPermissionVO;
import io.pelle.mango.db.voquery.Predicates;
import io.pelle.mango.demo.server.util.BaseDemoTest;

public class SecurityServiceTest extends BaseDemoTest {

	@Autowired
	private ISecurityService securityService;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testGetAvailablePermissions() {

		List<MangoPermissionVO> result = securityService.getAvailablePermissions();

		assertEquals(24, result.size());

		Optional<MangoPermissionVO> countryPermission = Iterables.tryFind(result, Predicates.attributeEquals(MangoPermissionVO.PERMISSIONID, "io.pelle.mango.demo.client.showcase.CountryVO"));
		assertTrue(countryPermission.isPresent());

		assertEquals(4, countryPermission.get().getOperations().size());

		assertTrue(Iterables.any(countryPermission.get().getOperations(), Predicates.attributeEquals(MangoOperationVO.OPERATIONID, IVOEntity.OPERATION_CREATE)));
		assertTrue(Iterables.any(countryPermission.get().getOperations(), Predicates.attributeEquals(MangoOperationVO.OPERATIONID, IVOEntity.OPERATION_UPDATE)));
		assertTrue(Iterables.any(countryPermission.get().getOperations(), Predicates.attributeEquals(MangoOperationVO.OPERATIONID, IVOEntity.OPERATION_DELETE)));
		assertTrue(Iterables.any(countryPermission.get().getOperations(), Predicates.attributeEquals(MangoOperationVO.OPERATIONID, IVOEntity.OPERATION_READ)));

	}

	@Test
	public void testRemoveDuplicatePermissions() {

		MangoPermissionVO permission1 = new MangoPermissionVO();
		permission1.setPermissionId("xxx");

		MangoOperationVO operation1 = new MangoOperationVO();
		operation1.setOperationId("uuu");
		permission1.getOperations().add(operation1);

		MangoPermissionVO permission2 = new MangoPermissionVO();
		permission2.setPermissionId("xxx");

		MangoOperationVO operation2 = new MangoOperationVO();
		operation2.setOperationId("ooo");
		permission2.getOperations().add(operation2);

		MangoGroupVO mangoGroupVO = new MangoGroupVO();
		mangoGroupVO.getPermissions().add(permission1);
		mangoGroupVO.getPermissions().add(permission2);

		mangoGroupVO = baseEntityService.create(mangoGroupVO);

		assertEquals(1, mangoGroupVO.getPermissions().size());
		assertEquals(2, mangoGroupVO.getPermissions().get(0).getOperations().size());

	}

	@Test
	public void testRemoveDuplicateOperations() {

		MangoPermissionVO permission1 = new MangoPermissionVO();
		permission1.setPermissionId("xxx");

		MangoOperationVO operation1 = new MangoOperationVO();
		operation1.setOperationId("uuu");
		permission1.getOperations().add(operation1);

		MangoOperationVO operation2 = new MangoOperationVO();
		operation2.setOperationId("uuu");
		permission1.getOperations().add(operation2);

		MangoGroupVO mangoGroupVO = new MangoGroupVO();
		mangoGroupVO.getPermissions().add(permission1);

		mangoGroupVO = baseEntityService.create(mangoGroupVO);

		assertEquals(1, mangoGroupVO.getPermissions().size());
		assertEquals(1, mangoGroupVO.getPermissions().get(0).getOperations().size());

	}

}