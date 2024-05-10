export interface IClan{
    id: number,
    name : string,
}

export interface IClanList{
    children: IClan[],
}

export interface ITask{
    id: number,
    title: string,
    description: string,
    exp: number,
    money: number;
}

export interface ITasksList{
    children: ITask[],
}

export interface IStoreItem {
    image: string,
    name: string,
}

export interface IStoreList {
    children: IStoreItem[],
}

export interface INotifItem {
    id: number,
    title: string,
    description: string,
    type: string,
}

export interface INotifList {
    children: IStoreItem[],
}

export interface IMember {
    id: number,
    fname: string,
    lname: string,
    email: string,
}

export interface IMemberList{
    children: IMember[]
}