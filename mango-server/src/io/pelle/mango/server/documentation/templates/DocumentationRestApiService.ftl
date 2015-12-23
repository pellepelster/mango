<#assign serviceUUID><@uuid/></#assign>
<h3><a class="" data-toggle="collapse" href="#${serviceUUID}" aria-expanded="false" aria-controls="${ serviceUUID}">${service.serviceName}</a></h3>
<div class="collapse" id="${serviceUUID}">

<#assign lastMethodName = "">

<#list service.methods as method>
<#assign methodName = "${method.paths?first}">
<#if lastMethodName != methodName>

<div class="row">
  <div class="col-md-1 bg-primary">${method.methods?first}</div>
  <div class="col-md-11 bg-info"><strong>${service.paths?first}/${method.paths?first}</strong></div>
</div>
</#if>
<#assign lastMethodName = methodName>
<#assign indentDelimiter = "	">

<#macro restBody body indent>
<#list body.attributes>
${indent}<strong>{</strong>
<#items as attribute>
${indent}${indentDelimiter}<strong>${attribute.name}</strong> (<#if attribute.type.primitive>${attribute.type.typeName})<#else><#assign id><@uuid/></#assign><a class="" data-toggle="collapse" href="#${id}" aria-expanded="false" aria-controls="${id}">${attribute.type.typeName}</a>)<div class="collapse" id="${id}"><@restBody attribute.type indent + indentDelimiter /></div></#if>
</#items>
${indent}<strong>}</strong>
</#list>
</#macro>

<#if method.returnType.primitive>
<strong>Response:</strong> <code>${method.returnType.typeName}</code><br/>
<#else>
<pre><@restBody method.returnType ""/></pre>
</#if>
<br/>
</#list>

</div>
