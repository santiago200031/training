import React from "react"

class CounterOnTyping extends React.Component {
    state = {
        counter: 0
    }

    incrementCounter = () => {
        this.setState({
            counter: this.state.counter + 1
        })
    }

    render() {
        return (
            <div>
                <p>{this.state.counter}</p>
                <input type="text" onKeyPress={this.incrementCounter}/>
            </div>
        )
    }
}

export default CounterOnTyping