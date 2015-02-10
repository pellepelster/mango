<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${entityLabel} REST API</title>

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
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="container">

        <h1>${entityLabel} REST API</h1>
        <p>Provides access to ${entityLabel} returning JSON.</p>

		<h2>Get ${entityLabel} by Id</h2>
        <p>Returns the ${entityLabel} with the id given by the path variable <code>{entityId}</code></p>

		<pre>${baseUrl}/rest/byid/{entityId}</pre>
        
		<div class="panel panel-info" id="panel1">
		
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" id="byIdTryTitle" aria-expanded="false" data-target="#collapseOne" href="#collapseOne">Try get ${entityLabel} by Id</a>
				</h4>
			</div>
			<div id="collapseOne" class="panel-collapse collapse">
				<div class="panel-body">
					<form class="form-inline">
						<div class="form-group">
							<label for="byIdInput">${baseUrl}/rest/byid/</label>
							<input type="text" class="form-control" id="byIdInput" placeholder="Id">
						</div>
						<button type="button" id="byIdSubmit" class="btn btn-default">GET</button>
					</form>
					
					<textarea id="byIdResponse" cols="40" rows="10"></textarea>
				</div>
			</div>
		</div>
        
		<script type="text/javascript">
			$(document).ready(function() {
			
				var byIdEditor = CodeMirror.fromTextArea(document.getElementById('byIdResponse'), {
					lineNumbers: true,
					mode: "javascript",
					readOnly: true,
					autofocus: true
				});

				$('#byIdTryTitle').click(function(){
					setTimeout(function() { byIdEditor.refresh(); }, 0)
				});

				$( "#byIdSubmit" ).click(function() {
				    $.ajax({
				        url: "${baseUrl}/rest/byid/" + $('#byIdInput').val()
				    }).then(function(data) {
				    	byIdEditor.getDoc().setValue(JSON.stringify(data, null, '\t'));
				    }).fail(function(jqXHR, data) {
				    	byIdEditor.getDoc().setValue(data);
					});
				});

			});		
		</script>

		<h2>Get ${entityLabel} by natural key</h2>
		<pre>${baseUrl}/rest/bynaturalkey/{naturalKey}</pre>

		<h2>Get ${entityLabel} by custom query</h2>
		<pre>${baseUrl}/rest/query?query={query}</pre>

    </div>

  </body>
</html>
