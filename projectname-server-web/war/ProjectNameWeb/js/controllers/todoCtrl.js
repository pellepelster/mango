angular.module('todomvc')
	.controller('TodoCtrl', function TodoCtrl($scope, TodoServices, $routeParams, $filter, $resource) {
		'use strict';

		$scope.todos = [];
		
		$scope.updateTodos = function () {
			TodoServices.getAll({}).$promise.then(function(data) {  
				$scope.todos = data;
			});
		};
		
		$scope.updateTodos();

				
		$scope.newTodo = '';
		$scope.editedTodo = null;

		$scope.$watch('todos', function (newValue, oldValue) {
			
			$scope.remainingCount = $filter('filter') ($scope.todos, { completed: false }).length;
			$scope.completedCount = $scope.todos.length - $scope.remainingCount;
			$scope.allChecked = !$scope.remainingCount;
			
			newValue.forEach(function (todo, id) {
				if (oldValue[id] && [id].completed != oldValue[id].completed) {					
					TodoServices.save(todo).$promise.then(function(data) {  
						// $scope.updateTodos() 
					});
				}
			});
		}, true);

		// Monitor the current route for changes and adjust the filter accordingly.
		$scope.$on('$routeChangeSuccess', function () {
			var status = $scope.status = $routeParams.status || '';

			$scope.statusFilter = (status === 'active') ?
				{ completed: false } : (status === 'completed') ?
				{ completed: true } : null;
		});

		$scope.addTodo = function () {
			var newTodo = $scope.newTodo.trim();
			
			if (!newTodo.length) {
				return;
			}
			
			var todo = {};
			todo.title = newTodo;
			
			TodoServices.save(todo).$promise.then(function(data) {  
				$scope.updateTodos() 
			});

			$scope.newTodo = '';
		};

		$scope.editTodo = function (todo) {
			$scope.editedTodo = todo;
			// Clone the original todo to restore it on demand.
			$scope.originalTodo = angular.extend({}, todo);
		};

		$scope.doneEditing = function (todo) {
			$scope.editedTodo = null;
			todo.title = todo.title.trim();

			if (!todo.title) {
				$scope.removeTodo(todo);
			}
			else {
				TodoServices.save(todo).$promise.then(function(data) {  
					$scope.updateTodos() 
				});
			}
		};

		$scope.revertEditing = function (todo) {
			$scope.todos[$scope.todos.indexOf(todo)] = $scope.originalTodo;
			$scope.doneEditing($scope.originalTodo);
		};

		$scope.removeTodo = function (todo) {
			Todo.delete({ todoId: todo.id }, function(data) {
				$scope.updateTodos();
			});
		};

		$scope.toggleCompleted = function (todo) {
			
			TodoServices.save(todo).$promise.then(function(data) {  
				$scope.updateTodos() 
			});
		};
		
		$scope.clearCompletedTodos = function () {

			$scope.todos.forEach(function (todo) {
				if (todo.completed) {
					$scope.removeTodo(todo);
				}
			});

			$scope.todos = todos = todos.filter(function (val) {
				return !val.completed;
			});
		};

		$scope.markAll = function (completed) {
			$scope.todos.forEach(function (todo) {
				todo.completed = !completed;
			});
		};
	});
