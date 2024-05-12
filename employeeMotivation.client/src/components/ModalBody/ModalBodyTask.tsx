import { FC, useState } from "react";
import axios from "axios";

const ModalBodyTask : FC<{closeModal : () => void}> = ({closeModal}) => {

    const [name, setName] = useState('')
    const [description, setDescription] = useState('')
    const [taskExp, setTaskExp] = useState(0)
    const [taskMoney, setTaskMoney] = useState(0)

    const CreateTask = async() =>{
        const accessToken = localStorage.getItem('accessToken')
        const clanId = sessionStorage.getItem('userClanId')
        const response = await axios.request({
            url: `http://localhost:8080/api/task/clan/${clanId}`,
            method: "post",
            data: {
                name: name,
                description: description,
                exp: taskExp,
                money: taskMoney,
            },
            headers: {
                Authorization: `${accessToken}`
            }
        })

        console.log('FromCreateTask', response)
    }
    
    const handleClickCreateTask = () =>{
        if(name.length < 3 || description.length < 20 || taskExp <= 0 || taskMoney <= 0 || isNaN(taskExp) || isNaN(taskMoney)){
            alert('Fill fields correctly')
        }else{
            CreateTask().then(() => {
                closeModal();
            });
        }
    }

    const handleChangeName = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setName(event.target.value)
    }
    const handleChangeDescription = (event : React.ChangeEvent<HTMLTextAreaElement>) =>{
        setDescription(event.target.value)
    }
    const handleChangeTaskExp = (event : React.ChangeEvent<HTMLInputElement>) =>{
        const value = Number(event.target.value)
        setTaskExp(value)
    }
    const handleChangeTaskMoney = (event : React.ChangeEvent<HTMLInputElement>) =>{
        const value = Number(event.target.value)
        setTaskMoney(value)
    }

    return (
        <>
            <label htmlFor="">{'Enter task name (more then 3 symbols)'}</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="text" onChange={handleChangeName}/>
            </div>
            <label htmlFor="">Enter description {'(more then 20 symbols)'}</label>
            <div className="inputValue" id="description">
                <div id='cursor'>{">"}</div>
                <textarea name="" id="task_description" onChange={handleChangeDescription}></textarea>
            </div>
            <label htmlFor="">Enter experience</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="text" onChange={handleChangeTaskExp}/>
            </div>
            <label htmlFor="">Enter money</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="text" onChange={handleChangeTaskMoney}/>
            </div>
            <button onClick={handleClickCreateTask}>Create Task</button>
        </> 
    );
};

export default ModalBodyTask;