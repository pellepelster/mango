
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
import io.pelle.mango.client.gwt.utils.BaseCustomGwtCompositeGeneratedFactory;
import io.pelle.mango.client.gwt.utils.CustomGwtComposite;

public class CustomGwtCompositeFactoryGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {

		String generatorName = "CustomGwtCompositeFactoryImpl";

		PrintWriter pw = context.tryCreate(logger, "io.pelle.mango.client", generatorName);

		if (pw != null) {

			pw.println("package io.pelle.mango.client;");
			pw.println();
			pw.println("import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;");
			pw.println("import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;");
			pw.println("import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;");
			pw.println();
			pw.println("public class " + generatorName + " extends " + BaseCustomGwtCompositeGeneratedFactory.class.getName() + " {");
			pw.println("	public " + generatorName + "() {");

			TypeOracle oracle = context.getTypeOracle();

			for (JClassType gwtCompositeType : oracle.getTypes()) {

				CustomGwtComposite gwtCompositeAnnotation = gwtCompositeType.getAnnotation(CustomGwtComposite.class);

				if (gwtCompositeAnnotation != null) {

					System.out.println(String.format("found CustomGwtComposite '%s'", gwtCompositeAnnotation.value(), gwtCompositeType.getQualifiedSourceName()));

					for (JClassType compositeType : oracle.getTypes()) {

						CustomComposite compositeAnnotation = compositeType.getAnnotation(CustomComposite.class);

						if (compositeAnnotation != null) {

							System.out.println(String.format("found CustomComposite '%s'", compositeAnnotation.value(), compositeType.getQualifiedSourceName()));

							if (compositeAnnotation.value().toLowerCase().equals(gwtCompositeAnnotation.value().toLowerCase())) {

								logger.log(Type.ERROR, String.format("found annotated gwt class '%s' for custom composite '%s'", gwtCompositeType.getQualifiedSourceName(), compositeType.getQualifiedSourceName()));

								String name = gwtCompositeType.getQualifiedSourceName();

								pw.println("register(\"" + gwtCompositeAnnotation.value().toLowerCase() + "\", ");
								pw.println("	new FactoryMethod() {");
								pw.println("		public Object create(io.pelle.mango.client.web.modules.dictionary.container.BaseCustomComposite baseCustomComposite) {");
								pw.println("			return new " + name + "((" + compositeType.getQualifiedSourceName() + ")baseContainer);");
								pw.println("		}");
								pw.println("	});");

								break;
							}
						}
					}
				}
			}

			pw.println("	}");
			pw.println("}");
			context.commit(logger, pw);
		}

		return "io.pelle.mango.client." + generatorName + "";
	}
}
