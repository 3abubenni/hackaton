import { IoSearchOutline } from "react-icons/io5";
import "../../Tasks/Parent/styles/Tasks.css"
import { useEffect, useState } from "react";
import { ITask, ITasksList } from "../../../../entities/Items.interface";
import TaskItem from "../../Tasks/TaskItem/app/TaskItem";
import axios from "axios";
export const UsersTasks = () => {

    const [tasksList, setTasksList] = useState<ITasksList>({
        children:[
            {id: 0, title: "Do rtpoop poefwpoewkfffhfffqwfq", description:'You must do RTPO', exp: 10, money: 100},
            {id: 1, title: "Do CHMfffff", description:'You must do CHM', exp: 10, money: 100},
            {id: 2, title: "Do TPR", description:'You must do TPR', exp: 10, money: 100},
        ]
    })

    useEffect(() =>{
        const getUserTasks = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const response = axios.request({
                url: 'http://localhost:8080/api/task/user',
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`,
                },
            })

            console.log(response)
        }

        getUserTasks();
    })

    const RemoveTaskItem = (task : ITask) => {
        const afterRemoveData = filteredTasks.children.filter(task_inList => task_inList.id !== task.id);
        const updatedAfterRemoveData = afterRemoveData.map((task, index) => ({
            ...task,
            id: index,
        }));
        setTasksList({ children: updatedAfterRemoveData });
        setFilteredTasks({ children: updatedAfterRemoveData });
    };

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
    const SearchStoreItem = (event : React.ChangeEvent<HTMLInputElement>) => {
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
                    <input type="text" onChange={SearchStoreItem}/>   
                </div>
                <div id="tasksContainer">
                    {FilterText(filteredTasks.children).map((item, index) =>
                            <TaskItem id={index} key={index} title={item.title} description={item.description} exp={item.exp} money={item.money} remove={RemoveTaskItem}/>
                        )}
                </div>
            </div>
        </div>
        </>
    );
};