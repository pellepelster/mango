<#assign path = springMacroRequestContext.getRequestUri()?keep_after(navigationModel.baseUrl)>
<#assign levelCount = path?split("/")?size-2>

<#assign basePath = "">
<#if levelCount == 0>
	<#assign basePath = "./">
<#else>
	<#list 1..levelCount as level>
	<#assign basePath += "../">
	</#list>
</#if>

<nav class="navbar navbar-default navbar-static-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${basePath}index">Home</a>
		</div>
		
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<#list navigationModel.primaryNavigation as navigation>
				<li><a href="${basePath}${navigation.path}">${navigation.name}</a></li>
				</#list>
				<!--
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li role="separator" class="divider"></li>
						<li class="dropdown-header">Nav header</li>
						<li><a href="#">Separated link</a></li>
						<li><a href="#">One more separated link</a></li>
					</ul>
				</li>
				-->
			</ul>
		</div>
	</div>
</nav>

<ul class="breadcrumb">
	<#list navigationModel.breadCrumbs as breadcrumb>
	<li><a  class="active" href="${basePath}${breadcrumb.path}">${breadcrumb.name}</a></li>
	</#list>
</ul>
