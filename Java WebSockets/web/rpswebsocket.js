var websocket = new WebSocket("ws://192.168.0.7:80/rpsapp/rpsapp");
websocket.onmessage = onMessage;
window.onload = onLoad;
window.onbeforeunload = close;
window.onclose = close;

var cmd = "";
var userName  = ""; 
var tosend="";
 
function clickEvent(objButton) {
    var cmdButton = objButton.value;
    if (cmdButton.localeCompare("login") === 0) {
        login();
    }
    
    if (cmdButton.localeCompare("Click to Participate") === 0) {
        requestToPlay();
    }
    
    if (cmdButton.localeCompare("submit") === 0) {
        submitOption();
    }
}
function login() {
    cmd = "login";
    userName = document.getElementById("txtStatus").value;
    tosend = cmd + "," + userName ;
    if (!userName.match(/\S/)) {
        alert("Enter Name");
        return false;
    } 
    else {
        sendToServer(tosend);
    }
}

function sendToServer(tosend) {
    websocket.send(tosend);
}

function onMessage(event) {
    var temp = [];
    temp = event.data.split(",");
    var cmde = temp[0];
    
    if (cmde.localeCompare("newUserAdded") === 0) {
        updateUserList(temp);
        
    } else if (cmde.localeCompare("logedin") === 0) {
        document.getElementById("btnSend").value = "submit";
        document.getElementById("btnSend").disabled = true;
        document.getElementById("txtStatus").value = "";
        document.getElementById("status").innerHTML = "";
        document.getElementById("requestToPlay").disabled = false;
        document.getElementById("name").innerHTML = "Your Name: " + temp[1];
        document.getElementById("listofusers").innerHTML = "You are now Logged in" +
                "<br/>" + "Click Button, Click To Participate";
    } else if (cmde.localeCompare("removeUser") === 0) {
        updateUserList(temp);
    } else if (cmde.localeCompare("score") === 0) {
        document.getElementById("btnSend").value = "submit";
        document.getElementById("btnSend").disabled = false;
        document.getElementById("listofusers").innerHTML = "";
        document.getElementById("listofusers").innerHTML += "Score <br/>";
        document.getElementById("listofusers").innerHTML += temp[1];
    }
    else {
        document.getElementById("listofusers").innerHTML = event.data;
    }
}

function close() {
    cmd = "close,"
    tosend = cmd + userName;
    document.getElementById("btnSend").value = "login";
    document.getElementById("txtStatus").value = "";
    document.getElementById("requestToPlay").disabled = true;
    sendToServer(tosend);
}

function updateUserList(temp) {
    var list = document.getElementById("list");
    for (var i = 0; i < list.options.length; i++) {
        list.options[i].innerHTML = "";
    }
    
    for (var i = 1; i < temp.length; i++) {
        document.getElementById("p"+i).innerHTML = temp[i];
    }   
}

function requestToPlay() {
    cmd="requestToPlay,"+userName +",";
    
    tosend = cmd;
    var list = document.getElementById("list");
    for (var i = 0; i < list.options.length; i++) {
        if (list.options[i].innerHTML != "" && list.options[i].selected === true) {
            tosend += list.options[i].innerHTML +",";
        }
    }
    tosend = tosend.slice(0,-1);
    document.getElementById("listofusers").innerHTML = "Request to Play sent!";
    document.getElementById("requestToPlay").disabled = true;
    document.getElementById("btnSend").disabled = false;
    document.getElementById("status").innerHTML = "Enter R, P, S";
    sendToServer(tosend);
}

function submitOption() {
    cmd="option," +userName +",";
    tosend = cmd;
    var op = document.getElementById("txtStatus").value;
    tosend += op;
    if ( ( !op.match(/\S/)) || (op.length > 1) ) {
        alert("Enter R, P, or S");
        return false;
    } 
    else {
        document.getElementById("btnSend").value = "wait...";
        document.getElementById("btnSend").disabled = true;
        sendToServer(tosend);
    }
}

function onLoad() {
    document.getElementById("listofusers").innerHTML = "You are not Logged in";
}


/**
 *  for (var i = 0; i < list.options.length; i++ ) {
        opt = list.options[i];
        if ( opt.selected === true ) {
            tosend += opt.value +",";
            //optList.push(opt.value +",") ;
        }
    }
    tosend = tosend.slice(0,-1);
 */
