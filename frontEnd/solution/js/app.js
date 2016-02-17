'use strict';

var serverLoc='http://private-9aad-note10.apiary-mock.com:80/';

angular.module('mLApp', [
  'ngRoute',
  'mLApp.languageRoute',
  'mLApp.noteRestServices',
  'mLApp.notesRestServices'
])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/readme'});
}]);
