package io.pelle.mango.gradle;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.StandardClassMetadata;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;

public class BaseClassTypeFilter extends AbstractClassTestingTypeFilter {

        private Class<?> baseClass;

        public BaseClassTypeFilter(Class<?> baseClass) {
                this.baseClass = baseClass;
        }

        @Override
        protected boolean match(ClassMetadata metadata) {

                if (!metadata.hasSuperClass()) {
                        return false;
                }

                try {
                        ClassMetadata currentClass = metadata;

                        while (currentClass.hasSuperClass()) {

                                currentClass = new StandardClassMetadata(Class.forName(currentClass.getSuperClassName()));

                                if (currentClass.getClassName().equals(baseClass.getName())) {
                                        return true;
                                }
                        }

                } catch (Exception e) {
                        return false;
                }

                return false;
        }

}
