import { IoSearchOutline } from "react-icons/io5";
import "../styles/Tasks.css"
import { MyModal } from "../../../../MyModal/Modal/app/MyModal";
import Modal from 'react-modal';
import { useState } from "react";
import { ITask, ITasksList } from "../../../../../entities/Items.interface";
import TaskItem from "../../TaskItem/app/TaskItem";
export const Tasks = () => {

    const [modalIsOpen, setModalIsOpen] = useState(false);

    const [tasksList, setTasksList] = useState<ITasksList>({
        children:[
            {id: 0, title: "Do rtpoop poefwpoewkfffhfffqwfq", description:'You must do RTPO', exp: 10, money: 100},
            {id: 1, title: "Do CHMfffff", description:'You must do CHM', exp: 10, money: 100},
            {id: 2, title: "Do TPR", description:'You must do TPR', exp: 10, money: 100},
        ]
    })

    const FilterText = (filteredData : ITask[]) =>{
        return filteredData.map((item) => {
            if (item.title.length > 24) {
                return { title: item.title.slice(0, 22) + "...", description: item.description, exp: item.exp, money: item.money };
            } else {
                return item;
            }
        });
    }

    const [filteredTasks, setFilteredTasks] = useState<ITasksList>(tasksList);

    const openModal = () => {
        setModalIsOpen(true);
    };
    const closeModal = () => {
        setModalIsOpen(false);
    };

    const SearchTaskItem = (event : React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = event.target.value;
        const filteredData = tasksList.children.filter((item) => item.title.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredTasks({ children: filteredData });
    }

    const RemoveTaskItem = (task : ITask) => {
        const afterRemoveData = filteredTasks.children.filter(task_inList => task_inList.id !== task.id);
        const updatedAfterRemoveData = afterRemoveData.map((task, index) => ({
            ...task,
            id: index,
        }));
        setTasksList({ children: updatedAfterRemoveData });
        setFilteredTasks({ children: updatedAfterRemoveData });
    };


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
    
    return (
        <>
        <div className="mainView">
            <div className="tasksListMainView">
                <div className="searchConatiner">
                    <div className="searchIcon">
                        <IoSearchOutline />
                    </div>
                    <div>{">"}</div>
                    <input type="text" onChange={SearchTaskItem}/>   
                </div>
                <button onClick={openModal}>Add store item</button>
                <div id="tasksContainer">
                    {FilterText(filteredTasks.children).map((item, index) =>
                            <TaskItem key={index} id={index} title={item.title} description={item.description} exp={item.exp} money={item.money} remove={RemoveTaskItem}/>
                        )}
                </div>
            </div>
        </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            style={customStyles}
            >
                <MyModal inputValue="Enter name of product"/>
            </Modal>
        </>
    );
};