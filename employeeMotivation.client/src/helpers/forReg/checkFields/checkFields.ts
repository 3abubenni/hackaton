import { CheckWrongDate } from "../../checkWrongDate/checkWrongDate";
import { convertDateFormat } from "../convertDateFormat/convertDateFormat";

export function CheckFields(value: string, index: number){
    if (value.length < 2) return false;
    if (value.length < 8 && index === 3) return false;

    const userInputDate = new Date(convertDateFormat(value));

    if ((index === 4) && (CheckWrongDate(userInputDate) || isNaN(userInputDate.getTime()))) return false;
    if ((index === 2) && (!value.includes('@') || value.indexOf('@') === value.length - 1)) return false;

    return true;
}
