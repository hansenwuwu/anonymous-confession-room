import React, { useState } from 'react'
import { Row, Col, Card, Button, Form } from 'react-bootstrap'

function Starter(props) {

    const [username, setusername] = useState("");

    const [sessionId, setsessionId] = useState("");
    const [message, setmessage] = useState("");

    const isUsernameInvalid = () => {
        return username === "";
    }

    const isTestInValid = () => {
        return (sessionId === "" || message === "");
    }

    return (
        <Row className="row">
            <Col md={7} xs={12}>
                <div className="title">
                    <h1>匿名告解室</h1>
                    <h5>
                        屬於你的匿名告解室。想要告解不欲人知的秘密嗎
                    </h5>
                </div>
            </Col>
            <Col md={5} xs={12}>
                <div className="registration">
                    <Card style={{ width: "400px", margin: "auto" }}>
                        <Card.Body>
                            <Form.Control required style={{ marginBottom: "10px" }} type="text" placeholder="輸入化名"
                                onChange={(e) => {
                                    setusername(e.target.value);
                                }} />
                            <Button disabled={!props.connectionState || isUsernameInvalid()} style={{ width: "100%", marginBottom: "5px" }} variant="primary"
                                onClick={() => {
                                    props.goToConfess();
                                }}>我要告解</Button>
                            <Button disabled={!props.connectionState || isUsernameInvalid()} style={{ width: "100%", marginBottom: "5px" }} variant="primary"
                                onClick={() => {
                                    props.goToListen();
                                }}>我想傾聽</Button>
                            <h4 style={{ marginTop: "10px" }}>Session id: {props.sessionId}</h4>
                            <Form.Control required style={{ marginBottom: "10px" }} type="text" placeholder="輸入 session id"
                                onChange={(e) => {
                                    setsessionId(e.target.value);
                                }} />
                            <Form.Control required style={{ marginBottom: "10px" }} type="text" placeholder="輸入 message"
                                onChange={(e) => {
                                    setmessage(e.target.value);
                                }} />
                            <Button disabled={!props.connectionState || isTestInValid()} style={{ width: "100%" }} variant="primary"
                                onClick={() => {
                                    console.log("發送訊息至 " + sessionId + " with: " + message);
                                    props.sendPrivateMessage(sessionId, message);
                                }}
                            >測試發送訊息</Button>
                            <h4 style={{ marginTop: "10px" }}>接收: {props.receivedMessage}</h4>
                        </Card.Body>
                    </Card>
                </div>
            </Col>
        </Row>
    )
}

export default Starter
