import { FC, useState } from "react";
import "../styles/MyModalItemsstyles.css"
import { ITaskItem } from "../../../../entities/Items.interface";
import { IMyModalItems } from "../../../../entities/Modal.interface";
import axios from "axios";


export const MyModalItems : FC<IMyModalItems  & { remove: (task : ITaskItem) => void} & { closeModal: () => void}> = ({id, name, description, type, img, money, exp , remove, closeModal, status}) => {

    const [task] = useState<ITaskItem>({
        id: id,
        name: name,
        description: description,
        exp: exp!,
        money: money!,
        title: "",
        status: status,
    })

    const TakeTask = async() =>{
        const accessToken = localStorage.getItem('accessToken')
        const response = await axios.request({
            url: `http://localhost:8080/api/task/take/${task.id}`,
            method: 'post',
            headers: {
                Authorization: `${accessToken}`
            }
        })

        console.log(response)
    }


    const handleClickTakeTask = () =>{
        TakeTask().then(() => {
            remove(task),
            closeModal()
        }).catch((error) => {
            if(error.response.status === 403){
                alert(`You cant take this task`)
            }
        });
    }

    const SendAnswer = async() =>{
        const accessToken = localStorage.getItem('accessToken')
        await axios.request({
            url: `http://localhost:8080/api/task/mark_solved/${task.id}`,
            method: `post`,
            headers: {
                Authorization: `${accessToken}`
            }
        })
    }

    const handleClickSendAnswer = () =>{
        SendAnswer().then(() => {
            remove(task),
            closeModal();
        }).catch((error) => {
            if(error.response.status === 403){
                alert(`It's not your task`)
            }
        })
    }

    const CheckAnswer = async(solved : boolean) =>{
        const accessToken = localStorage.getItem('accessToken')
        const response = await axios.request({
            url: `http://localhost:8080/api/task/check/${task.id}?solvedCorrectly=${solved}`,
            method: `post`,
            headers: {
                Authorization: `${accessToken}`
            }
        })

        console.log(response)
    }

    const handelClickCheck = (solved : boolean) =>{
        CheckAnswer(solved).then(() =>{
            remove(task),
            closeModal();
        }).catch((error) =>{
            if(error.response.status === 403){
                alert(`You cant check this task`)
            }
        })
    }
    
    return (
        <div className="myModalItemsView" style={{height: '500px'}}>
            {img ? <img src={img}/> : ""}
            <div className="elements">
                <label htmlFor=""><p>Name:</p>{">"} {name}</label>
                <p>Description:</p>
                <div className="answer_description">{"> "}<textarea name="" id="task_description" readOnly={true}>{description}</textarea></div>
                {money ? <label htmlFor=""><p>Money:</p>{">"}  {money}</label> : ""}
                {exp ? <label htmlFor=""><p>Experience: </p>{">"} {exp}</label> : ""}
            </div>
            {type == 'task' ? <button onClick={handleClickTakeTask}>Take task</button> : type == 'answer' ? <button onClick={handleClickSendAnswer}>Send answer</button> 
            : type =='check' ? <div className="buttonsOnCheck"><button onClick={() => handelClickCheck(false)}>Incorrectly</button> <button onClick={() => handelClickCheck(true)}>Correctly!</button></div> 
            : <button>Buy</button>}
        </div>
    );
};