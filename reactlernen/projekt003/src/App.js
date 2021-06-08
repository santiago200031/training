import React from "react"
import ContactCard from "./components/ContactCard";

function App() {
    return (
        <div className={"app"}>
            <ContactCard
                name={"Santiago"}
                telf={"0987655514"}
                URLlink={"https://media-exp1.licdn.com/dms/image/C4E03AQEkCSJ9zKw1Zw/profile-displayphoto-shrink_800_800/0/1615494709657?e=1627516800&v=beta&t=mgqjCdZqd04obVwaLD1TMSYLvxKRmYHJ1pMqdTbQVx8"}/>
            <ContactCard
                name={"Juan"}
                telf={"015204774343"}
                URLlink={"https://media-exp1.licdn.com/dms/image/C4E03AQEkCSJ9zKw1Zw/profile-displayphoto-shrink_800_800/0/1615494709657?e=asdfasdfasd"}/>
            <ContactCard
                name={"Andres"}
                telf={"2870965"}
                URLlink={"https://media-exp1.licdn.com/dms/image/C4E03AQEkCSJ9zKw1Zw/profile-displayphoto-shrink_800_800/0/1615494709657?e=sadasd"}/>
        </div>
    );
}

export default App