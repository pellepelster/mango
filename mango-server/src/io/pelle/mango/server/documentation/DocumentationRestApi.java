package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import io.pelle.mango.server.base.meta.Documented;

@RequestMapping(IDocumentationBean.DOCUMENTATION_BASE_PATH + "/" + DocumentationRestApi.REST_DOCUMENTATION_BASE_PATH)
public class DocumentationRestApi implements ApplicationListener<ContextRefreshedEvent>, IDocumentationBean {

	public final static String REST_DOCUMENTATION_BASE_PATH = "rest";

	public final static String INDEX_VIEW_NAME = DocumentationRestApi.class.getPackage().getName().replace(".", "/") + "/templates/DocumentationRestApiIndex";

	public final static String PACKAGE_VIEW_NAME = DocumentationRestApi.class.getPackage().getName().replace(".", "/") + "/templates/DocumentationRestApiPackageIndex";

	public final static String SERVICE_VIEW_NAME = DocumentationRestApi.class.getPackage().getName().replace(".", "/") + "/templates/DocumentationRestApiServiceIndex";

	public final static String PACKAGE_DOCUMENTATION_LIST_TEMPLATE_VARIABLE = "packageDocumentations";

	public final static String PACKAGE_DOCUMENTATION_TEMPLATE_VARIABLE = "packageDocumentation";

	public final static String SERVICE_DOCUMENTATION_TEMPLATE_VARIABLE = "serviceDocumentation";

	private static final NavigationItem NAVIGATION_ITEM = new NavigationItem("DocumentationRestApi", "Rest API", REST_DOCUMENTATION_BASE_PATH + "/" + INDEX_PATH);

	private static final BreadCrumb BREADCRUMB = new BreadCrumb(REST_DOCUMENTATION_BASE_PATH + "/" + INDEX_PATH, "Rest API");

	@Autowired
	private DocumentationService documentationService;

	private List<PackageDocumentation> packageDocumentations = new ArrayList<PackageDocumentation>();

	private class PackageNamePredicate implements Predicate<PackageDocumentation> {

		private final String packageName;

		public PackageNamePredicate(String packageName) {
			super();
			this.packageName = packageName;
		}

		@Override
		public boolean apply(PackageDocumentation input) {
			return packageName.equals(input.getPackageName());
		}
	}

	private class ServiceNamePredicate implements Predicate<RestServiceDocumentation> {

		private final String className;

		public ServiceNamePredicate(String packageName) {
			super();
			this.className = packageName;
		}

		@Override
		public boolean apply(RestServiceDocumentation input) {
			return className.equals(input.getClassName());
		}

	}

	private PackageDocumentation getOrInitRestPackageDocumentation(String packageName) {

		Optional<PackageDocumentation> result = Iterables.tryFind(packageDocumentations, new PackageNamePredicate(packageName));

		if (result.isPresent()) {
			return result.get();
		} else {

			PackageDocumentation packageDocumentation = new PackageDocumentation(packageName);
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
				PackageDocumentation packageDocumentation = getOrInitRestPackageDocumentation(bean.getClass().getPackage().getName());
				RestServiceDocumentation restDocumentation = new RestServiceDocumentation(beanClass.getName());
				packageDocumentation.getServiceDocumentations().add(restDocumentation);
			}
		}
	}

	@Override
	public List<NavigationItem> getPrimaryNavigation() {
		return Arrays.asList(new NavigationItem[] {NAVIGATION_ITEM } );
	}

	
	
	@RequestMapping(IDocumentationBean.INDEX_PATH)
	public ModelAndView index() {

		Map<String, Object> templateModel = documentationService.getDefaultTemplateModel(BREADCRUMB);
		templateModel.put(PACKAGE_DOCUMENTATION_LIST_TEMPLATE_VARIABLE, packageDocumentations);

		return new ModelAndView(INDEX_VIEW_NAME, templateModel);

	}

	@RequestMapping("/{path}")
	public ModelAndView packageIndex(@PathVariable String path) {

		Optional<PackageDocumentation> packageDocumentation = Iterables.tryFind(packageDocumentations, new PackageNamePredicate(path));

		Optional<RestServiceDocumentation> serviceDocumentation = Optional.absent();

		if (!packageDocumentation.isPresent()) {

			String packagePath = path.substring(0, path.lastIndexOf("."));

			packageDocumentation = Iterables.tryFind(packageDocumentations, new PackageNamePredicate(packagePath));
			serviceDocumentation = Iterables.tryFind(packageDocumentation.get().getServiceDocumentations(), new ServiceNamePredicate(path));
		}

		List<BreadCrumb> breadCrumbs = new ArrayList<BreadCrumb>();
		breadCrumbs.add(BREADCRUMB);
		
		if (packageDocumentation.isPresent()) {
			breadCrumbs.add(new BreadCrumb(REST_DOCUMENTATION_BASE_PATH + "/" + packageDocumentation.get().getPackageName(), packageDocumentation.get().getPackageName()));
		}

		if (serviceDocumentation.isPresent()) {
			breadCrumbs.add(new BreadCrumb(REST_DOCUMENTATION_BASE_PATH + "/" + serviceDocumentation.get().getClassName(), serviceDocumentation.get().getServiceName()));
		}

		Map<String, Object> templateModel = documentationService.getDefaultTemplateModel(breadCrumbs.toArray(new BreadCrumb[0]));
		
		String view = null;
		if (serviceDocumentation.isPresent()) {
			templateModel.put(SERVICE_DOCUMENTATION_TEMPLATE_VARIABLE, serviceDocumentation.get());
			view = SERVICE_VIEW_NAME;
		} else {
			templateModel.put(PACKAGE_DOCUMENTATION_TEMPLATE_VARIABLE, packageDocumentation.get());
			view = PACKAGE_VIEW_NAME;
		}

		return new ModelAndView(view, templateModel);

	}

	public List<PackageDocumentation> getPackageDocumentation() {
		return packageDocumentations;
	}

}
