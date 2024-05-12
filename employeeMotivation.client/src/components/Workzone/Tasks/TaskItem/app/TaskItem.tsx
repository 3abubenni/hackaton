import { FC, useState } from "react";
import { ITaskItem } from "../../../../../entities/Items.interface";
import "../styles/TaskItemstyles.css"
import Modal from 'react-modal';
import { MyModalItems } from "../../../../MyModal/ModalItems/app/MyModalItems";
import { customStyles } from "../../../../../helpers/styles/customStyleModal";

const TaskItem: FC<ITaskItem & { remove: (task : ITaskItem) => void } & {type : string}> = ({id, title, description, exp, money, remove, name, status, type}) => {

    const [modalIsOpen, setModalIsOpen] = useState(false);
    
    const handleClickOpenModal = () => {
        setModalIsOpen(true);
    };
    const handleClickCloseModal = () => {
        setModalIsOpen(false);
    };


    return (
        <>
            <div id="taskItem" onClick={handleClickOpenModal}>
                <div className="taskItemElements">
                    <p>{title}</p>
                </div>
            </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={handleClickCloseModal}
            style={customStyles}
            >
                <MyModalItems id={id} name={name} description={description} status={status} type={type} exp={exp} money={money} remove={remove} closeModal={handleClickCloseModal}/>
            </Modal>
        </>
        
    );
};

export default TaskItem;