package io.pelle.mango.dsl.jvmmodel

import com.google.inject.Inject
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.ModelRoot
import io.pelle.mango.dsl.mango.ValueObject
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor.IPostIndexingInitializing
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>     
 */
class MangoJvmModelInferrer extends AbstractModelInferrer {

	@Inject
	extension JvmTypesBuilder

	@Inject
	extension IQualifiedNameProvider
	
	/**
     * convenience API to build and initialize JVM types and t heir members.
     */
	/**
	 * The dispatch method {@code infer} is called for each instance of the
	 * given element's type that is contained in a resource.
	 * 
	 * @param element
	 *            the model to create one or more
	 *            {@link JvmDeclaredType declared
	 *            types} from.
	 * @param acceptor
	 *            each created
	 *            {@link JvmDeclaredType type}
	 *            without a container should be passed to the acceptor in order
	 *            get attached to the current resource. The acceptor's
	 *            {@link IJvmDeclaredTypeAcceptor#accept(org.eclipse.xtext.common.types.JvmDeclaredType)
	 *            accept(..)} method takes the constructed empty type for the
	 *            pre-indexing phase. This one is further initialized in the
	 *            indexing phase using the closure you pass to the returned
	 *            {@link IPostIndexingInitializing#initializeLater(org.eclipse.xtext.xbase.lib.Procedures.Procedure1)
	 *            initializeLater(..)}.
	 * @param isPreIndexingPhase
	 *            whether the method is called in a pre-indexing phase, i.e.
	 *            when the global index is not yet fully updated. You must not
	 *            rely on linking using the index if isPreIndexingPhase is
	 *            <code>true</code>.
	 */
	def dispatch void infer(ModelRoot modelRoot, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {

		for (Entity entity : modelRoot.eAllContents.toIterable.filter(Entity)) {
			infer(entity, acceptor, isPreIndexingPhase);
		}

		for (ValueObject valueObject : modelRoot.eAllContents.toIterable.filter(ValueObject)) {
			infer(valueObject, acceptor, isPreIndexingPhase);
		}
	}

	def dispatch void infer(ValueObject valueObject, IJvmDeclaredTypeAcceptor acceptor, boolean isPrelinkingPhase) {
		acceptor.accept(valueObject.toClass(valueObject.fullyQualifiedName)).initializeLater [
			documentation = valueObject.documentation
		]
	}

	def dispatch void infer(Entity entity, IJvmDeclaredTypeAcceptor acceptor, boolean isPrelinkingPhase) {
		acceptor.accept(entity.toClass(entity.fullyQualifiedName)).initializeLater [
			documentation = entity.documentation
		]
	}

}
