<!DOCTYPE html>
<html lang="en">

<#setting url_escaping_charset='UTF-8'>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>${navigationModel.title}</title>

	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet" />
	<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet'  type='text/css' />

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

	<style type="text/css">
	body {
    	font-family: 'Open Sans', sans-serif;
	}
	
	.table td.fit, 
	.table th.fit {
		white-space: nowrap;
		width: 1%;
	}
	
	.http-method {
	    padding: 0.5em;
		display: inline-block;
		width: 3.5em;
		text-align: center;
	}

	.http-method-POST {
    	background-color: #10a54a;
	}

	.http-method-GET {
    	background-color: #0f6ab4;
	}

	.tab-pane {
    	background-color: #fff;
    	padding: 1em;
	}
	
	.method-container {
		margin-bottom: 1em;
	}
	
	.method-container pre {
		border-radius: 0px;
	}
	
	.method-panel {
		padding: 1em;
	}
	
	.method-container-GET {
		background-color: #e7f0f7;
		border: 1px solid #c3d9ec;
	}
	
	.method-container-POST {
		background-color: #e7f6ec;
		border: 1px solid #c3e8d1;
	}
	
	
	.method-container {
		padding-left: 0px;
	}
	
	.documentation-service-container>.row {
		margin-left: 0px;
	}
	
	.documentation-service-container {
		margin-bottom: 3em;
	}
	
	
	</style>
	
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>

<body>

<#include "DocumentationNavigation.ftl">