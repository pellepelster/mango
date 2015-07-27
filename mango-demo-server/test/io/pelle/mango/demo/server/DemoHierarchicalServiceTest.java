package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.hierarchy.DictionaryHierarchicalNodeVO;
import io.pelle.mango.client.hierarchy.IHierarchicalService;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.demo.client.TestClientHierarchicalConfiguration;
import io.pelle.mango.demo.client.showcase.CompanyVO;
import io.pelle.mango.demo.client.showcase.DepartmentVO;
import io.pelle.mango.demo.client.showcase.EmployeeVO;
import io.pelle.mango.demo.client.showcase.ManagerVO;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoHierarchicalServiceTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private IBaseVODAO baseVODAO;

	@Autowired
	private IHierarchicalService hierarchicalService;

	@Before
	public void init() {

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
	public void testRemoveParentNotAllowed() {

		baseEntityService.deleteAll(CompanyVO.class.getName());
		baseEntityService.deleteAll(ManagerVO.class.getName());

		CompanyVO company = new CompanyVO();
		company.setName("xxx");
		company = baseEntityService.create(company);

		ManagerVO manager = new ManagerVO();
		manager.setName("aaa");
		manager.setParent(company);

		manager = baseEntityService.create(manager);

		assertNotNull(manager.getParent());

		manager.setParent(null);

		Result<ManagerVO> managerSaveResult = baseEntityService.validateAndSave(manager);

		assertEquals(1, managerSaveResult.getValidationMessages().size());
		assertEquals("io.pelle.mango.demo.client.showcase.ManagerVO needs a parent", managerSaveResult.getValidationMessages().get(0).getMessage());
		assertEquals("Manager needs a parent", managerSaveResult.getValidationMessages().get(0).getHumanMessage());
	}

	@Test
	public void testInvalidParent() {

		baseEntityService.deleteAll(CompanyVO.class.getName());
		baseEntityService.deleteAll(EmployeeVO.class.getName());

		CompanyVO company = new CompanyVO();
		company.setName("xxx");
		company = baseEntityService.create(company);

		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setName("aaa");
		employeeVO.setParent(company);

		Result<EmployeeVO> employeeSaveResult = baseEntityService.validateAndSave(employeeVO);

		assertEquals(1, employeeSaveResult.getValidationMessages().size());
		assertEquals("io.pelle.mango.demo.client.showcase.CompanyVO not allowed as parent for io.pelle.mango.demo.client.showcase.EmployeeVO", employeeSaveResult.getValidationMessages().get(0).getMessage());
		assertEquals("Company not allowed as parent for Employee", employeeSaveResult.getValidationMessages().get(0).getHumanMessage());
	}

	@Test
	public void testGetChildren() {
		List<DictionaryHierarchicalNodeVO> rootNodes = this.hierarchicalService.getRootNodes(TestClientHierarchicalConfiguration.ID);

		DictionaryHierarchicalNodeVO rootNode1 = rootNodes.get(0);
		assertEquals(true, rootNode1.getHasChildren());

		List<DictionaryHierarchicalNodeVO> childNodes = this.hierarchicalService.getChildNodes(TestClientHierarchicalConfiguration.ID, rootNode1.getVoId(), rootNode1.getVoClassName());

		assertEquals(1, childNodes.size());
		assertEquals("aaa", childNodes.get(0).getLabel());
		assertEquals(false, childNodes.get(0).getHasChildren());
		assertEquals(MangoDemoDictionaryModel.MANAGER.getName(), childNodes.get(0).getDictionaryName());
	}

	@Test
	public void testGetConfigurationById() {
		HierarchicalConfigurationVO hierarchicalConfiguration = this.hierarchicalService.getConfigurationById(TestClientHierarchicalConfiguration.ID);
		assertEquals(TestClientHierarchicalConfiguration.ID, hierarchicalConfiguration.getId());
	}

	@Test(expected = RuntimeException.class)
	public void testGetHierarchicalConfigurationByInvalidId() {
		this.hierarchicalService.getConfigurationById("xxx");
	}

	@Test
	public void testGetConfigurationByDictionaryId() {
		HierarchicalConfigurationVO hierarchicalConfiguration = this.hierarchicalService.getConfigurationByDictionaryId(MangoDemoDictionaryModel.COMPANY.getName());
		assertEquals(TestClientHierarchicalConfiguration.ID, hierarchicalConfiguration.getId());
	}

	@Test(expected = RuntimeException.class)
	public void testGetConfigurationByInvalidDictionaryId() {
		this.hierarchicalService.getConfigurationByDictionaryId("xxx");
	}

	@Test
	public void testGetRootNodes() {
		List<DictionaryHierarchicalNodeVO> rootNodes = this.hierarchicalService.getRootNodes(TestClientHierarchicalConfiguration.ID);

		assertEquals(2, rootNodes.size());

		assertEquals("xxx", rootNodes.get(0).getLabel());
		assertEquals("yyy", rootNodes.get(1).getLabel());

		assertEquals(MangoDemoDictionaryModel.COMPANY.getName(), rootNodes.get(0).getDictionaryName());
	}

	@Test
	public void testCreate() {

		baseEntityService.deleteAll(CompanyVO.class.getName());

		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setName("kkk");
		companyVO1 = baseEntityService.create(companyVO1);

		ManagerVO managerVO = new ManagerVO();
		managerVO.setName("ppp");
		managerVO.setParent(companyVO1);
		Result<ManagerVO> savedManagerVO = baseEntityService.validateAndCreate(managerVO);

		assertEquals(managerVO.getParent().getOid(), savedManagerVO.getValue().getParent().getOid());
	}

	@Test
	public void testGetNewVO() {

		baseEntityService.deleteAll(CompanyVO.class.getName());

		CompanyVO companyVO1 = new CompanyVO();
		companyVO1.setName("kkk");
		companyVO1 = baseEntityService.create(companyVO1);

		Map<String, String> properties = new HashedMap<String, String>();
		properties.put(IHierarchicalVO.PARENT_CLASS_FIELD_NAME, companyVO1.getClass().getName());
		properties.put(IHierarchicalVO.PARENT_ID_FIELD_NAME, Long.toString(companyVO1.getOid()));

		ManagerVO managerVO = baseEntityService.getNewVO(ManagerVO.class.getName(), properties);

		assertEquals(companyVO1, managerVO.getParent());
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

		assertEquals(managerVO.getParent().getOid(), savedManagerVO.getValue().getParent().getOid());
	}

	@Test
	public void testVODecorator() {

		List<DictionaryHierarchicalNodeVO> rootNodes = this.hierarchicalService.getRootNodes(TestClientHierarchicalConfiguration.ID);
		DictionaryHierarchicalNodeVO rootNode1 = rootNodes.get(0);

		List<DictionaryHierarchicalNodeVO> childNodes = this.hierarchicalService.getChildNodes(TestClientHierarchicalConfiguration.ID, rootNode1.getVoId(), rootNode1.getVoClassName());

		assertEquals(1, childNodes.size());

		List<ManagerVO> managers = this.baseVODAO.filter(SelectQuery.selectFrom(ManagerVO.class).where(ManagerVO.ID.eq(childNodes.get(0).getVoId())));
		assertEquals(1, managers.size());
		assertEquals("xxx", managers.get(0).getParent().get("name"));
		assertEquals(false, managers.get(0).getHasChildren());

		List<CompanyVO> companies = this.baseVODAO.filter(SelectQuery.selectFrom(CompanyVO.class));
		assertEquals(2, companies.size());
		assertEquals(true, companies.get(0).getHasChildren());
		assertEquals(true, companies.get(1).getHasChildren());

	}

	@Test
	public void testGetParentNodes() {

		baseEntityService.deleteAll(CompanyVO.class.getName());
		baseEntityService.deleteAll(ManagerVO.class.getName());

		CompanyVO company = new CompanyVO();
		company.setName("xxx");
		company = baseEntityService.create(company);

		DepartmentVO department = new DepartmentVO();
		department.setParent(company);
		department.setName("yyy");
		department = baseEntityService.create(department);

		ManagerVO manager = new ManagerVO();
		manager.setName("zzz");
		manager.setParent(department);
		manager = baseEntityService.create(manager);

		List<DictionaryHierarchicalNodeVO> parentNodes = hierarchicalService.getParentNodes(TestClientHierarchicalConfiguration.ID, manager);

		assertEquals(2, parentNodes.size());

		assertEquals(department.getClass().getName(), parentNodes.get(0).getVoClassName());
		assertEquals((Long) department.getId(), parentNodes.get(0).getVoId());

		assertEquals(company.getClass().getName(), parentNodes.get(1).getVoClassName());
		assertEquals((Long) company.getId(), parentNodes.get(1).getVoId());

	}

}