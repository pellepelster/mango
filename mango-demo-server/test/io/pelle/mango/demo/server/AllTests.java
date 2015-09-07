package io.pelle.mango.demo.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import io.pelle.mango.demo.server.api.ImportExportWebserviceTest;
import io.pelle.mango.demo.server.api.WebhookApiTest;
import io.pelle.mango.demo.server.api.WebhookServiceTest;
import io.pelle.mango.demo.server.api.XmlImportExportTest;
import io.pelle.mango.demo.server.api.XmlModelGeneratorTest;
import io.pelle.mango.demo.server.entity.DemoBaseEntityDAOTest;
import io.pelle.mango.demo.server.entity.DemoBaseEntityServiceTest;
import io.pelle.mango.demo.server.entity.DemoCountryEntityDAOTest;
import io.pelle.mango.demo.server.entity.DemoEntityMetaInformationTest;
import io.pelle.mango.demo.server.entity.DemoFileStorageServiceTest;
import io.pelle.mango.demo.server.entity.DemoVOMetadataServiceTest;
import io.pelle.mango.demo.server.entity.Entity4EntityDAOTest;
import io.pelle.mango.demo.server.entity.EntityApiTest;
import io.pelle.mango.demo.server.entity.EntityUtilsTest;
import io.pelle.mango.demo.server.entity.EntityVOMapperTest;
import io.pelle.mango.demo.server.entity.JPQLTest;
import io.pelle.mango.demo.server.file.FileControllerTest;
import io.pelle.mango.demo.server.file.FileStorageTest;
import io.pelle.mango.demo.server.model.ModelProviderTest;
import io.pelle.mango.demo.server.vo.DemoBaseVODAOTest;
import io.pelle.mango.demo.server.vo.DemoCurrencyVODAOTest;
import io.pelle.mango.demo.server.vo.VOMetadataTest;

@RunWith(Suite.class)
@SuiteClasses({ DemoBaseVODAOTest.class, DemoCurrencyVODAOTest.class, VOMetadataTest.class, ModelProviderTest.class, FileControllerTest.class, FileStorageTest.class, DemoBaseEntityDAOTest.class, DemoBaseEntityServiceTest.class,
		DemoCountryEntityDAOTest.class, DemoEntityMetaInformationTest.class, DemoFileStorageServiceTest.class, DemoVOMetadataServiceTest.class, Entity4EntityDAOTest.class, EntityApiTest.class, EntityUtilsTest.class,
		EntityVOMapperTest.class, JPQLTest.class, ImportExportWebserviceTest.class, WebhookApiTest.class, WebhookServiceTest.class, XmlImportExportTest.class, XmlModelGeneratorTest.class, DemoClientTest.class, DemoDataGeneratorTest.class,
		DemoHierarchicalServiceTest.class, DemoLogServiceTest.class, DocumentationCode.class, PropertyServiceTest.class, SearchIndexBuilderTest.class, SearchServiceTest.class, SecurityServiceTest.class, StateServiceTest.class })
public class AllTests {

}
