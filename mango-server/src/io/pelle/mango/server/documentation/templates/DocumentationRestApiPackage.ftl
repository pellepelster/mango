<h3>Package <a href="${packageDocumentation.packageName}">${packageDocumentation.packageName}</a></h3>

<#if packageDocumentation.description??>
<p>${packageDocumentation.description}</p>
</#if>

<#list packageDocumentation.services as service>
	<#include "DocumentationRestApiService.ftl">
</#list>
