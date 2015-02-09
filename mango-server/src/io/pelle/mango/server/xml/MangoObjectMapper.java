package io.pelle.mango.server.xml;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class MangoObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 7269036357617656481L;

	private List<String> FILTERED_FIELDS = Arrays.asList(new String[] { "oid", "naturalKey", "new", "changeTracker" });

	public MangoObjectMapper() {

		super();
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// setSerializationInclusion(JsonInclude.Include.NON_NULL);

		setSerializerFactory(getSerializerFactory().withSerializerModifier(new BeanSerializerModifier() {
			@Override
			public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
				Iterables.removeIf(beanProperties, new Predicate<BeanPropertyWriter>() {
					@Override
					public boolean apply(BeanPropertyWriter input) {
						return FILTERED_FIELDS.contains(input.getFullName().getSimpleName());
					}
				});

				return beanProperties;
			}
		}));

	}
}
