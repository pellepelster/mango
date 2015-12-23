<h3>Service <a href="${serviceDocumentation.className}">${serviceDocumentation.serviceName}</a></h3>

<strong>Base URL:</strong><code>${serviceDocumentation.paths?first}</code><br/>

<#assign lastMethodName = "">

<#list serviceDocumentation.methodDocumentations as methodDocumentation>
<#assign methodName = "${methodDocumentation.paths?first}">
<#if lastMethodName != methodName>
<h4>Method: ${methodName}</h4>
</#if>
<#assign lastMethodName = methodName>

<strong>URL:</strong> <code>${serviceDocumentation.paths?first}/${methodDocumentation.paths?first}</code><br/> 

<#if methodDocumentation.response.primitive>
<strong>Response:</strong> <code>${methodDocumentation.response.primitiveName}</code><br/>
<#else>
	<#list methodDocumentation.response.attributes>
	<div class="panel panel-default">
		<div class="panel-heading"><strong>Response</strong></div>
		<table class="table">
			<thead>
				<tr>
					<th>Attribute</th>
					<th>Type</th>
				</tr>
			</thead>
			<tbody>
	    	<#items as attribute>
				<tr>
					<th>${attribute.name}</th>
					<td>
					-
<!--
<#assign id = serviceDocumentation.className?replace(".", "") + methodName + attribute.name>
<a class="btn btn-primary" role="button" data-toggle="collapse" href="#${id}" aria-expanded="false" aria-controls="${id}">Link with href</a>
<div class="collapse" id="${id}">
<div class="well">
Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident.
</div>
</div>
-->				
					</td>
				</tr>
		    </#items>
			</tbody>
		</table>
	</div>
	<#else>
		<strong>Response:</strong> none<br/>
	</#list>
	
</#if>
<br/>
</#list>
