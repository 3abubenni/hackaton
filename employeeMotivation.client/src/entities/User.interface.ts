export interface IUser{
    fname: string,
    lname: string,
    email: string,
    password: string,
    bday: Date,
    money?: number,
    exp?: number,
}