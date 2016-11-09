/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    var socket;
    //$( "#connect" ).on("click", function(){
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

                var tr;
                for (var i = 0; i < json.length; i++) {
                    tr = $('<tr/>');
                    tr.append("<td>" + json[i].userid + "</td>");
                    tr.append("<td>" + json[i].title + "</td>");
                    tr.append("<td>" + json[i].content + "</td>");
                    tr.append("<td>" + json[i].posteddate + "</td>");
                    tr.append("<td>" + json[i].noteid + "</td>");
                    $('table').append(tr);
                }
           };
       }
//       else {
//           alert ("Socket is null! Closing...");
//           socket.close();
//           socket = null;
//       }
    //});
    
    $( "#all" ).on("click", function(){
        socket.send("All");
        console.log("Sending all");
    });
   
});


