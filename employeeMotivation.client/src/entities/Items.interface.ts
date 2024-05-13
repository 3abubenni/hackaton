export interface IClan{
    id: number,
    name : string,
}

export interface IClanList{
    children: IClan[],
}

export interface ITaskItem{
    id: number,
    title: string,
    name: string,
    description: string,
    exp: number,
    money: number,
    status: number,
}

export interface ITasksList{
    children: ITaskItem[],
}

export interface IStoreItem {
    id: number,
    name: string,
    description: string,
    cost: number,
    count?: number,
    amount?: number,
}

export interface IStoreList {
    children: IStoreItem[],
}

export interface INotifItem {
    id: number,
    title: string,
    description: string,
    type: string,
    idClan: string,
}

export interface INotifList {
    children: INotifItem[],
}

export interface IMember {
    id: number,
    fname: string,
    lname: string,
    email?: string,
}

export interface IMemberList{
    children: IMember[]
}

export interface IInventoryItem {
    id: number,
    name: string,
    description: string,
    cost: number,
    count: number,
    img?: string,
}

export interface IInventoryList {
    children: IInventoryItem[];
}