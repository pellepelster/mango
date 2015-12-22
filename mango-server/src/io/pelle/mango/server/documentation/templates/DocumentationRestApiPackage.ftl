<h3>Package <a href="${packageDocumentation.packageName}">${packageDocumentation.packageName}</a></h3>

<#if packageDocumentation.description??>
<p>${packageDocumentation.description}</p>
</#if>

<#list packageDocumentation.serviceDocumentations as serviceDocumentation>
	<#include "DocumentationRestApiService.ftl">
</#list>
