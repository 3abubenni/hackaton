import axios from "axios";
import { FC, useState } from "react";

const ModalBodyClan : FC<{closeModal : () => void}> = ({closeModal}) => {

    const [name, setName] = useState('')
    
    const handleChangeName = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setName(event.target.value)
    }

    const CreateClan = async() =>{
        const accessToken = localStorage.getItem('accessToken')
        const response = await axios.request({
            url: "http://localhost:8080/api/clan",
            method: "post",
            data: {
                name: name,
            },
            headers: {
                Authorization: `${accessToken}`
            }
        })

        console.log('FromCreateClan', response)
    }

    const handleClickCreateClan = () =>{
        if(name.length > 3 && name.length < 40){
            CreateClan().then(() => {
                closeModal();
            });
        }else{
            alert('Clan name length must be between 3 and 40')
        }
    }
    

    return (
        <>
            <label htmlFor="">Enter clan name</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="text" onChange={handleChangeName}/>
            </div>
            <button onClick={handleClickCreateClan}>Create Clan</button>
        </>
    );
};

export default ModalBodyClan;