import React from "react"
import ItemList from "./ItemList"

function TodoList() {
    return (
        <div className={"todo-list"}>
            <ItemList/>
            <ItemList/>
        </div>
    )
}

export default TodoList