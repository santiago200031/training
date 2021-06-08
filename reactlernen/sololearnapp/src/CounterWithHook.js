import React, {useState} from "react"

function CounterWithHook() {
    let [counter, setCounter] = useState(0);

    function incrementCounter() {
        setCounter(counter+1);
    }

    return (
        <div id="counter-hook" align="center">
            <p>{counter}</p>
            <button onClick={incrementCounter}>
                Incrementar
            </button>
        </div>
    )
}

export default CounterWithHook