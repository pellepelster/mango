package io.pelle.mango.server.entity;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.db.voquery.AttributesDescriptorQuery;
import io.pelle.mango.db.voquery.VOClassQuery;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class EntityUtils {

	public static class ReferenceTree {

		private Map<IAttributeDescriptor<?>, ReferenceTree> attributePaths = new HashMap<IAttributeDescriptor<?>, ReferenceTree>();

		public Map<IAttributeDescriptor<?>, ReferenceTree> getAttributePaths() {
			return attributePaths;
		}

	}

	@SuppressWarnings("unchecked")
	public static ReferenceTree parseReferenceTree(Class<? extends IVOEntity> voEntityClass, String referenceTree) {

		ReferenceTree result = new ReferenceTree();

		AttributesDescriptorQuery<?> attributesDescriptorQuery = VOClassQuery.createQuery(voEntityClass).attributesDescriptors();
		StringTokenizer referenceTreeTokenizer = new StringTokenizer(referenceTree, ",");

		while (referenceTreeTokenizer.hasMoreTokens()) {

			String currentToken = referenceTreeTokenizer.nextToken();
			String attributeName = currentToken;
			String subAttributeName = null;

			if (currentToken.contains("[") && currentToken.endsWith("]")) {

				int i = currentToken.indexOf("[");

				attributeName = currentToken.substring(0, i);
				subAttributeName = currentToken.substring(i + 1, currentToken.length() - 1);
			}

			AttributesDescriptorQuery<?> tmp = attributesDescriptorQuery.byName(attributeName);

			if (tmp.hasExactlyOne()) {

				IAttributeDescriptor<?> attributeDescriptor = tmp.getSingleResult();
				ReferenceTree subTree = null;

				if (subAttributeName != null) {

					if (!IVOEntity.class.isAssignableFrom(attributeDescriptor.getAttributeType())) {
						throw new RuntimeException(String.format("attribute '%s' of class '%s' is no an IVOEntity type", currentToken, voEntityClass.getName()));
					} else {
						subTree = parseReferenceTree((Class<? extends IVOEntity>) attributeDescriptor.getAttributeType(), subAttributeName);
					}
				}

				result.getAttributePaths().put(attributeDescriptor, subTree);
			} else {
				throw new RuntimeException(String.format("class '%s' has no attribute '%s'", voEntityClass.getName(), currentToken));
			}
		}

		return result;
	}

}
