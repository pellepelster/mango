
package io.pelle.mango.gwt.rebind;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;

class MangoGeneratedFactoryGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {

		PrintWriter pw = context.tryCreate(logger, "io.pelle.mango.client", "GeneratedFactoryImpl");

		if (pw != null) {
			// TypeOracle oracle = context.getTypeOracle();
			//
			// for (JClassType type : oracle.getTypes()) {
			// if
			// (type.getAnnotation(io.pelle.mango.client.base.util.MayCreate.class)
			// != null) {
			// String name = type.getQualifiedSourceName();
			// pw.println("register(\"" + name + "\", ");
			// pw.println(" new FactoryMethod() {");
			// pw.println(" public Object create() {");
			// pw.println(" return new " + name + "();");
			// pw.println(" }");
			// pw.println(" });");
			// }
			// }

			// pw.println(" }");
			// pw.println("}");
			context.commit(logger, pw);
		}

		return "io.pelle.mango.client.GeneratedFactoryImpl";
	}
}
