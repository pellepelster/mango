package io.pelle.mango.server.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import io.pelle.mango.server.base.meta.Documented;

@RequestMapping(IDocumentationBean.DOCUMENTATION_BASE_PATH + "/" + RestServiceDocumentationBean.REST_DOCUMENTATION_BASE_PATH)
public class RestServiceDocumentationBean implements ApplicationListener<ContextRefreshedEvent>, IDocumentationBean {

	public final static String REST_DOCUMENTATION_BASE_PATH = "rest";

	public final static String INDEX_VIEW_NAME = "DocumentationRestApi";

	public final static String REST_DOCUMENTATION_TEMPLATE_VARIABLE = "restDocumentations";

	private List<RestPackageDocumentation> packageDocumentations = new ArrayList<RestPackageDocumentation>();

	public List<RestPackageDocumentation> getPackageDocumentations() {
		return packageDocumentations;
	}

	public class RestPackageDocumentation {

		private final String packageName;

		private List<RestServiceDocumentation> serviceDocumentations = new ArrayList<RestServiceDocumentation>();

		public RestPackageDocumentation(String packageName) {
			super();
			this.packageName = packageName;
		}

		public String getPackageName() {
			return packageName;
		}

		public List<RestServiceDocumentation> getServiceDocumentations() {
			return serviceDocumentations;
		}
	}

	public class RestServiceDocumentation {

		private final String className;

		public RestServiceDocumentation(String className) {
			super();
			this.className = className;
		}

		public String getClassName() {
			return className;
		}

	}

	@Override
	public List<NavigationItem> getPrimaryNavigation() {
		return Arrays.asList(new NavigationItem[] { new NavigationItem("DocumentationRestApi", "Rest API", REST_DOCUMENTATION_BASE_PATH) });
	}

	@RequestMapping("*")
	public ModelAndView index() {

		NavigationModel navigationModel = new NavigationModel("xx", "yyy");

		Map<String, Object> templateModel = new HashMap<String, Object>();
		templateModel.put(NAVIGATION_MODEL_TEMPLATE_VARIABLE, navigationModel);
		templateModel.put(REST_DOCUMENTATION_TEMPLATE_VARIABLE, packageDocumentations);

		return new ModelAndView(INDEX_VIEW_NAME, templateModel);

	}

	private class PackageNamePredicate implements Predicate<RestPackageDocumentation> {

		private final String packageName;

		public PackageNamePredicate(String packageName) {
			super();
			this.packageName = packageName;
		}

		@Override
		public boolean apply(RestPackageDocumentation input) {
			return packageName.equals(input.packageName);
		}

	}

	private RestPackageDocumentation getOrInitRestPackageDocumentation(String packageName) {

		Optional<RestPackageDocumentation> result = Iterables.tryFind(packageDocumentations, new PackageNamePredicate(packageName));

		if (result.isPresent()) {
			return result.get();
		} else {

			RestPackageDocumentation packageDocumentation = new RestPackageDocumentation(packageName);
			packageDocumentations.add(packageDocumentation);
			return packageDocumentation;
		}

	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		packageDocumentations.clear();

		String[] beanNames = event.getApplicationContext().getBeanDefinitionNames();

		for (String beanName : beanNames) {

			Object bean = event.getApplicationContext().getBean(beanName);
			Class<?> beanClass = AopProxyUtils.ultimateTargetClass(bean);
			
			Documented documented = beanClass.getAnnotation(Documented.class);
			
			if (documented != null) {
				RestPackageDocumentation packageDocumentation = getOrInitRestPackageDocumentation(bean.getClass().getPackage().getName());
				RestServiceDocumentation restDocumentation = new RestServiceDocumentation(bean.getClass().getName());
				packageDocumentation.getServiceDocumentations().add(restDocumentation);
			}
		}
	}
}
