import { useNavigate } from "react-router-dom";
import "../styles/DropDownWindowstyles.css"

interface DropWindow{
    showDropWindow : (value : boolean) => void
}

export const DropDownWindow : React.FC<DropWindow> = ({showDropWindow}) => {

    const navigate = useNavigate();

    const GoToProfile = () =>{
        navigate(`profile`);
        showDropWindow(false);
    } 
    const GoToInventory = () =>{
        navigate(`inventory`);
        showDropWindow(false);
    } 
    const GoToUserClan = () =>{
        navigate(`userClan`);
        showDropWindow(false);
    } 
    const GoToUsersTasks= () =>{
        navigate(`usersTasks`);
        showDropWindow(false);
    } 
    const GoToUsersNotif= () =>{
        navigate(`notifications`);
        showDropWindow(false);
    } 
    return (
        <>
            <ul id="dropDownSelection">
                <li onClick={GoToProfile}>Profile</li>
                <li onClick={GoToInventory}>Inventory</li>
                <li onClick={GoToUserClan}>Your clan</li>
                <li onClick={GoToUsersTasks}>Your tasks</li>
                <li onClick={GoToUsersNotif}>Notifications</li>
            </ul>
        </>
    );
};
