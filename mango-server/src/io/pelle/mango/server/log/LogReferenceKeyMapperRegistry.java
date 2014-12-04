package io.pelle.mango.server.log;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class LogReferenceKeyMapperRegistry {

	@Autowired(required = false)
	private List<ILogReferenceKeyMapper> referenceMappers = new ArrayList<ILogReferenceKeyMapper>();

	public String getLogReferenceKey(Object reference) {

		if (reference == null || reference instanceof String) {
			return (String) reference;
		}

		Optional<ILogReferenceKeyMapper> referenceKeyMapper = getReferenceMapper(reference);

		if (referenceKeyMapper.isPresent()) {
			return referenceKeyMapper.get().getReferenceKey(reference);
		} else {
			return null;
		}

	}

	private Optional<ILogReferenceKeyMapper> getReferenceMapper(final Object reference) {
		return Iterables.tryFind(referenceMappers, new Predicate<ILogReferenceKeyMapper>() {
			@Override
			public boolean apply(ILogReferenceKeyMapper input) {
				return input.supports(reference.getClass());
			}
		});
	}

}
