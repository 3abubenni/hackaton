import { IUser } from "../../../entities/User.interface";

export function FillUserFields(newUser: IUser, value: number, inputValue: string){
    switch (value) {
    case 0:
        newUser.fname = inputValue;
        break;
    case 1:
        newUser.lname = inputValue;
        break;
    case 2:
        newUser.email = inputValue;
        break;
    case 3:
        newUser.password = inputValue;
        break;
    case 4:
        newUser.bday = new Date(inputValue);
        break;
    default:
        break;
    }

    return;
}