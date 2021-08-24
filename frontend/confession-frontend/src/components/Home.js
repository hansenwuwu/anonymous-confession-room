import React, { useState } from 'react'
import { Container } from 'react-bootstrap'
import '../css/Home.css';
import Confess from './Confess';
import Starter from './Starter';

function Home(props) {

    const [pageState, setpageState] = useState("starter");

    const goToListen = () => {
        setpageState("listen");
    }

    const goToConfess = () => {
        setpageState("confess");
    }

    const goToStarter = () => {
        setpageState("starter");
    }

    return (
        <Container fluid>
            {pageState === "starter" && <Starter
                connectionState={props.connectionState}
                sendPrivateMessage={props.sendPrivateMessage}
                goToListen={goToListen}
                goToConfess={goToConfess}
                goToStarter={goToStarter}
                sessionId={props.sessionId}
                receivedMessage={props.receivedMessage}
            />}
            {pageState === "confess" && <Confess
                connectionState={props.connectionState}
                sendPrivateMessage={props.sendPrivateMessage}
                goToListen={goToListen}
                goToConfess={goToConfess}
                goToStarter={goToStarter}
            />}
        </Container >
    )
}

export default Home;