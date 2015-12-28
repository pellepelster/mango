<#macro restBody body indent>
<#list body.attributes>
${indent}<strong>{</strong>
<#items as attribute>
${indent}${indentDelimiter}<strong>${attribute.name}</strong> (<#if attribute.type.primitive>${attribute.type.typeName})<#else><#assign id><@uuid/></#assign><a class="" data-toggle="collapse" href="#${id}" aria-expanded="false" aria-controls="${id}">${attribute.type.typeName}</a>)<div class="collapse" id="${id}"><@restBody attribute.type indent + indentDelimiter /></div></#if>
</#items>
${indent}<strong>}</strong>
</#list>
</#macro>


<#assign serviceUUID><@uuid/></#assign>
<h3><a class="" data-toggle="collapse" href="#${serviceUUID}" aria-expanded="false" aria-controls="${serviceUUID}">${service.serviceName}</a></h3>

<!-- service ${service.serviceName} -->
<div class="collapse documentation-service-container" id="${serviceUUID}">

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
				<div class="collapse" id="${methodUUID}">

					<!-- response model -->					
					<div>
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#${methodUUID}-request-model" aria-controls="${methodUUID}-request-model" role="tab" data-toggle="tab">Request Model</a></li>
						</ul>
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="${methodUUID}-request-model">

								<#list method.attributes as attribute>
									<#if attribute.type??>
									<pre><@restBody attribute.type ""/></pre>
									</#if>
								</#list>
							</div>
						</div>
					</div>					

					<!-- response model -->					
					<div>
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#${methodUUID}-model" aria-controls="${methodUUID}-model" role="tab" data-toggle="tab">Response Model</a></li>
						</ul>
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="${methodUUID}-model">
								<#assign indentDelimiter = "	">
								<#if method.returnType.primitive>
								<strong>Response:</strong> <code>${method.returnType.typeName}</code><br/>
								<#else>
								<pre><@restBody method.returnType ""/></pre>
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
