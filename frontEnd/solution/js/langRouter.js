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
	scope.notes = new Array();
	scope.noteSelectedId = -1;
	scope.noteTitle = '';
	scope.noteContent = '';
	
	
	scope.refreshUINotes = function(){
	  scope.notes.splice(0, scope.notes.length);
	  scope.notes = Notes.getAll();
	}
	
	scope.removeNote = function() {
	  if(scope.noteSelectedId != -1){
	    var found = $filter('filter')(scope.notes, {id: scope.noteSelectedId}, true);

	  if (found.length) {
	    Note.remove({itemId: found[0].id});
	    var i = scope.notes.indexOf(found[0]);
	    scope.notes.splice(i, 1);
	    scope.noteSelectedId = -1;
	      }
	  }
	}
	
	scope.saveUserInput = function(){
	  var $ = jQuery;
	    var jTit = $('input.noteTitle');
	    var jCont = $('textarea.noteContent');
	    
	  scope.noteTitle = jTit.attr('value');
	  scope.noteContent = jCont.attr('value');
	}
	
	scope.applyUpdate = function(updated){
	  updated.title = scope.noteTitle;
	  updated.value = scope.noteContent;
	  scope.getElementByID(updated.id).innerHTML=updated.title;
	}
	
	scope.updateNote = function() {
	  if(scope.noteSelectedId != -1){
	    scope.saveUserInput();
	   var found = $filter('filter')(scope.notes, {id: scope.noteSelectedId}, true);
	   if (found.length) {
	    Note.update({itemId: found[0].id}, found[0]);
	    scope.applyUpdate(found);
		}
	   }
	}

	scope.addNote = function() {
	  scope.saveUserInput();
	  var newNote = Notes.post({
	    title: scope.noteTitle,
	  value: scope.noteContent
	  }, function() {
	    scope.notes.push(newNote.data);
	    });
	  }
	  
	  scope.selectItem = function(elem){
	    var $ = jQuery;
	    var jElem = $(elem);
	    jElem.closest('a').find('.active').removeClass('active');
	    jElem.addClass('active');
	    scope.noteSelectedId = elem.value;
	  }
	  
	  scope.displayNote = function(note){
	    scope.noteTitle = note.title;
	    scope.noteContent = note.value;
	    scope.noteSelectedId = note.id;
	  }
	scope.getNoteInfo = function(){
	  if(scope.noteSelectedId != -1){
	    var note = Note.get({itemId: scope.noteSelectedId});
	    scope.displayNote(note.data);
	  }
	  
	}
	scope.refreshUINotes();
}]);
