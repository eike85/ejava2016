/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    var socket;
    $( "#connect" ).on("click", function(){
       if ( !socket ) {
           socket = new WebSocket("ws://localhost:8080/week04_ca/board");
           socket.onopen = function() {
               console.log("Connected");
           };
           socket.onclose =  function() {
            console.log("Disconnected");
            socket = null;
           };
           socket.onmessage = function(evt) {
               var json = JSON.parse(evt.data);
               console.log(json);
           };
       } else {
           alert ("Socket is null! Closing...");
           socket.close();
           socket = null;
       }
    });
});

