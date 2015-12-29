<#macro restBody body indent>
<#list body.attributes>
${indent}<strong>{</strong>
<#items as attribute>
${indent}${indentDelimiter}<strong>${attribute.name}</strong> (<#if attribute.type.primitive>${attribute.type.typeName})<#else><#assign id><@uuid/></#assign><a class="" data-toggle="collapse" href="#${id}" aria-expanded="false" aria-controls="${id}">${attribute.type.typeName}</a>)<div class="collapse" id="${id}"><@restBody attribute.type indent + indentDelimiter /></div></#if>
</#items>
${indent}<strong>}</strong>
</#list>
</#macro>

<#macro attributesTable attributes>

	<#list attributes>

		<table class="table">
			<thead>
				<tr>
					<th class="fit">Attribute</th>
					<th>Type</th>
				</tr>
			</thead>
			<tbody>
			
			<#items as attribute>
				<tr>
					<td class="fit">${attribute.name!"-"}</td>
					<td>
						<#if attribute.type.primitive>
							<code>${attribute.type.typeName}</code><br/>
						<#else>
							<pre><@restBody attribute.type ""/></pre>
						</#if>
					</td>
				</tr>
	 		</#items>
	 			
			</tbody>
		</table>
	
	</#list>

</#macro>


<#assign serviceUUID><@uuid/></#assign>
<h3>
	<a class="" data-toggle="collapse" href="#${serviceUUID}" aria-expanded="false" aria-controls="${serviceUUID}">${service.serviceName}</a>
	<#if service.shortDescription??>
	<small>${service.shortDescription}</small>
	</#if>
</h3>
	
<!-- service ${service.serviceName} -->
<div class="collapse documentation-service-container" id="${serviceUUID}">

	<#if service.description??>
	<p>${service.description}</p>
	</#if>

	<#assign lastMethodName = "">
	<#list service.methods as method>
	
		<#assign methodUUID><@uuid/></#assign>
		<#assign methodName = "${method.paths?first}">
		<#assign methodHttpMethod = "${method.httpMethods?first}">
		
		<div class="row">
			<div class="col-md-12 method-container method-container-${methodHttpMethod}">
				<span class="http-method http-method-${methodHttpMethod}">${methodHttpMethod}</span>
				<a class="" data-toggle="collapse" href="#${methodUUID}" aria-expanded="false" aria-controls="${serviceUUID}">${service.paths?first}/${method.paths?first}</a>
				
				<!-- method ${methodName} -->
				<div class="collapse method-panel" id="${methodUUID}">

					<h4>Request</h4>
					<!-- request model -->					
					<div>
					
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#${methodUUID}-request-model" aria-controls="${methodUUID}-request-model" role="tab" data-toggle="tab">Model</a></li>
						</ul>
						
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="${methodUUID}-request-model">

								<#if method.attributes?size == 0>
								none
								<#else>
									<#if methodHttpMethod == "POST">
										<#if method.attributes?size == 1 && !method.attributes[0].name??>
											<pre><@restBody method.attributes[0].type ""/></pre>
										<#else>
											<@attributesTable method.attributes/>
										</#if>
									<#else>
										<@attributesTable method.attributes/>
									</#if>
								</#if>

							</div>
						</div>
					</div>					

					<!-- response model -->					
					<h4>Response</h4>
					<div>
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#${methodUUID}-response-model" aria-controls="${methodUUID}-response-model" role="tab" data-toggle="tab">Model</a></li>
						</ul>
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="${methodUUID}-response-model">
								<#assign indentDelimiter = "	">
								<#if method.response.type.typeName == "void">
								none
								<#elseif method.response.type.primitive>
								
								<table class="table">
									<thead>
										<tr>
											<th class="fit">Type</th>
											<th>Description</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="fit"><code>${method.response.type.typeName}</code></td>
											<td>${method.response.description!"-"}</td>
										</tr>
									</tbody>
								</table>
								
								<#else>
								<pre><@restBody method.response.type ""/></pre>
								</#if>
							</div>
						</div>
					</div>					

				<!-- /method ${methodName} -->
				</div>
			</div>
		</div>
		
	</#list>

<!-- /service ${service.serviceName} -->
</div>
