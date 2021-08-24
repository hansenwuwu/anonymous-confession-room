import React from 'react'

function Confess(props) {
    return (
        <div>
            <h1>this is confess</h1>
            <button onClick={() => { props.goToStarter() }}>Back</button>
        </div >
    )
}

export default Confess
