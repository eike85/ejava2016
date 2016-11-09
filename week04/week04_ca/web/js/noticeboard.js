/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var notes = [];
$(function() {
    var socket;
    
    var filerByCategory = function() {
        $("#location tbody").remove();
        var trHTML = '';
        $.each(notes, function (i, item) {
            var filterCatgory = $('#selectId').val();
            if ( filterCatgory === 'All' || item.category === filterCatgory ) {
              trHTML += '<tr><td>' + item.title + '</td><td>' + item.category + '</td><td>' + item.content + '<td>' + item.userid + '</td><td>' + item.postedDate + '</td>/td></tr>';
            }
        });

        $('#location').append(trHTML);
    };
    
    //var notes = [];
    //$( "#connect" ).on("click", function(){
       if ( !socket ) {
           socket = new WebSocket("ws://localhost:8080/week04_ca/board");
           socket.onopen = function() {
               console.log("Connected");
               socket.send("All");
           };
           socket.onclose =  function() {
            console.log("Disconnected");
            socket = null;
           };
           socket.onmessage = function(evt) {
                var json = JSON.parse(evt.data);
                console.log(json);
                
                for (var i = 0; i < json.length; i++) {
                    var note = {
                        "title": "",
                        "userid": "",
                        "category": "",
                        "content": "",
                        "postedDate": ""
                    };
                    note.title = json[i].title;
                    note.category = json[i].category; 
                    note.content = json[i].content; 
                    note.userid = json[i].userid; 
                    note.postedDate = json[i].postedDate; 
                    notes.push(note);
                }
                filerByCategory();
           };
       }
    
    $('#selectId').on('change', function() {
        console.log(this.value);
        filerByCategory();
    });
});