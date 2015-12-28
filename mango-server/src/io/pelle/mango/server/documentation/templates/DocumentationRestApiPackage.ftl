<#assign packageUUID><@uuid/></#assign>
<h3>
	<a data-toggle="collapse" href="#${packageUUID}" aria-expanded="false" aria-controls="${packageUUID}">${packageDocumentation.packageName}</a>
	<#if packageDocumentation.shortDescription??><small>${packageDocumentation.shortDescription}</small></#if>
</h3>

<div class="collapse documentation-service-container" id="${packageUUID}">

	<#if packageDocumentation.description??>
	<p>${packageDocumentation.description}</p>
	</#if>
	
	<#list packageDocumentation.services as service>
		<#include "DocumentationRestApiService.ftl">
	</#list>

</div>