<#include "DocumentationPrepend.ftl">

<div class="container">
	<h1>Rest API</h1>
	
	<#list packageDocumentations as packageDocumentation>
		<#include "DocumentationRestApiPackage.ftl">
	</#list>
	
	
</div>

<#include "DocumentationPostpend.ftl">