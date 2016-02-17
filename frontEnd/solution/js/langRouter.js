'use strict';

angular.module('mLApp.languageRoute', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
  .when('/sk', {
    templateUrl: 'sk/sk.html',
    controller: 'langCtrl'
  })
  .when('/en', {
    templateUrl: 'en/en.html',
    controller: 'langCtrl'
  })
  .when('/readme', {
    templateUrl: 'readme/readme.html'
  });
}])

.controller('langCtrl', ['$scope', 'Notes','Note',  function(scope, Notes, Note) {
	scope.noteSelectedId = -1;
	scope.noteTitle = '';
	scope.noteContent = '';
	
	scope.refreshUINotes = function(){
	  scope.notes = Notes.getAll();
	}
	
	scope.removeNote = function() {
	  if(scope.noteSelectedId != -1){
	    var found = $filter('filter')(scope.notes, {id: scope.noteSelectedId}, true);

	  if (found.length) {
	    Note.remove({itemId: found[0].id});
	    var i = scope.notes.indexOf(found[0]);
	    scope.notes.splice(i, 1);
	      }
	  }
	}

	scope.updateNote = function() {
	  if(scope.noteSelectedId != -1){
	   var found = $filter('filter')(scope.notes, {id: scope.noteSelectedId}, true);
	   if (found.length) {
	    Note.update({itemId: found[0].id}, found[0]); 
		}
	   }
	}

	scope.addNote = function() {
	  var newNote = Notes.post({
	    title: scope.noteTitle,
	  value: scope.noteContent
	  }, function() {
	    scope.notes.push(newNote.data);
	    });
	  }
	scope.getNoteInfo = function(){
	  if(scope.noteSelectedId != -1){
	    var note = Note.get({itemId: scope.noteSelectedId});
	    scope.displayNote(note.data);
	  }
	  
	}
	scope.displayNote = function(note){
	  //todo
	  }
	scope.refreshUINotes();
}]);
