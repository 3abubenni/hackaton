import { IoSearchOutline } from "react-icons/io5";
import "../../Tasks/Parent/styles/Tasks.css"
import { useEffect, useState } from "react";
import { ITaskItem, ITasksList } from "../../../../entities/Items.interface";
import TaskItem from "../../Tasks/TaskItem/app/TaskItem";
import axios from "axios";
import { FilterTextForTasks } from "../../../../functions/filterText/FilterText";

export const UsersTasks = () => {

    const [tasksList, setTasksList] = useState<ITasksList>({
        children:[
        ]
    })
    
    const [activeOption, setActiveOption] = useState(1)

    useEffect(() =>{
        const getUserTasks = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const response = await axios.request({
                url: 'http://localhost:8080/api/task/user',
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`,
                },
            })

            let transformedTask: ITaskItem[] = response.data.map(task => { return{id: task.id, title: task.name, name:task.name, description: task.description, exp: task.exp, money: task.money, status: task.status} });
            transformedTask = transformedTask.filter(item => item.status === 1);
            setTasksList({children: transformedTask})
            setFilteredTasks({children: transformedTask})
        }

        const getUserCheckTasks = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const userClanId = sessionStorage.getItem('userClanId')
            const response = await axios.request({
                url: `http://localhost:8080/api/task/clan/${userClanId}`,
                method: `get`,
                headers: {
                    Authorization: `${accessToken}`,
                }
            })

            let transformedTask: ITaskItem[] = response.data.map(task => { return{id: task.id, title: task.name, name:task.name, description: task.description, exp: task.exp, money: task.money, status: task.status} });
            transformedTask = transformedTask.filter(item => item.status === 2);
            setTasksList({children: transformedTask})
            setFilteredTasks({children: transformedTask})
        }

        if(activeOption === 1){
            getUserTasks();
        }else{
            getUserCheckTasks();
        }
    }, [activeOption])


    const handleClickRemoveTaskItem = (task : ITaskItem) => {
        const afterRemoveData = filteredTasks.children.filter(task_inList => task_inList.id !== task.id);
        setTasksList({ children: afterRemoveData });
        setFilteredTasks({ children: afterRemoveData });
    };

    const [filteredTasks, setFilteredTasks] = useState<ITasksList>(tasksList);

    const handleChangeSearchStoreItem = (event : React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = event.target.value;
        const filteredData = tasksList.children.filter((item) => item.title.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredTasks({ children: filteredData });
    }
    
    return (
        <>
        <div className="mainView">
            <div className="tasksListMainView">
                <div className="searchConatiner">
                    <div className="searchIcon">
                        <IoSearchOutline />
                    </div>
                    <div>{">"}</div>
                    <input type="text" onChange={handleChangeSearchStoreItem}/>   
                </div>
                <div id="checkOption"><div className="option" id = {activeOption == 1 ? "active" : ""} onClick={() => setActiveOption(1)}><a>Your tasks</a></div><div className="option" id = {activeOption == 2 ? "active" : ""} onClick={() => setActiveOption(2)}><a>Check Tasks</a></div></div>
                <div id="tasksContainer">
                    {FilterTextForTasks(filteredTasks.children).map((item, index) =>
                            <TaskItem id={item.id} type={activeOption == 2 ? 'check' : 'answer'} status= {item.status} name={item.name} key={index} title={item.title} description={item.description} exp={item.exp} money={item.money} remove={handleClickRemoveTaskItem}/>
                        )}
                </div>
            </div>
        </div>
        </>
    );
};