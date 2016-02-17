var noteServices = angular.module('mLApp.noteRestServices', ['ngResource']);
var notesServices = angular.module('mLApp.notesRestServices', ['ngResource']);

noteServices.factory('Note', ['$resource',
  function($resource){
    return $resource(serverLoc+'notes/:idItem', {idItem:'@id'}, {
      get: {
        method: 'GET'
      }, 
      update: {
        method: 'PUT'
      },
      remove: {
        method: 'DELETE'
      }
    });
  }]); 
  
notesServices.factory('Notes', ['$resource', 
  function($resource){
    return $resource(serverLoc+'notes', {}, {
      getAll: {
        method: 'GET',
        isArray: true
      },
      post: {
        method: 'POST'
      }
    });
  }
]);

