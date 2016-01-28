<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>${entityLabel} API</title>

	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/4.12.0/codemirror.css" rel="stylesheet">

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/4.12.0/codemirror.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/4.12.0/mode/javascript/javascript.min.js"></script>

	<style type="text/css">
		.panel-heading a:after {
			font-family:'Glyphicons Halflings';
			content:"\e114";
			float: right;
			color: grey;
		}
		.panel-heading a.collapsed:after {
			content:"\e080";
		}
	</style>
	
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>

<body>
    <div class="container">

		<h1>${entityLabel} API</h1>
		<p>Provides JSON access to ${entityLabel}.</p>

		<h2>Get ${entityLabel} by id</h2>
        <p>Returns the ${entityLabel} with the id given by the path variable <code>{entityId}</code>. If no entity is found an HTTP 404 is returned.</p>

		<pre>${baseUrl}/byid/{entityId}</pre>
        
		<div class="panel panel-info">
		
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" id="byIdTryTitle" aria-expanded="false" data-target="#byIdCollapse" href="#byIdCollapse">Try get ${entityLabel} by id</a>
				</h4>
			</div>
			
			<div id="byIdCollapse" class="panel-collapse collapse">
				<div class="panel-body">
					<form class="form-inline">
						<div class="form-group">
							<label for="byIdInput">${baseUrl}/byid/</label>
							<input type="text" class="form-control" id="byIdInput" placeholder="Id">
						</div>
						<button type="button" id="byIdSubmit" class="btn btn-default">GET</button>
					</form>
					
					<textarea id="byIdResponse"></textarea>
				</div>
			</div>
			
			<script type="text/javascript">
			
				$(document).ready(function() {
				
					var byIdEditor = CodeMirror.fromTextArea(document.getElementById('byIdResponse'), {
						mode: "javascript",
						readOnly: true,
					});

					$('#byIdTryTitle').click(function(){
						setTimeout(function() { byIdEditor.refresh(); }, 0)
					});

					$( "#byIdSubmit" ).click(function() {
						var value = $('#byIdInput').val();
						
						if (value) {
							$.ajax({
								url: "${baseUrl}/byid/" + value
							}).then(function(data) {
								byIdEditor.getDoc().setValue(JSON.stringify(data, null, '\t'));
							}).fail(function(jqXHR, data) {
								byIdEditor.getDoc().setValue("HTTP " + jqXHR.status + ": " +  jqXHR.responseText);
							});
						} else {
							alert("Enter an id");
						}
					});
				});		
			</script>
		</div>
        
		<h2>Get ${entityLabel} by natural key</h2>
        <p>Returns the ${entityLabel} with the natural key given by the path variable <code>{naturalKey}</code>. If no entity is found an HTTP 404 is returned.</p>
		<pre>${baseUrl}/bynaturalkey/{naturalKey}</pre>

		<div class="panel panel-info">
		
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" id="byNaturalKeyTryTitle" aria-expanded="false" data-target="#byNaturalKeyCollapse" href="#byNaturalKeyCollapse">Try get ${entityLabel} by natural key</a>
				</h4>
			</div>
			
			<div id="byNaturalKeyCollapse" class="panel-collapse collapse">
				<div class="panel-body">
					<form class="form-inline">
						<div class="form-group">
							<label for="byNaturalKeyInput">${baseUrl}/bynaturalkey/</label>
							<input type="text" class="form-control" id="byNaturalKeyInput" placeholder="Natural Key">
						</div>
						<button type="button" id="byNaturalKeySubmit" class="btn btn-default">GET</button>
					</form>
					
					<textarea id="byNaturalKeyResponse"></textarea>
				</div>
			</div>
			
			<script type="text/javascript">
			
				$(document).ready(function() {
				
					var byNaturalKeyEditor = CodeMirror.fromTextArea(document.getElementById('byNaturalKeyResponse'), {
						mode: "javascript",
						readOnly: true,
					});

					$('#byNaturalKeyTryTitle').click(function(){
						setTimeout(function() { byNaturalKeyEditor.refresh(); }, 0)
					});

					$( "#byNaturalKeySubmit" ).click(function() {
						var value = $('#byNaturalKeyInput').val();
						
						if (value) {
							$.ajax({
								url: "${baseUrl}/bynaturalkey/" + value
							}).then(function(data) {
								byNaturalKeyEditor.getDoc().setValue(JSON.stringify(data, null, '\t'));
							}).fail(function(jqXHR, data) {
								byNaturalKeyEditor.getDoc().setValue("HTTP " + jqXHR.status + ": " +  jqXHR.responseText);
							});
						} else {
							alert("Enter a natural key");
						}
					});
				});		
			</script>
		</div>
		
		
		<h2>Get ${entityLabel} by custom query</h2>
		<p>Returns all ${entityLabel} matching a query that can either be given by query parameter <code>query</code> or by posting the query as body. Expressions are based on <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/expressions.html">Spring Expression Language (SpEL)</a>, see Mango documentation for detailed instructions</p>

		<p>
		<strong>Expression example</strong><br/>
		<code>attribute1 == 'string' &amp;&amp; (attribute2 == 12 || attribute3 != 'string')</code>
		</p>
		
		<pre>${baseUrl}/query?query={query}</pre>

				<div class="panel panel-info">
		
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" id="byQueryTryTitle" aria-expanded="false" data-target="#byQueryCollapse" href="#byQueryCollapse">Try get ${entityLabel} by query</a>
				</h4>
			</div>
			
			<div id="byQueryCollapse" class="panel-collapse collapse">
				<div class="panel-body">
					<form class="form-inline">
						<div class="form-group">
							<label for="byQueryInput">${baseUrl}/query/</label>
							<input type="text" class="form-control" id="byQueryInput" placeholder="Query">
						</div>
						<button type="button" id="byQuerySubmit" class="btn btn-default">GET</button>
					</form>
					
					<textarea id="byQueryResponse"></textarea>
				</div>
			</div>
			
			<script type="text/javascript">
			
				$(document).ready(function() {
				
					var byQueryEditor = CodeMirror.fromTextArea(document.getElementById('byQueryResponse'), {
						mode: "javascript",
						readOnly: true,
					});

					$('#byQueryTryTitle').click(function(){
						setTimeout(function() { byQueryEditor.refresh(); }, 0)
					});

					$( "#byQuerySubmit" ).click(function() {
						var value = $('#byQueryInput').val();
						
						if (value) {
							$.ajax({
								url: "${baseUrl}/query?query=" + value
							}).then(function(data) {
								byQueryEditor.getDoc().setValue(JSON.stringify(data, null, '\t'));
							}).fail(function(jqXHR, data) {
								byQueryEditor.getDoc().setValue("HTTP " + jqXHR.status + ": " +  jqXHR.responseText);
							});
						} else {
							alert("Enter a query");
						}
					});
				});		
			</script>
		</div>

	</div>
</body>
</html>
