import { FC } from "react";
import "../styles/MyModalstyles.css"
import ModalBodyTask from "../../../ModalBody/ModalBodyTask";
import ModalBodyClan from "../../../ModalBody/ModalBodyClan";
import ModalBodyStore from "../../../ModalBody/ModalBodyStroe";

export const MyModal:FC<{type : string} & {closeModal : () => void}> = ({ type, closeModal}) => {
    return (
        <div className="myModalView" style={type === 'addTask' ? {height:'600px', width:'600px'} : type === 'store' ? {height:'850px', width:'700px'} : {}} >
            <div className="input">
                {type == 'addTask' ? <ModalBodyTask closeModal={closeModal}/>
                : type == 'createClan' ? <ModalBodyClan closeModal={closeModal}/>
                : <ModalBodyStore closeModal={closeModal}/>
                }
            </div>
        </div>
    );
};