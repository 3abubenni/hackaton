import { FC, useState } from "react";
import { ITask } from "../../../../../entities/Items.interface";
import "../styles/TaskItemstyles.css"
import Modal from 'react-modal';
import { MyModalItems } from "../../../../MyModal/ModalItems/app/MyModalItems";
import { useParams } from "react-router-dom";



const TaskItem: FC<ITask & { remove: (task : ITask) => void }> = ({id, title, description, exp, money, remove}) => {
    const customStyles = {
        content: {
            top: '50%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)',
            backgroundColor: 'rgb(22, 62, 73)',
            borderRadius: '20px',
        },
        overlay: {
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            transition: 'background-color 0.5s ease'
        }
    };

    const url_now = useParams();
    const url_from = url_now['*']?.split("/")[1];

    const [modalIsOpen, setModalIsOpen] = useState(false);
    
    const openModal = () => {
        setModalIsOpen(true);
    };
    const closeModal = () => {
        setModalIsOpen(false);
    };

    return (
        <>
            <div id="taskItem" onClick={openModal}>
                <div className="taskItemElements">
                    <p>{title}</p>
                </div>
            </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            style={customStyles}
            >
                <MyModalItems id={id} name={title} description={description} type={url_from == 'usersTasks' ? 'answer' : 'task'} exp={exp} money={money} remove={remove} closeModal={closeModal}/>
            </Modal>
        </>
        
    );
};

export default TaskItem;