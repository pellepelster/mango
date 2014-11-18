package io.pelle.mango.dsl.formatting

import com.google.inject.Inject
import io.pelle.mango.dsl.services.MangoGrammarAccess
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter
import org.eclipse.xtext.formatting.impl.FormattingConfig

class MangoFormatter extends AbstractDeclarativeFormatter {

	@Inject
	extension MangoGrammarAccess m

	private int MIN_WRAPS = 1;

	private int DEFAULT_WRAPS = 1;

	private int MAX_WRAPS = 2;

	private int MIN_KEYWORD_WRAPS = 1;

	private int DEFAULT_KEYWORD_WRAPS = 1;

	private int MAX_KEYWORD_WRAPS = 2;

	def void defaultBlockFormat(FormattingConfig c, EObject rule, Keyword leftCurlyBracketKeyword, Keyword rightCurlyBracketKeyword) {
		c.setLinewrap(MIN_KEYWORD_WRAPS, DEFAULT_KEYWORD_WRAPS, MAX_KEYWORD_WRAPS).before(rule);
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
		defaultBlockFormat(c, m.modelRule, m.modelAccess.leftCurlyBracketKeyword_2, m.modelAccess.rightCurlyBracketKeyword_4)

		// package
		defaultBlockFormat(c, m.packageDeclarationRule, m.packageDeclarationAccess.leftCurlyBracketKeyword_2, m.packageDeclarationAccess.rightCurlyBracketKeyword_4)

		// datatype
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.datatypeRule);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.datatypeRule);

		// entity
		defaultBlockFormat(c, m.entityRule, m.entityAccess.leftCurlyBracketKeyword_4, m.entityAccess.rightCurlyBracketKeyword_8)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.entityAttributeRule);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.entityAttributeRule);

		// service
		defaultBlockFormat(c, m.serviceRule, m.serviceAccess.leftCurlyBracketKeyword_3, m.serviceAccess.rightCurlyBracketKeyword_6)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.serviceMethodRule);

		// value object
		defaultBlockFormat(c, m.valueObjectRule, m.valueObjectAccess.leftCurlyBracketKeyword_4, m.valueObjectAccess.rightCurlyBracketKeyword_6)

		// base control
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.baseDictionaryControlAccess.entityattributeKeyword_0_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.baseDictionaryControlAccess.readonlyKeyword_4_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.labelsAccess.columnLabelKeyword_3_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.labelsAccess.editorLabelKeyword_4_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.labelsAccess.filterLabelKeyword_2_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.labelsAccess.editorLabelKeyword_4_0);

		// text control
		defaultBlockFormat(c, m.dictionaryTextControlRule, m.dictionaryTextControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryTextControlAccess.rightCurlyBracketKeyword_4_2)
			
		defaultBlockFormat(c, m.dictionaryIntegerControlRule, m.dictionaryIntegerControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryIntegerControlAccess.rightCurlyBracketKeyword_4_2)
		defaultBlockFormat(c, m.dictionaryBigDecimalControlRule, m.dictionaryBigDecimalControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryBigDecimalControlAccess.rightCurlyBracketKeyword_4_2)
		defaultBlockFormat(c, m.dictionaryDateControlRule, m.dictionaryDateControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryDateControlAccess.rightCurlyBracketKeyword_4_2)

		// reference control
		defaultBlockFormat(c, m.dictionaryReferenceControlRule, m.dictionaryReferenceControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryReferenceControlAccess.rightCurlyBracketKeyword_4_5)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryReferenceControlAccess.dictionaryKeyword_4_2_0);

		defaultBlockFormat(c, m.dictionaryBooleanControlRule, m.dictionaryBooleanControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryBooleanControlAccess.rightCurlyBracketKeyword_4_2)

		// enumeration control
		defaultBlockFormat(c, m.dictionaryEnumerationControlRule, m.dictionaryEnumerationControlAccess.leftCurlyBracketKeyword_4_0, m.dictionaryEnumerationControlAccess.rightCurlyBracketKeyword_4_2)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryEnumerationControlAccess.enumerationcontrolKeyword_1);

		// datatypes
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.baseDataTypeAccess.labelKeyword_1_0);

		defaultBlockFormat(c, m.stringDataTypeRule, m.stringDataTypeAccess.leftCurlyBracketKeyword_3, m.stringDataTypeAccess.rightCurlyBracketKeyword_7)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.stringDataTypeAccess.minLengthKeyword_6_0);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.stringDataTypeAccess.maxLengthKeyword_5_0);
		
		defaultBlockFormat(c, m.integerDataTypeRule, m.integerDataTypeAccess.leftCurlyBracketKeyword_3, m.integerDataTypeAccess.rightCurlyBracketKeyword_5)
		defaultBlockFormat(c, m.decimalDataTypeRule, m.decimalDataTypeAccess.leftCurlyBracketKeyword_3, m.decimalDataTypeAccess.rightCurlyBracketKeyword_5)
		defaultBlockFormat(c, m.dateDataTypeRule, m.dateDataTypeAccess.leftCurlyBracketKeyword_3, m.dateDataTypeAccess.rightCurlyBracketKeyword_5)
		defaultBlockFormat(c, m.entityDataTypeRule, m.entityDataTypeAccess.leftCurlyBracketKeyword_3, m.entityDataTypeAccess.rightCurlyBracketKeyword_7)
		defaultBlockFormat(c, m.booleanDataTypeRule, m.booleanDataTypeAccess.leftCurlyBracketKeyword_3, m.booleanDataTypeAccess.rightCurlyBracketKeyword_5)
		defaultBlockFormat(c, m.enumerationDataTypeRule, m.enumerationDataTypeAccess.leftCurlyBracketKeyword_3, m.enumerationDataTypeAccess.rightCurlyBracketKeyword_7)

		// dictionary
		defaultBlockFormat(c, m.dictionaryRule, m.dictionaryAccess.leftCurlyBracketKeyword_2, m.dictionaryAccess.rightCurlyBracketKeyword_11)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryAccess.entityKeyword_3);
		c.setLinewrap(MIN_KEYWORD_WRAPS, DEFAULT_KEYWORD_WRAPS, MAX_KEYWORD_WRAPS).before(m.dictionaryAccess.dictionarycontrolsKeyword_7_0);
		c.setIndentationIncrement.after(m.dictionaryAccess.leftCurlyBracketKeyword_7_1);
		c.setIndentationDecrement.before(m.dictionaryAccess.rightCurlyBracketKeyword_7_3);

		// dictionary control group
		//c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.dictionaryControlGroupOptionsRule);
		//c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryControlGroupOptionsRule);
		defaultBlockFormat(c, m.dictionaryControlGroupRule, m.dictionaryControlGroupAccess.leftCurlyBracketKeyword_4_0, m.dictionaryControlGroupAccess.rightCurlyBracketKeyword_4_4)
		defaultBlockFormat(c, m.dictionaryControlGroupOptionsContainerRule, m.dictionaryControlGroupOptionsContainerAccess.leftCurlyBracketKeyword_2, m.dictionaryControlGroupOptionsContainerAccess.rightCurlyBracketKeyword_4)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.dictionaryControlGroupOptionsRule);

		// dictionary editor
		defaultBlockFormat(c, m.dictionaryEditorRule, m.dictionaryEditorAccess.leftCurlyBracketKeyword_2, m.dictionaryEditorAccess.rightCurlyBracketKeyword_7)

		// dictionary filter
		defaultBlockFormat(c, m.dictionaryFilterRule, m.dictionaryFilterAccess.leftCurlyBracketKeyword_2, m.dictionaryFilterAccess.rightCurlyBracketKeyword_6)

		// dictionary result
		defaultBlockFormat(c, m.dictionaryResultRule, m.dictionaryResultAccess.leftCurlyBracketKeyword_2, m.dictionaryResultAccess.rightCurlyBracketKeyword_4)

		// dictionary controls
//		defaultBlockFormat(c, m.dictionary ResultRule, m.dictionaryResultAccess.leftCurlyBracketKeyword_2, m.dictionaryResultAccess.rightCurlyBracketKeyword_4)

		// dictionary search
		defaultBlockFormat(c, m.dictionarySearchRule, m.dictionarySearchAccess.leftCurlyBracketKeyword_2, m.dictionarySearchAccess.rightCurlyBracketKeyword_6)

		// dictionary composite
		defaultBlockFormat(c, m.dictionaryCompositeRule, m.dictionaryCompositeAccess.leftCurlyBracketKeyword_2, m.dictionaryCompositeAccess.rightCurlyBracketKeyword_6)

		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.dictionaryControlRule);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.dictionaryControlRule);

		// navigation node
		defaultBlockFormat(c, m.navigationNodeRule, m.navigationNodeAccess.leftCurlyBracketKeyword_2, m.navigationNodeAccess.rightCurlyBracketKeyword_9)

		// enumeration
		c.setLinewrap(1, 1, 2).before(m.enumerationRule);

		//c.setIndentation(m.packageDeclarationAccess.leftCurlyBracketKeyword_2, m.packageDeclarationAccess.rightCurlyBracketKeyword_4)
		//c.setLinewrap(1, 1, 1).after(m.packageDeclarationAccess.leftCurlyBracketKeyword_2)
		//c.setLinewrap(2, 2, 2).after(m.packageDeclarationAccess.rightCurlyBracketKeyword_4)
		//c.setLinewrap(0, 1, 2).after(m.packageDeclarationAccess. DictionaryElements.cLeftCurlyBracketKeyword_7_1);
		c.setAutoLinewrap(180);
	}
}
