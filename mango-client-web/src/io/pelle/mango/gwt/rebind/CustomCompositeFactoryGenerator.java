
package io.pelle.mango.gwt.rebind;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

import io.pelle.mango.client.base.util.CustomComposite;
import io.pelle.mango.client.web.util.BaseCustomCompositeGeneratedFactory;

public class CustomCompositeFactoryGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {

		String generatorName = "CustomCompositeFactoryImpl";

		PrintWriter pw = context.tryCreate(logger, "io.pelle.mango.client", generatorName);

		if (pw != null) {

			pw.println("package io.pelle.mango.client;");
			pw.println();
			pw.println("import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;");
			pw.println("import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;");
			pw.println("import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;");
			pw.println();
			pw.println("public class " + generatorName + " extends " + BaseCustomCompositeGeneratedFactory.class.getName() + " {");
			pw.println("	public " + generatorName + "() {");

			TypeOracle oracle = context.getTypeOracle();

			for (JClassType type : oracle.getTypes()) {

				CustomComposite annoation = type.getAnnotation(io.pelle.mango.client.base.util.CustomComposite.class);

				if (annoation != null) {

					logger.log(Type.INFO, String.format("found annotated class '%s'", type.getQualifiedSourceName()));

					String name = type.getQualifiedSourceName();

					pw.println("register(\"" + annoation.value().toLowerCase() + "\", ");
					pw.println("	new FactoryMethod() {");
					pw.println("		public Object create(ICustomCompositeModel compositeModel, BaseDictionaryElement<? extends IBaseModel> parent) {");
					pw.println("			return new " + name + "(compositeModel, parent);");
					pw.println("		}");
					pw.println("	});");
				}
			}

			pw.println("	}");
			pw.println("}");
			context.commit(logger, pw);
		}

		return "io.pelle.mango.client." + generatorName + "";
	}
}
