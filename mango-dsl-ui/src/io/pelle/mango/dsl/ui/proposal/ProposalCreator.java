package io.pelle.mango.dsl.ui.proposal;

import io.pelle.mango.dsl.emf.EObjectQuery;
import io.pelle.mango.dsl.emf.EmfModelQuery;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ProposalCreator {

	private EObject eObject;

	private boolean removeTypeFromName = false;

	private EObjectQuery<EObject> from;

	public ProposalCreator(EObject eObject) {
		this.eObject = eObject;
	}

	public ProposalCreator createProposalsFor(EObject eObject) {
		return new ProposalCreator(eObject);
	}

	public <T extends EObject> ProposalCreator fromParent(Class<T> parentType, EStructuralFeature parentFeature) {

		EObjectQuery<T> parent = EmfModelQuery.createEObjectQuery(eObject).getParentByType(parentType);

		if (parent.hasMatch()) {
			if (parent.getMatch().eIsSet(parentFeature)) {
				String featureString = parent.getMatch().eGet(parentFeature).toString();
			}
		}

		return this;
	}
}
