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

	private int MIN_KEYWORD_WRAPS = 2;

	private int DEFAULT_KEYWORD_WRAPS = 2;

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
		defaultBlockFormat(c, m.entityRule, m.entityAccess.leftCurlyBracketKeyword_4, m.entityAccess.rightCurlyBracketKeyword_7)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).after(m.entityAttributeRule);
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.entityAttributeRule);

		// service
		defaultBlockFormat(c, m.serviceRule, m.serviceAccess.leftCurlyBracketKeyword_3, m.serviceAccess.rightCurlyBracketKeyword_6)
		c.setLinewrap(MIN_WRAPS, DEFAULT_WRAPS, MAX_WRAPS).before(m.serviceMethodRule);

		// value object
		defaultBlockFormat(c, m.valueObjectRule, m.valueObjectAccess.leftCurlyBracketKeyword_4, m.valueObjectAccess.rightCurlyBracketKeyword_6)

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
		defaultBlockFormat(c, m.dictionaryEditorRule, m.dictionaryEditorAccess.leftCurlyBracketKeyword_2, m.dictionaryEditorAccess.rightCurlyBracketKeyword_5)

		// dictionary filter
		defaultBlockFormat(c, m.dictionaryFilterRule, m.dictionaryFilterAccess.leftCurlyBracketKeyword_2, m.dictionaryFilterAccess.rightCurlyBracketKeyword_4)

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
		defaultBlockFormat(c, m.navigationNodeRule, m.navigationNodeAccess.leftCurlyBracketKeyword_2, m.navigationNodeAccess.rightCurlyBracketKeyword_8)

		// enumeration
		c.setLinewrap(1, 1, 2).before(m.enumerationRule);

		//c.setIndentation(m.packageDeclarationAccess.leftCurlyBracketKeyword_2, m.packageDeclarationAccess.rightCurlyBracketKeyword_4)
		//c.setLinewrap(1, 1, 1).after(m.packageDeclarationAccess.leftCurlyBracketKeyword_2)
		//c.setLinewrap(2, 2, 2).after(m.packageDeclarationAccess.rightCurlyBracketKeyword_4)
		//c.setLinewrap(0, 1, 2).after(m.packageDeclarationAccess. DictionaryElements.cLeftCurlyBracketKeyword_7_1);
		c.setAutoLinewrap(180);
	}
}
