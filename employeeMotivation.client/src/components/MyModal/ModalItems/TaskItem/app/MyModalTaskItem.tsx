import { FC, useState } from "react";
import {ITaskItem } from "../../../../../entities/Items.interface";
import { IMyModalItems } from "../../../../../entities/Modal.interface";
import ModalBodyViewTasks from "../../../../ModalBodyView/ModalBodyViewTasks";
import "../styles/MyModalItemsstyles.css"


export const MyModalTaskItem : FC<IMyModalItems  & { remove: (task: ITaskItem) => void} & { closeModal: () => void}> = ({id, name, description, type, money, exp , remove, closeModal, status}) => {

    const [task] = useState<ITaskItem>({
        id: id,
        name: name,
        description: description,
        exp: exp!,
        money: money!,
        title: "",
        status: status!,
    })
    
    return (
        <div className="mainView" style={{height:'600px', width:'650px'}}>
            <ModalBodyViewTasks task={task} type={type} remove={remove} closeModal={closeModal}/>
        </div>
    );
};