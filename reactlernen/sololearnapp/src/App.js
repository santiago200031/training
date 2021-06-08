import React from "react"
import CounterByClick from "./CounterByClick"
import CounterOnTyping from "./CounterOnTyping"
import CounterWithHook from "./CounterWithHook";

function App() {
    return (
        <div align={"center"}>
            <CounterByClick/>
            <CounterOnTyping/>
            <CounterWithHook/>

        </div>
    )
}

export default App