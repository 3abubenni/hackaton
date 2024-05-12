export function CheckWrongDate(userDate : Date){
    const today = new Date()
    if(userDate.getFullYear() >= today.getFullYear()-10) return true
    return false
}