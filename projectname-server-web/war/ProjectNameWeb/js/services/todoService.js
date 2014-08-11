var todoServices = angular.module('todoServices', ['ngResource']);

todoServices.factory('Todos', ['$resource',
	function($resource){
		return $resource('../remote/todos/:action', {}, {
			getAll: {
                params: { 'action': 'all' }
            },
			add: {
                params: { 'action': 'all' }
            },	
		});
}]);