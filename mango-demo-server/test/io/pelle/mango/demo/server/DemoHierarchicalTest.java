package io.pelle.mango.demo.server;

import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.hierarchy.DictionaryHierarchicalNodeVO;
import io.pelle.mango.client.hierarchy.IHierachicalService;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.demo.client.MangoDemoClientConfiguration;
import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.demo.client.TestClientHierarchicalConfiguration;
import io.pelle.mango.demo.client.showcase.CompanyVO;
import io.pelle.mango.demo.client.showcase.ManagerVO;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoHierarchicalTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private IBaseVODAO baseVODAO;

	@Autowired
	private IHierachicalService hierachicalService;

	@Before
	public void init() {

		MangoDemoClientConfiguration.registerAll();

		baseEntityService.deleteAll(CompanyVO.class.getName());

		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setName("xxx");
		companyVO1 = baseEntityService.create(companyVO1);

		ManagerVO managerVO1 = new ManagerVO();
		managerVO1.setName("aaa");
		managerVO1.setParent(companyVO1);
		baseEntityService.create(managerVO1);

		CompanyVO companyVO2 = new CompanyVO();
		companyVO2.setName("yyy");
		companyVO2 = baseEntityService.create(companyVO2);

		ManagerVO managerVO2 = new ManagerVO();
		managerVO2.setName("bbb");
		managerVO2.setParent(companyVO2);

		baseEntityService.create(managerVO2);
	}

	@Test
	public void testRemoveParent() {

		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setName("xxx");
		companyVO1 = baseEntityService.create(companyVO1);

		ManagerVO managerVO1 = new ManagerVO();
		managerVO1.setName("aaa");
		managerVO1.setParent(companyVO1);

		managerVO1 = baseEntityService.create(managerVO1);

		Assert.assertNotNull(managerVO1.getParent());

		managerVO1.setParent(null);
		managerVO1 = baseEntityService.create(managerVO1);

		Assert.assertNull(managerVO1.getParent());
		Assert.assertNull(managerVO1.getParentClassName());
		Assert.assertNull(managerVO1.getParentId());
	}

	@Test
	public void testGetChildren() {
		List<DictionaryHierarchicalNodeVO> rootNodes = this.hierachicalService.getRootNodes(TestClientHierarchicalConfiguration.ID);

		DictionaryHierarchicalNodeVO rootNode1 = rootNodes.get(0);
		Assert.assertEquals(true, rootNode1.getHasChildren());

		List<DictionaryHierarchicalNodeVO> childNodes = this.hierachicalService.getChildNodes(TestClientHierarchicalConfiguration.ID, rootNode1.getVoId(), rootNode1.getVoClassName());

		Assert.assertEquals(1, childNodes.size());
		Assert.assertEquals("aaa", childNodes.get(0).getLabel());
		Assert.assertEquals(false, childNodes.get(0).getHasChildren());
		Assert.assertEquals(MangoDemoDictionaryModel.MANAGER.getName(), childNodes.get(0).getDictionaryName());
	}

	@Test
	public void testGetHierarchicalConfiguration() {
		HierarchicalConfigurationVO hierarchicalConfiguration = this.hierachicalService.getConfigurationById(TestClientHierarchicalConfiguration.ID);
		Assert.assertEquals(TestClientHierarchicalConfiguration.ID, hierarchicalConfiguration.getId());
	}

	@Test
	public void testGetRootNodes() {
		List<DictionaryHierarchicalNodeVO> rootNodes = this.hierachicalService.getRootNodes(TestClientHierarchicalConfiguration.ID);

		Assert.assertEquals(2, rootNodes.size());

		Assert.assertEquals("xxx", rootNodes.get(0).getLabel());
		Assert.assertEquals("yyy", rootNodes.get(1).getLabel());

		Assert.assertEquals(MangoDemoDictionaryModel.COMPANY.getName(), rootNodes.get(0).getDictionaryName());
	}

	@Test
	public void testCreate() {
		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setName("kkk");
		companyVO1 = baseEntityService.create(companyVO1);

		ManagerVO managerVO = new ManagerVO();
		managerVO.setName("ppp");
		managerVO.setParent(companyVO1);
		Result<ManagerVO> savedManagerVO = baseEntityService.validateAndCreate(managerVO);

		Assert.assertEquals(managerVO.getParent().getOid(), savedManagerVO.getValue().getParent().getOid());
	}

	@Test
	public void testSave() {

		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setName("kkk");
		companyVO1 = baseEntityService.create(companyVO1);

		ManagerVO managerVO = new ManagerVO();
		managerVO.setName("ppp");
		managerVO.setParent(companyVO1);

		managerVO = baseEntityService.create(managerVO);
		managerVO.setName("nnn");

		Result<ManagerVO> savedManagerVO = baseEntityService.validateAndSave(managerVO);

		Assert.assertEquals(managerVO.getParent().getOid(), savedManagerVO.getValue().getParent().getOid());
	}

	@Test
	public void testVODecorator() {

		List<DictionaryHierarchicalNodeVO> rootNodes = this.hierachicalService.getRootNodes(TestClientHierarchicalConfiguration.ID);
		DictionaryHierarchicalNodeVO rootNode1 = rootNodes.get(0);

		List<DictionaryHierarchicalNodeVO> childNodes = this.hierachicalService.getChildNodes(TestClientHierarchicalConfiguration.ID, rootNode1.getVoId(), rootNode1.getVoClassName());

		Assert.assertEquals(1, childNodes.size());

		List<ManagerVO> managers = this.baseVODAO.filter(SelectQuery.selectFrom(ManagerVO.class).where(ManagerVO.ID.eq(childNodes.get(0).getVoId())));
		Assert.assertEquals(1, managers.size());
		Assert.assertEquals("xxx", managers.get(0).getParent().get("name"));
		Assert.assertEquals(false, managers.get(0).getHasChildren());

		List<CompanyVO> companies = this.baseVODAO.filter(SelectQuery.selectFrom(CompanyVO.class));
		Assert.assertEquals(2, companies.size());
		Assert.assertEquals(true, companies.get(0).getHasChildren());
		Assert.assertEquals(true, companies.get(1).getHasChildren());

	}
}