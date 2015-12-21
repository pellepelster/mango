<#include "DocumentationPrepend.ftl">

<div class="container">
	<h1>Rest API</h1>
	
	
	<#list restDocumentations as restDocumentation>
	${restDocumentation.className}<br>
	</#list>
	
	
</div>

<#include "DocumentationPostpend.ftl">