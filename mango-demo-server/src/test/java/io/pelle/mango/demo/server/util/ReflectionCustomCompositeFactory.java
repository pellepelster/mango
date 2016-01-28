package io.pelle.mango.demo.server.util;

import java.lang.reflect.Constructor;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;
import io.pelle.mango.client.base.util.CustomComposite;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.util.ICustomCompositeFactory;

public class ReflectionCustomCompositeFactory implements ICustomCompositeFactory {

	private static final LoadingCache<String, Set<Class<?>>> types = CacheBuilder.newBuilder().maximumSize(1000).build(new CacheLoader<String, Set<Class<?>>>() {

		public Set<Class<?>> load(String key) {
			Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));
			return reflections.getTypesAnnotatedWith(CustomComposite.class);
		}
	});

	private static final LoadingCache<String, Class<?>> annotatedClass = CacheBuilder.newBuilder().maximumSize(1000).build(new CacheLoader<String, Class<?>>() {

		public Class<?> load(final String key) {
			Optional<Class<?>> customCompositeClass = Iterables.tryFind(types.getUnchecked(""), new Predicate<Class<?>>() {

				@Override
				public boolean apply(Class<?> input) {

					CustomComposite customComposite = input.getAnnotation(CustomComposite.class);

					return customComposite.value().equalsIgnoreCase(key);
				}
			});

			if (customCompositeClass.isPresent()) {
				return customCompositeClass.get();
			} else {
				throw new RuntimeException(String.format("no class found implementing custom composite '%s'", key));
			}

		}
	});

	public ReflectionCustomCompositeFactory() {
		super();
	}

	@Override
	public Object create(final String type, ICustomCompositeModel compositeModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		try {

			Constructor constructor = annotatedClass.getUnchecked(type).getConstructor(ICustomCompositeModel.class, BaseDictionaryElement.class);
			return constructor.newInstance(compositeModel, parent);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
