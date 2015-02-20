var todoServices = angular.module('todoServices', ['ngResource']);

todoServices.factory('TodoServices', ['$resource',
	function($resource){
		return $resource('../remote/projectnameservice/:action', {}, {
			getAll: {
				method: 'POST',
				params: { 'action': 'getall' },
				isArray: true
            },
			save: {
				method: 'POST',
				params: { 'action': 'save' }
            },	
		});
}]);