import { Switch, Route } from "react-router-dom";
import './App.css';
import Home from "./components/Home";
import { useState, useEffect } from "react";
const Stomp = require('stompjs');
const SockJS = require('sockjs-client');

let stompClient = null;
let socket = null;

function App() {

  const [connectionState, setConnectionState] = useState(false);
  const [sessionId, setsessionId] = useState(null);

  const [receivedMessage, setreceivedMessage] = useState("");

  useEffect(() => {
    socket = new SockJS('http://localhost:8080/chat')
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
  }, [])

  function onError(error) {
    console.log("something wrong: " + error);
    setConnectionState(false);
  }

  function onConnected() {
    console.log("connected");
    setConnectionState(true);
    var sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
    console.log("session url: " + sessionId);
    setsessionId(sessionId);

    stompClient.subscribe('/topic/pubic', onPublicMessageReceived);
    stompClient.subscribe('/topic/messages/' + sessionId, onPrivateMessageReceived);
  }

  const onPublicMessageReceived = (payload) => {
    console.log("received public message: " + payload);
  }

  const onPrivateMessageReceived = (payload) => {
    console.log("received private message: " + JSON.parse(payload.body).message);
    setreceivedMessage(JSON.parse(payload.body).message);
  }

  const sendPrivateMessage = (to, message) => {
    console.log("send message to " + to + " with message" + message);
    stompClient.send("/app/chat/" + to, {}, JSON.stringify({
      message: message,
      fromLogin: sessionId
    }));
  }

  return (
    <div className="App">
      <Switch>
        <Route path={"/"} exact render={() => <Home
          connectionState={connectionState}
          sendPrivateMessage={sendPrivateMessage}
          sessionId={sessionId}
          receivedMessage={receivedMessage}
        />} />
      </Switch>
    </div>
  );
}

export default App;
