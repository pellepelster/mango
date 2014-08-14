package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.MangoPackage;
import io.pelle.mango.dsl.mango.StringDataType;

import com.google.common.base.Optional;

public class StringDatatypeQuery extends DatatypeQuery<StringDataType> {

	public StringDatatypeQuery(StringDataType stringDataType) {
		super(stringDataType);
	}

	public static StringDatatypeQuery createQuery(StringDataType stringDataType) {
		return new StringDatatypeQuery(stringDataType);
	}

	public int getMaxLength() {
		Optional<Object> maxLength = getStructuralFeature(MangoPackage.Literals.STRING_DATA_TYPE__MAX_LENGTH);

		if (maxLength.isPresent()) {
			return (int) maxLength.get();
		} else {
			throw new RuntimeException("no maxlength found for datatype '" + getDatatype() + "'");
		}

	}

	public boolean hasMaxLength() {
		return getStructuralFeature(MangoPackage.Literals.STRING_DATA_TYPE__MAX_LENGTH).isPresent();
	}

}
