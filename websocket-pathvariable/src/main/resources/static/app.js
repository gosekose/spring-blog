var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');

    var gameId = $("#channel").val();

    var headers = {
        "gameId": gameId,
        'Authorization': 'Bearer my-token',
        'userId': 'testUserId'
    };

    stompClient = Stomp.over(socket);
    stompClient.heartbeat.outgoing = 0;  // disable outgoing heartbeats
    stompClient.heartbeat.incoming = 0;
    stompClient.connect(headers, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings/' + gameId, function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
            console.log(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {

    var gameId = $("#channel").val();

    var headers = {
        'Authorization': 'Bearer my-token',
        'userId': 'testUserId'
    };

    stompClient.send("/app/hello/" + gameId, headers, JSON.stringify({
        "gameId": gameId,
        'name': $("#name").val()
    }));
    console.log(JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});