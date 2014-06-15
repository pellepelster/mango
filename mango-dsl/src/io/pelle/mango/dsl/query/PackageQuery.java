package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.PackageDeclaration;

import java.util.Collection;

public class PackageQuery extends BaseEObjectCollectionQuery<PackageDeclaration>
{
	public PackageQuery(Collection<PackageDeclaration> packages)
	{
		super(packages);
	}

}
