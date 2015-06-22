package io.pelle.mango.gradle.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.gradle.HibernateGenerator;
import io.pelle.mango.gradle.HibernateGenerator.LogCallback;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class HibernateGeneratorTest {

	private static final HibernateGenerator.LogCallback logCallback = new LogCallback() {

		@Override
		public void log(String message) {
			System.out.println(message);
		}
	};

	@Test
	public void testGenerateDefault() throws IOException {

		File schemaDir = new File("schemas");

		if (schemaDir.exists()) {
			FileUtils.deleteDirectory(schemaDir);
		}

		assertFalse(schemaDir.exists());

		HibernateGenerator hibernateGenerator = new HibernateGenerator(logCallback);
		hibernateGenerator.generate();

		assertTrue(String.format("checking for schema dir '%s'", schemaDir.getPath()), schemaDir.exists());
		assertTrue(schemaDir.isDirectory());

		File mysqlDialect = new File(schemaDir, "MySQLDialect");
		assertTrue(String.format("checking for mysql dialect dir '%s'", mysqlDialect.getPath()), mysqlDialect.exists());

		File mysqlCreate = new File(mysqlDialect, "create.sql");
		assertTrue(String.format("checking for mysql create script '%s'", mysqlCreate.getPath()), mysqlCreate.exists());

		String mysqlCreateContents = FileUtils.readFileToString(mysqlCreate);
		assertTrue(mysqlCreateContents.contains("create table JpaEntity1")) ;
		
		File mysqlDrop = new File(mysqlDialect, "drop.sql");
		assertTrue(String.format("checking for mysql drop script '%s'", mysqlCreate.getPath()), mysqlDrop.exists());

		String mysqlDropContents = FileUtils.readFileToString(mysqlDrop);
		assertTrue(mysqlDropContents.contains("drop table if exists JpaEntity1")) ;

		File mysqlDropAndCreate = new File(mysqlDialect, "drop_and_create.sql");
		assertTrue(String.format("checking for mysql drop and create script '%s'", mysqlDropAndCreate.getPath()), mysqlDropAndCreate.exists());

		String mysqlDropAndCreateContents = FileUtils.readFileToString(mysqlDropAndCreate);
		assertTrue(mysqlDropAndCreateContents.contains("drop table if exists JpaEntity1")) ;
		assertTrue(mysqlDropAndCreateContents.contains("create table JpaEntity1")) ;

	}

}
