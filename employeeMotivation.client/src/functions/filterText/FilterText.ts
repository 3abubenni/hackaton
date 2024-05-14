import { ITaskItem, IClan } from "../../entities/Items.interface";

export const FilterTextForClan = (filteredData : IClan[]) =>{
    return filteredData.map((item) => {
        if (item.name.length > 42) {
            return { name: item.name.slice(0, 10) + "..." };
        } else {
            return item;
        }
    });
}

export function FilterTextForTasks(filteredData : ITaskItem[]) {
    
    return filteredData.map((item) => {
        if (item.title.length > 10) {
            return { title: item.title.slice(0, 10) + "...", description: item.description, exp: item.exp, money: item.money, name: item.name, id:item.id, status: item.status};
        } else {
            return item;
        }
    });
}