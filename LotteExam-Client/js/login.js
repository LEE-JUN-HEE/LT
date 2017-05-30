var button = $('#complete');
var botchat1 = '<li id = "bot"><label id = "bottext">';
var botchat2 = '</label></li>';
var mychat1 = '<li id = "my"><label id = "mytext">';
var mychat2 = '</label></li>';
var stompClient = null;
// button.on('click', function(data){
//     if()
// });

button.on('click', function () {
    if ($('#age').val() != '' && $('#gender').val() != '') {
        $.ajax({
            type: "POST",
            url: "http://localhost/init",
            contentType: 'application/json',
            data: JSON.stringify(
                {
                    age: $('#age').val(),
                    gender: $('#gender').val()
                }),
            success: function (data) {
                chatstart(data)
            },
        });
    } else {
        alert("fill every input");
    }
});

function chatstart(data) {
    var list = $('#chat');
    $('login').hide();

    addbotchat(data);
    //connect();
    $('body').append('<input id = chatinput></input>');
    $(document).on('change', '#chatinput', function () {
        if ($('#chatinput').val() != '') {
            addmychat($('#chatinput').val());
            //stompClient.send("/chat/bot", {}, JSON.stringify({'msg' : $('#chatinput').val(), 'id' : $('tempid').text()}));
            $.ajax({
                type: "POST",
                url: "http://localhost/mysend",
                contentType: "application/json",
                data: JSON.stringify({
                    age: $('#age').val(),
                    gender: $('#gender').val(),
                    msg: $('#chatinput').val(),
                }),
                success: function (data) {
                    $('#chatinput').empty();// 비우기
                    addbotchat(data);
                }
            })
        }
    });
}

function addmychat(data) {
    var list = $('#chat');

    var html = mychat1;
    html += data;
    html += mychat2;
    list.append(html);
}

function addbotchat(data) {
    var list = $('#chat');

    var html = botchat1;
    html += data;
    html += botchat2;
    list.append(html);
}

// function connect() {
//     var socket = new SockJS('/Connect');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function (frame) {
//         console.log('Connected: ' + frame);
//         var id = stompClient.subscribe('/chatbot/lotte', function (data) {
//             addbotchat(data.body);
//         });
//         $('body').append("<tempid>"+id.id+"<tempid>");
//         $('tempid').hide();
//     });
// }

// function disconnect() {
//     if (stompClient != null) {
//         stompClient.disconnect();
//     }
//     setConnected(false);
//     console.log("Disconnected");
// }