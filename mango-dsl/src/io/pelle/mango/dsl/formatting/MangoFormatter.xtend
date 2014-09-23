package io.pelle.mango.dsl.formatting

import com.google.inject.Inject
import io.pelle.mango.dsl.services.MangoGrammarAccess
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter
import org.eclipse.xtext.formatting.impl.FormattingConfig

class MangoFormatter extends AbstractDeclarativeFormatter {

	@Inject
	extension MangoGrammarAccess m

	override protected void configureFormatting(FormattingConfig c) {

		c.setLinewrap(0, 1, 2).before(SL_COMMENTRule)
		c.setLinewrap(0, 1, 2).before(ML_COMMENTRule)
		c.setLinewrap(0, 1, 1).after(ML_COMMENTRule)

		c.setAutoLinewrap(180);
	}
}
