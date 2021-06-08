import React from "react"
import ReactDOM from "react-dom"
import App from "./App"

ReactDOM.render(
    <App/>,
    document.getElementById("root")
)

/*
let CounterByClick = 0;

function showTime() {
    CounterByClick++;
    const time = <p>{CounterByClick}</p>;
    ReactDOM.render(
        {time},
        document.getElementById('root')
    );
}

setInterval(showTime, 1000);*/
