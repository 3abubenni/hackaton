import { FC, useState } from "react";
import "../styles/MyModalstyles.css"
import { IMyModalInputp } from "../../../../entities/Modal.interface";
import axios from "axios";

export const MyModal:FC<IMyModalInputp> = ({inputValue}) => {

    const [clanName, setClanName] = useState('')

    const CreateClan = async() =>{
        const accessToken = localStorage.getItem('accessToken')
        const response = await axios.request({
            url: "http://localhost:8080/api/clan",
            method: "post",
            data: {
                name: clanName,
            },
            headers: {
                Authorization: `${accessToken}`
            }
        })

        console.log(response)
    }

    const handleClickCreateClan = () =>{
        if(clanName.length > 3 && clanName.length < 40){
            CreateClan()
        }else{
            alert('Clan name length must be between 3 and 40')
        }
    }

    const handleChangeClanName = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setClanName(event.target.value)
    }

    return (
        <div className="myModalView">
            <div className="inputClanName">
                <label htmlFor="">{inputValue}</label>
                <div className="inputValue">
                    <div id='cursor'>{">"}</div>
                    <input type="text" onChange={handleChangeClanName}/>
                </div>
            </div>
            <button onClick={handleClickCreateClan}>Create Clan</button>
        </div>
    );
};