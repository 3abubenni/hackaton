import { IoSearchOutline } from "react-icons/io5";
import "../styles/Tasks.css"
import { MyModal } from "../../../../MyModal/Modal/app/MyModal";
import Modal from 'react-modal';
import { useEffect, useState } from "react";
import { ITaskItem, ITasksList } from "../../../../../entities/Items.interface";
import TaskItem from "../../TaskItem/app/TaskItem";
import { customStyles } from "../../../../../helpers/styles/customStyleModal";
import { FilterTextForTasks } from "../../../../../functions/filterText/FilterText";
import axios from "axios";

export const Tasks = () => {

    const [modalIsOpen, setModalIsOpen] = useState(false);

    const [tasksList, setTasksList] = useState<ITasksList>({
        children:[
        ]
    })
    const [filteredTasks, setFilteredTasks] = useState<ITasksList>(tasksList);

    useEffect(() =>{

        const getAllClansTasks = async() =>{
            const accessToken = localStorage.getItem('accessToken');
            const userClanId = sessionStorage.getItem('userClanId')
            const response = await axios.request({
                url: `http://localhost:8080/api/task/clan/${userClanId}`,
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`,
                },
            })

            let transformedTask: ITaskItem[] = response.data.map(task => { return{id: task.id, title: task.name, name:task.name, description: task.description, exp: task.exp, money: task.money, status: task.status} });
            transformedTask = transformedTask.filter(item => item.status === 0);
            setTasksList({children: transformedTask})
            setFilteredTasks({children: transformedTask})
        }

        getAllClansTasks();
    }, [modalIsOpen])

    const handleClickOpenModal = () => {
        setModalIsOpen(true);
    };

    const handleClickCloseModal = () => {
        setModalIsOpen(false);
    };

    const handleChangeSearchTaskItem = (event : React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = event.target.value;
        const filteredData = tasksList.children.filter((item) => item.title.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredTasks({ children: filteredData });
    }

    const handleClickRemoveTaskItem= (task : ITaskItem) => {
        const afterRemoveData = filteredTasks.children.filter(task_inList => task_inList.id !== task.id);
        console.log('afterremove',afterRemoveData)
        setTasksList({ children: afterRemoveData });
        setFilteredTasks({ children: afterRemoveData });
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
                    <input type="text" onChange={handleChangeSearchTaskItem}/>   
                </div>
                <button onClick={handleClickOpenModal}>Add task</button>
                <div id="tasksContainer">
                    {FilterTextForTasks(filteredTasks.children).map((item, index) =>
                            <TaskItem key={index} type={'task'} name={item.name} id={item.id} title={item.title} description={item.description} exp={item.exp} money={item.money} remove={handleClickRemoveTaskItem} status={item.status}/>
                        )}
                </div>
            </div>
        </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={handleClickCloseModal}
            style={customStyles}
            >
                <MyModal type="addTask" closeModal={handleClickCloseModal}/>
            </Modal>
        </>
    );
};