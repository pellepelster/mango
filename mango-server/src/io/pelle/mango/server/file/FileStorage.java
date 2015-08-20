package io.pelle.mango.server.file;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.server.File;

public class FileStorage {

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	public File getFile(String fileUUID) {

		SelectQuery<File> query = SelectQuery.selectFrom(File.class).where(File.FILEUUID.eq(fileUUID));

		List<File> files = baseEntityDAO.filter(query);

		if (files.isEmpty()) {
			throw new FileNotFoundException(String.format("file with uuid '%s' not found", fileUUID));
		} else {
			return files.get(0);
		}
	}

	public List<File> getFiles(List<String> fileUUIDs) {

		List<File> result = new ArrayList<>();

		for (String fileUUID : fileUUIDs) {
			result.add(getFile(fileUUID));
		}

		return result;
	}

}