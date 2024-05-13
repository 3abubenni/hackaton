import axios from "axios";
import { FC } from "react";
import { ITaskItem } from "../../entities/Items.interface";

const ModalBodyViewTasks : FC<{task: ITaskItem} & {type : string} & { remove: (task : ITaskItem) => void} & { closeModal: () => void}> = ({task, type, remove, closeModal}) => {

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
        <div className="myModalItemsView" style={{height: '600px'}}>
            <div className="elements">
                <label htmlFor=""><p>Name:</p>{">"}{task.name}</label>
                <p>Description:</p>
                <div className="answer_description">{"> "}<textarea name="" id="task_description" readOnly={true}>{task.description}</textarea></div>
                <label htmlFor=""><p>Money:</p>{">"}  {task.money}</label> : ""
                <label htmlFor=""><p>Experience: </p>{">"} {task.exp}</label> : ""
            </div>
            {type == 'task' ? <button onClick={handleClickTakeTask}>Take task</button> : type == 'answer' ? <button onClick={handleClickSendAnswer}>Send answer</button> 
            : <div className="buttonsOnCheck"><button onClick={() => handelClickCheck(false)}>Incorrectly</button> <button onClick={() => handelClickCheck(true)}>Correctly!</button></div>}
        </div>
    );
};

export default ModalBodyViewTasks;