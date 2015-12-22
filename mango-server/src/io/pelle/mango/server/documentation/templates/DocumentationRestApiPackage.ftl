<h3>Package <a href="${packageDocumentation.packageName}">${packageDocumentation.packageName}</a></h3>

<#list packageDocumentation.serviceDocumentations as serviceDocumentation>
	<#include "DocumentationRestApiService.ftl">
</#list>
