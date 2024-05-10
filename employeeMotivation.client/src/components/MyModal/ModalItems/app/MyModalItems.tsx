import { FC, useState } from "react";
import { IMyModalItems } from "../../../../entities/Modal.interface";
import "../styles/MyModalItemsstyles.css"
import { ITask } from "../../../../entities/Items.interface";


export const MyModalItems : FC<IMyModalItems  & { remove: (task : ITask) => void} & { closeModal: () => void}> = ({id, name, description, type, img, money, exp , remove, closeModal}) => {

    const [task] = useState<ITask>({
        id: id,
        title: name,
        description: description,
        exp: exp!,
        money: money!,
    })

    const RemoveTask = () =>{
        remove(task);
        closeModal();
    }
    
    return (
        <div className="myModalItemsView" style={type == 'task' ? {height: '500px'} : {height: '750px'}}>
            {img ? <img src={img}/> : ""}
            <div className="elements">
                <label htmlFor=""><p>Title:</p>{">"} {name}</label>
                <p>Description:</p>
                <div className="answer_description">{"> "}<textarea name="" id="" readOnly={true}>{description}</textarea></div>
                {money ? <label htmlFor=""><p>Money:</p>{">"}  {money}</label> : ""}
                {exp ? <label htmlFor=""><p>Experience: </p>{">"} {exp}</label> : ""}
                {type == 'answer' ? <label htmlFor="">Put your answer</label> : ""}
                {type == 'answer' ? <input type="file"/> : ""}
                {type == 'answer' ? <div className="answer_description">{">"}<textarea name="" id="answer_description"></textarea></div> : ""}
            </div>
            {type == 'task' ? <button onClick={RemoveTask}>Take task</button> : type == 'answer' ? <button onClick={() => remove(task)}>Send answer</button> : <button>Buy</button>}
        </div>
    );
};