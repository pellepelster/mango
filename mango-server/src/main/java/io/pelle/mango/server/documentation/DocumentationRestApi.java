package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.google.common.collect.Ordering;

import io.pelle.mango.server.MangoServerApplicationContext;
import io.pelle.mango.server.Messages;
import io.pelle.mango.server.base.meta.Documented;

@RequestMapping(IDocumentationContributor.DOCUMENTATION_BASE_PATH + "/" + DocumentationRestApi.REST_DOCUMENTATION_BASE_PATH)
public class DocumentationRestApi implements ApplicationListener<ContextRefreshedEvent>, IDocumentationContributor {

	public final static String REST_DOCUMENTATION_BASE_PATH = "rest";

	public final static String INDEX_VIEW_NAME = DocumentationRestApi.class.getPackage().getName().replace(".", "/") + "/templates/DocumentationRestApiIndex";

	public final static String PACKAGE_VIEW_NAME = DocumentationRestApi.class.getPackage().getName().replace(".", "/") + "/templates/DocumentationRestApiPackageIndex";

	public final static String SERVICE_VIEW_NAME = DocumentationRestApi.class.getPackage().getName().replace(".", "/") + "/templates/DocumentationRestApiServiceIndex";

	public final static String PACKAGE_DOCUMENTATION_LIST_TEMPLATE_VARIABLE = "packageDocumentations";

	public final static String PACKAGE_DOCUMENTATION_TEMPLATE_VARIABLE = "packageDocumentation";

	public final static String SERVICE_DOCUMENTATION_TEMPLATE_VARIABLE = "serviceDocumentation";

	private static final NavigationItem NAVIGATION_ITEM = new NavigationItem(Messages.getString(IDocumentationContributor.DOCUMENTATION_RESTAPI_NAME_MESSAGE_KEY),
			REST_DOCUMENTATION_BASE_PATH + "/" + INDEX_PATH);

	private static final BreadCrumb BREADCRUMB = new BreadCrumb(REST_DOCUMENTATION_BASE_PATH + "/" + INDEX_PATH, Messages.getString(IDocumentationContributor.DOCUMENTATION_RESTAPI_NAME_MESSAGE_KEY));

	// @Value("${documentation.hide.mangocontrollers:documentation.hide.mangocontrollers.default}")
	private boolean hideMangoControllersFromDocumentation = true;

	@Autowired
	private DocumentationService documentationService;

	private List<RestPackageDocumentation> packageDocumentations = new ArrayList<RestPackageDocumentation>();

	private class PackageNamePredicate implements Predicate<RestPackageDocumentation> {

		private final String packageName;

		public PackageNamePredicate(String packageName) {
			super();
			this.packageName = packageName;
		}

		@Override
		public boolean apply(RestPackageDocumentation input) {
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

	private RestPackageDocumentation getOrInitRestPackageDocumentation(String packageName) {

		Optional<RestPackageDocumentation> result = Iterables.tryFind(packageDocumentations, new PackageNamePredicate(packageName));

		if (result.isPresent()) {
			return result.get();
		} else {

			RestPackageDocumentation packageDocumentation = new RestPackageDocumentation(packageName);
			packageDocumentations.add(packageDocumentation);
			Collections.sort(packageDocumentations, Ordering.usingToString());
			return packageDocumentation;
		}

	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		String[] beanNames = event.getApplicationContext().getBeanDefinitionNames();

		String mangoRootPackageName = MangoServerApplicationContext.class.getPackage().getName();

		for (String beanName : beanNames) {

			Object bean = event.getApplicationContext().getBean(beanName);
			Class<?> beanClass = AopProxyUtils.ultimateTargetClass(bean);

			if (hideMangoControllersFromDocumentation && beanClass.getPackage() != null && beanClass.getPackage().getName().startsWith(mangoRootPackageName)) {
				continue;
			}

			Documented documented = beanClass.getAnnotation(Documented.class);
			RequestMapping requestMapping = beanClass.getAnnotation(RequestMapping.class);

			if (documented != null && requestMapping != null) {
				RestPackageDocumentation packageDocumentation = getOrInitRestPackageDocumentation(bean.getClass().getPackage().getName());
				RestServiceDocumentation restDocumentation = DocumentationReflectionFactory.getDocumentationFor(beanClass);
				packageDocumentation.getServices().add(restDocumentation);
			}
		}
	}

	@Override
	public List<NavigationItem> getPrimaryNavigation() {
		return Arrays.asList(new NavigationItem[] { NAVIGATION_ITEM });
	}

	@RequestMapping(IDocumentationContributor.INDEX_PATH)
	public ModelAndView index() {

		Map<String, Object> templateModel = documentationService.getDefaultTemplateModel(BREADCRUMB);
		templateModel.put(PACKAGE_DOCUMENTATION_LIST_TEMPLATE_VARIABLE, packageDocumentations);

		return new ModelAndView(INDEX_VIEW_NAME, templateModel);

	}

	public class PackageOrService {

		private RestPackageDocumentation packageDocumentation;

		private Optional<RestServiceDocumentation> serviceDocumentation = Optional.absent();

		public PackageOrService(RestPackageDocumentation packageDocumentation, Optional<RestServiceDocumentation> serviceDocumentation) {
			super();
			this.packageDocumentation = packageDocumentation;
			this.serviceDocumentation = serviceDocumentation;
		}

		public RestPackageDocumentation getPackage() {
			return packageDocumentation;
		}

		public Optional<RestServiceDocumentation> getService() {
			return serviceDocumentation;
		}
	}

	public PackageOrService getPackageOrService(String path) {

		Optional<RestPackageDocumentation> packageDocumentation = Iterables.tryFind(packageDocumentations, new PackageNamePredicate(path));

		Optional<RestServiceDocumentation> serviceDocumentation = Optional.absent();

		if (!packageDocumentation.isPresent()) {

			String packagePath = path.substring(0, path.lastIndexOf("."));

			packageDocumentation = Iterables.tryFind(packageDocumentations, new PackageNamePredicate(packagePath));
			serviceDocumentation = Iterables.tryFind(packageDocumentation.get().getServices(), new ServiceNamePredicate(path));
		}

		return new PackageOrService(packageDocumentation.get(), serviceDocumentation);

	}

	@RequestMapping("/{path}")
	public ModelAndView packageIndex(@PathVariable String path) {

		PackageOrService packageOrService = getPackageOrService(path);

		List<BreadCrumb> breadCrumbs = new ArrayList<BreadCrumb>();
		breadCrumbs.add(BREADCRUMB);

		breadCrumbs.add(new BreadCrumb(REST_DOCUMENTATION_BASE_PATH + "/" + packageOrService.getPackage().getPackageName(), packageOrService.getPackage().getPackageName()));

		if (packageOrService.getService().isPresent()) {
			breadCrumbs.add(new BreadCrumb(REST_DOCUMENTATION_BASE_PATH + "/" + packageOrService.getService().get().getClassName(), packageOrService.getService().get().getServiceName()));
		}

		Map<String, Object> templateModel = documentationService.getDefaultTemplateModel(breadCrumbs.toArray(new BreadCrumb[0]));

		String view = null;
		if (packageOrService.getService().isPresent()) {
			templateModel.put(SERVICE_DOCUMENTATION_TEMPLATE_VARIABLE, packageOrService.getService().get());
			view = SERVICE_VIEW_NAME;
		} else {
			templateModel.put(PACKAGE_DOCUMENTATION_TEMPLATE_VARIABLE, packageOrService.getPackage());
			view = PACKAGE_VIEW_NAME;
		}

		return new ModelAndView(view, templateModel);

	}

	public List<RestPackageDocumentation> getPackageDocumentation() {
		return packageDocumentations;
	}

}
