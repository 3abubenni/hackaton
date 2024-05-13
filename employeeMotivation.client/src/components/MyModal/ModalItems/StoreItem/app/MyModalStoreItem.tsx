import { FC, useState } from "react";
import {IStoreItem } from "../../../../../entities/Items.interface";
import { IMyModalItems } from "../../../../../entities/Modal.interface";
import ModalBodyViewStore from "../../../../ModalBodyView/ModalBodyViewStore";

export const MyModalStoreItem : FC<IMyModalItems  & { remove: (task: IStoreItem) => void} & { closeModal: () => void}> = ({id, count, name, description, cost, amount, type, closeModal, remove}) => {

    const [item] = useState<IStoreItem>({
        id: id,
        name: name,
        description: description,
        cost: cost!,
        amount: amount!,
        count: count!,
    })
    
    return (
        <div className="myModalView" style={{height:'600px', width:'700px'}}>
            <ModalBodyViewStore item={item} type={type} remove={remove} closeModal={closeModal}/>
        </div>
    );
};