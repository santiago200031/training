import React from "react"

function ContactCard(props) {
    return (
        <div className="contact-card">
            <img src={props.URLlink} alt={props.name}/>
            <h1>{props.name}: {props.telf}</h1>
        </div>
    )
}

export default ContactCard