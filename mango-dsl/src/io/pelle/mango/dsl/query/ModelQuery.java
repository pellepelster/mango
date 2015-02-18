package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.AbstractElement;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.MangoFactory;
import io.pelle.mango.dsl.mango.Model;
import io.pelle.mango.dsl.mango.ModelRoot;
import io.pelle.mango.dsl.mango.PackageDeclaration;
import io.pelle.mango.dsl.query.functions.FunctionEObjectTypeSelect;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

public class ModelQuery {
	
	private Model model;

	private ModelQuery(Model model) {
		this.model = model;
	}

	public static ModelQuery createQuery(ModelRoot modelRoot) {
		if (modelRoot instanceof Model) {
			return new ModelQuery((Model) modelRoot);

		} else {
			throw new RuntimeException(String.format("unsupported model root '%s'", modelRoot.getClass().getName()));
		}
	}

	public static ModelQuery createQuery(Model model) {
		return new ModelQuery(model);
	}

	public PackageQuery getRootPackages() {
		return new PackageQuery(Collections2.transform(this.model.eContents(), FunctionEObjectTypeSelect.getFunction(PackageDeclaration.class)));
	}

	public EntitiesQuery getAllEntities() {
		Iterator<Entity> entities = Iterators.transform(this.model.eAllContents(), FunctionEObjectTypeSelect.getFunction(Entity.class));
		return new EntitiesQuery(Lists.newArrayList(Iterators.filter(entities, Predicates.notNull())));
	}

	private List<PackageDeclaration> getPackages(List<AbstractElement> abstractElements) {
		Iterator<PackageDeclaration> entities = Iterators.transform(abstractElements.iterator(), FunctionEObjectTypeSelect.getFunction(PackageDeclaration.class));
		return Lists.newArrayList(Iterators.filter(entities, Predicates.notNull()));
	}

	public void createPackageHiararchy(Collection<PackageDeclaration> packageDeclarations, String parentPackageName, SortedMap<String, PackageDeclaration> packageHierarchy) {
		String delimiter = "";

		if (parentPackageName != null && !parentPackageName.isEmpty()) {
			delimiter = ".";
		}

		for (PackageDeclaration packageDeclaration : packageDeclarations) {
			String currentPackageName = parentPackageName + delimiter + packageDeclaration.getPackageName();
			packageHierarchy.put(currentPackageName, packageDeclaration);

			createPackageHiararchy(getPackages(packageDeclaration.getElements()), currentPackageName, packageHierarchy);
		}
	}

	public PackageDeclaration getPackageByName(Model model, Collection<PackageDeclaration> packageDeclarations, String packageName, boolean create) {
		SortedMap<String, PackageDeclaration> packageHierarchy = new TreeMap<String, PackageDeclaration>();
		createPackageHiararchy(packageDeclarations, "", packageHierarchy);

		if (packageHierarchy.containsKey(packageName)) {
			return packageHierarchy.get(packageName);
		}

		if (create) {
			String tempPackageName = packageName;
			PackageDeclaration parentPackage = null;

			while (tempPackageName.lastIndexOf(".") > -1) {
				tempPackageName = tempPackageName.substring(0, tempPackageName.lastIndexOf("."));

				if (packageHierarchy.containsKey(tempPackageName)) {
					parentPackage = packageHierarchy.get(tempPackageName);
					break;
				}
			}

			if (parentPackage != null) {
				String existingPackage = tempPackageName;
				String packageToCreate = packageName.substring(tempPackageName.length() + 1);

				PackageDeclaration packageToSplit = null;

				tempPackageName = packageToCreate;
				while (tempPackageName.lastIndexOf(".") > -1) {
					tempPackageName = tempPackageName.substring(0, tempPackageName.lastIndexOf("."));

					for (PackageDeclaration p : getPackages(parentPackage.getElements())) {
						if (p.getPackageName().startsWith(tempPackageName)) {
							packageToSplit = p;
							existingPackage = tempPackageName;
							break;
						}
					}
				}

				if (packageToSplit == null) {
					PackageDeclaration newPackage = MangoFactory.eINSTANCE.createPackageDeclaration();
					newPackage.setPackageName(packageToCreate);

					parentPackage.getElements().add(newPackage);

					return newPackage;
				} else {
					String oldPackageName = packageToSplit.getPackageName().substring(0, existingPackage.length());
					String oldPackageSplitName = packageToSplit.getPackageName().substring(existingPackage.length() + 1);
					packageToCreate = packageToCreate.substring(existingPackage.length() + 1);

					packageToSplit.setPackageName(oldPackageName);

					PackageDeclaration splitPackage = MangoFactory.eINSTANCE.createPackageDeclaration();
					splitPackage.setPackageName(oldPackageSplitName);

					splitPackage.getElements().addAll(packageToSplit.getElements());
					splitPackage.getElements().clear();

					packageToSplit.getElements().add(splitPackage);

					PackageDeclaration newPackage = MangoFactory.eINSTANCE.createPackageDeclaration();
					newPackage.setPackageName(packageToCreate);
					packageToSplit.getElements().add(newPackage);

					return newPackage;
				}
			}

			PackageDeclaration newPackage = MangoFactory.eINSTANCE.createPackageDeclaration();
			newPackage.setPackageName(packageName);
			model.getElements().add(newPackage);

			return newPackage;

		}

		return null;
	}

	public PackageDeclaration getPackageByName(String packageName) {
		return getPackageByName(this.model, getRootPackages().getCollection(), packageName, false);
	}

	public PackageDeclaration getAndCreatePackageByName(String packageName) {
		return getPackageByName(this.model, getRootPackages().getCollection(), packageName, true);
	}
}
