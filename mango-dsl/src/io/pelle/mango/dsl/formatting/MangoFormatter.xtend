package io.pelle.mango.dsl.formatting

import com.google.inject.Inject
import io.pelle.mango.dsl.services.MangoGrammarAccess
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter
import org.eclipse.xtext.formatting.impl.FormattingConfig

class MangoFormatter extends AbstractDeclarativeFormatter {

	@Inject
	extension MangoGrammarAccess m

	private int MIN_WRAPS = 1;

	private int DEFAULT_WRAPS = 2;

	private int MAX_WRAPS = 3;

	def void defaultBlockFormat(FormattingConfig c, Keyword leftCurlyBracketKeyword, Keyword rightCurlyBracketKeyword) {
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(leftCurlyBracketKeyword)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(rightCurlyBracketKeyword)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(rightCurlyBracketKeyword)
		c.setIndentationIncrement.after(leftCurlyBracketKeyword)
		c.setIndentationDecrement.before(rightCurlyBracketKeyword)
	}

	override protected void configureFormatting(FormattingConfig c) {

		c.setLinewrap(0, 1, 2).before(SL_COMMENTRule)
		c.setLinewrap(0, 1, 2).before(ML_COMMENTRule)
		c.setLinewrap(0, 1, 1).after(ML_COMMENTRule)

		// one import per line
		c.setLinewrap(0, 1, 1).after(m.XImportDeclarationRule);

		// some space between imports and model root
		c.setLinewrap(1, 1, 1).before(m.modelRootRule);

		// model
		defaultBlockFormat(c, m.modelAccess.leftCurlyBracketKeyword_2, m.modelAccess.rightCurlyBracketKeyword_4)

		// package
		defaultBlockFormat(c,m.packageDeclarationAccess.leftCurlyBracketKeyword_2, m.packageDeclarationAccess.rightCurlyBracketKeyword_4)

		// datatype
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.datatypeRule);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.datatypeRule);

		// entity
		defaultBlockFormat(c, m.entityAccess.leftCurlyBracketKeyword_4, m.entityAccess.rightCurlyBracketKeyword_7)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.entityAttributeRule);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.entityAttributeRule);

		// entity options
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.entityOptionsContainerAccess.entityoptionsKeyword_1);
		defaultBlockFormat(c, m.entityOptionsContainerAccess.getLeftCurlyBracketKeyword_2, entityOptionsContainerAccess.rightCurlyBracketKeyword_4);

		// natural key
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.entityOptionsAccess.entityNaturalKeyFieldsParserRuleCall_3);
		defaultBlockFormat(c, m.entityNaturalKeyFieldsAccess.leftCurlyBracketKeyword_2, entityNaturalKeyFieldsAccess.rightCurlyBracketKeyword_5);

		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.entityOptionsAccess.entityDisableIdFieldParserRuleCall_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.entityOptionsAccess.entityLabelFieldParserRuleCall_1);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.entityOptionsAccess.entityPluralLabelFieldParserRuleCall_2);

		// service
		defaultBlockFormat(c,m.serviceAccess.leftCurlyBracketKeyword_3, m.serviceAccess.rightCurlyBracketKeyword_6)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.serviceMethodRule);

		// value object
		defaultBlockFormat(c, m.valueObjectAccess.leftCurlyBracketKeyword_4, m.valueObjectAccess.rightCurlyBracketKeyword_6)

		// base control
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.baseDictionaryControlAccess.entityattributeKeyword_0_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.baseDictionaryControlAccess.typeKeyword_1_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.baseDictionaryControlAccess.readonlyKeyword_5_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.labelsAccess.columnLabelKeyword_3_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.labelsAccess.editorLabelKeyword_4_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.labelsAccess.filterLabelKeyword_2_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.labelsAccess.editorLabelKeyword_4_0);

		// base datatype
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.baseDataTypeLabelAccess.labelKeyword_1);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.baseDataTypeWidthAccess.widthKeyword_1);

		// text control
		defaultBlockFormat(c, m.dictionaryTextControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryTextControlAccess.rightCurlyBracketKeyword_4_2)
			
		defaultBlockFormat(c, m.dictionaryIntegerControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryIntegerControlAccess.rightCurlyBracketKeyword_4_3)
		defaultBlockFormat(c, m.dictionaryBigDecimalControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryBigDecimalControlAccess.rightCurlyBracketKeyword_4_2)
		defaultBlockFormat(c, m.dictionaryDateControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryDateControlAccess.rightCurlyBracketKeyword_4_2)

		// reference control
		defaultBlockFormat(c, m.dictionaryReferenceControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryReferenceControlAccess.rightCurlyBracketKeyword_4_5)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryReferenceControlAccess.dictionaryKeyword_4_2_0);

		defaultBlockFormat(c, m.dictionaryBooleanControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryBooleanControlAccess.rightCurlyBracketKeyword_4_2)

		// enumeration control
		defaultBlockFormat(c, m.dictionaryEnumerationControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryEnumerationControlAccess.rightCurlyBracketKeyword_4_2)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryEnumerationControlAccess.enumerationcontrolKeyword_1);


		defaultBlockFormat(c, m.stringDataTypeAccess.leftCurlyBracketKeyword_3, m.stringDataTypeAccess.rightCurlyBracketKeyword_7)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.stringDataTypeAccess.minLengthKeyword_6_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.stringDataTypeAccess.maxLengthKeyword_5_0);
		
		defaultBlockFormat(c, m.integerDataTypeAccess.leftCurlyBracketKeyword_3, m.integerDataTypeAccess.rightCurlyBracketKeyword_5)
		defaultBlockFormat(c, m.decimalDataTypeAccess.leftCurlyBracketKeyword_3, m.decimalDataTypeAccess.rightCurlyBracketKeyword_5)
		defaultBlockFormat(c, m.dateDataTypeAccess.leftCurlyBracketKeyword_3, m.dateDataTypeAccess.rightCurlyBracketKeyword_5)
		defaultBlockFormat(c, m.entityDataTypeAccess.leftCurlyBracketKeyword_3, m.entityDataTypeAccess.rightCurlyBracketKeyword_7)
		defaultBlockFormat(c, m.booleanDataTypeAccess.leftCurlyBracketKeyword_3, m.booleanDataTypeAccess.rightCurlyBracketKeyword_5)
		defaultBlockFormat(c, m.enumerationDataTypeAccess.leftCurlyBracketKeyword_3, m.enumerationDataTypeAccess.rightCurlyBracketKeyword_7)

		// dictionary
		defaultBlockFormat(c, m.dictionaryAccess.leftCurlyBracketKeyword_2, m.dictionaryAccess.rightCurlyBracketKeyword_11)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryAccess.entityKeyword_3);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryAccess.dictionarycontrolsKeyword_7_0);
		c.setIndentationIncrement.after(m.dictionaryAccess.leftCurlyBracketKeyword_7_1);
		c.setIndentationDecrement.before(m.dictionaryAccess.rightCurlyBracketKeyword_7_3);

		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryAccess.entityKeyword_3);

		// label controls		
		defaultBlockFormat(c, m.dictionaryAccess.getLabelcontrolsKeyword_8_0, m.dictionaryAccess.getRightCurlyBracketKeyword_8_3)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryAccess.getLabelcontrolsKeyword_8_0);

		// dictionary control group
		defaultBlockFormat(c, m.dictionaryControlGroupAccess.leftCurlyBracketKeyword_4_0, m.dictionaryControlGroupAccess.rightCurlyBracketKeyword_4_4)
		defaultBlockFormat(c, m.dictionaryControlGroupOptionsContainerAccess.leftCurlyBracketKeyword_2, m.dictionaryControlGroupOptionsContainerAccess.rightCurlyBracketKeyword_4)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.dictionaryControlGroupOptionsRule);

		// dictionary editor
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryEditorAccess.dictionaryeditorKeyword_0);
		defaultBlockFormat(c, m.dictionaryEditorAccess.leftCurlyBracketKeyword_2, m.dictionaryEditorAccess.rightCurlyBracketKeyword_7)

		// dictionary filter
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryFilterAccess.dictionaryfilterKeyword_0);
		defaultBlockFormat(c, m.dictionaryFilterAccess.leftCurlyBracketKeyword_2, m.dictionaryFilterAccess.rightCurlyBracketKeyword_6)

		// dictionary result
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryResultAccess.dictionaryresultKeyword_0);
		defaultBlockFormat(c, m.dictionaryResultAccess.leftCurlyBracketKeyword_2, m.dictionaryResultAccess.rightCurlyBracketKeyword_4)

		// dictionary search
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionarySearchAccess.dictionarysearchKeyword_0);
		defaultBlockFormat(c, m.dictionarySearchAccess.leftCurlyBracketKeyword_2, m.dictionarySearchAccess.rightCurlyBracketKeyword_6)

		// dictionary composite
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryCompositeAccess.compositeKeyword_0);
		defaultBlockFormat(c, m.dictionaryCompositeAccess.leftCurlyBracketKeyword_2, m.dictionaryCompositeAccess.rightCurlyBracketKeyword_6)

		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.dictionaryControlRule);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryControlRule);

		// navigation node
		defaultBlockFormat(c, m.navigationNodeAccess.leftCurlyBracketKeyword_2, m.navigationNodeAccess.rightCurlyBracketKeyword_9)

		// enumeration
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.enumerationAccess.enumerationKeyword_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.enumerationValueAccess.nameAssignment_0);
		defaultBlockFormat(c, m.enumerationAccess.leftCurlyBracketKeyword_2, m.enumerationAccess.rightCurlyBracketKeyword_4)

		c.setAutoLinewrap(180);
	}
}

