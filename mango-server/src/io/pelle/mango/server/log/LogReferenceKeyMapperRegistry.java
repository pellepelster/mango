package io.pelle.mango.server.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class LogReferenceKeyMapperRegistry {

	private static final Logger LOG = Logger.getLogger(LogReferenceKeyMapperRegistry.class);

	@Autowired(required = false)
	private List<ILogReferenceKeyMapper> referenceMappers = new ArrayList<ILogReferenceKeyMapper>();

	public String getLogReferenceKey(Object reference) {

		String result = null;

		if (reference == null || reference instanceof String) {
			result = (String) reference;
		} else {
			Optional<ILogReferenceKeyMapper> referenceKeyMapper = getReferenceMapper(reference);

			if (referenceKeyMapper.isPresent()) {
				result = referenceKeyMapper.get().getReferenceKey(reference);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("converted object '%s' to log reference '%s'", reference, result));
		}

		return result;
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
