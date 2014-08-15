package io.pelle.mango.dsl.query.datatype;

import io.pelle.mango.dsl.mango.MangoPackage;
import io.pelle.mango.dsl.mango.StringDataType;

import com.google.common.base.Optional;

public class StringDatatypeQuery extends BaseDatatypeQuery<StringDataType> {

	public StringDatatypeQuery(StringDataType stringDataType) {
		super(stringDataType);
	}

	public static StringDatatypeQuery createQuery(StringDataType stringDataType) {
		return new StringDatatypeQuery(stringDataType);
	}

	public int getMaxLength() {

		Optional<Object> maxLength = getStructuralFeature(MangoPackage.Literals.STRING_DATA_TYPE__MAX_LENGTH);

		if (maxLength.isPresent()) {
			return Integer.parseInt(maxLength.get().toString());
		} else {
			throw new RuntimeException("no maxlength found for datatype '" + getDatatype() + "'");
		}

	}

	public boolean hasMaxLength() {
		return getStructuralFeature(MangoPackage.Literals.STRING_DATA_TYPE__MAX_LENGTH).isPresent();
	}

}
