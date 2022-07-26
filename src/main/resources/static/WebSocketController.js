class WebSocketController {
	
	constructor() {
		this._onConnected = this._onConnected.bind(this);
	}
	
	_onConnected(frame) {
        this.setConnected(true);
        console.log('Connected: ' + frame);
        this.stompClient.subscribe('/topic/greetings', this.showMessage);
	}
	
	connect() {
	    //var socket = new SockJS('/websocket-app');
	    var socket = new SockJS('/greeting-app');
	    this.stompClient = Stomp.over(socket);  
	    this.stompClient.connect({}, this._onConnected);
	}

	sendMessage() {
	    var message = document.getElementById('text').value;
	    this.stompClient.send("/app/say-hi", {}, message);		
	}
	
	showMessage(message) {
	    var response = document.getElementById('response');
	    var p = document.createElement('p');
	    p.style.wordWrap = 'break-word';
	    p.appendChild(document.createTextNode(message.body));
	    response.appendChild(p);
	}
	
	setConnected(connected) {
	    document.getElementById('connect').disabled = connected;
	    document.getElementById('disconnect').disabled = !connected;
	    document.getElementById('mural').style.visibility = connected ? 'visible' : 'hidden';
	    document.getElementById('response').innerHTML = '';		
	}
	
	disconnect() {
	    if(this.stompClient != null) {
	        this.stompClient.disconnect();
	    }
	    this.setConnected(false);
	    console.log("Disconnected");
	}
	
}
var webSocket = new WebSocketController();
