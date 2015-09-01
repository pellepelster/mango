package io.pelle.mango.db.voquery.predicates;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;

public class AttributeEqualsPredicate implements Predicate<Object> {

	private String attributeName;
	
	private Object value;

	public AttributeEqualsPredicate(Object value, String attributeName) {
		super();
		this.value = value;
		this.attributeName = attributeName;
	}

	@Override
	public boolean apply(Object object) {
		try {
			return Objects.equal(value, PropertyUtils.getProperty(object, attributeName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
