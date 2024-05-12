import { useNavigate } from "react-router-dom";
import "../styles/DropDownWindowstyles.css"

interface DropWindow{
    showDropWindow : (value : boolean) => void
}

export const DropDownWindow : React.FC<DropWindow> = ({showDropWindow}) => {

    const navigate = useNavigate();

    const handleClickGoToProfile = () =>{
        navigate(`profile`);
        showDropWindow(false);
    } 
    
    const handleClickGoToInventory = () =>{
        navigate(`inventory`);
        showDropWindow(false);
    } 

    const handleClickGoToUserClan = () =>{
        navigate(`userClan`);
        showDropWindow(false);
    } 

    const handleClickGoToUsersTasks= () =>{
        navigate(`usersTasks`);
        showDropWindow(false);
    } 

    const handleClickGoToUsersNotif= () =>{
        navigate(`notifications`);
        showDropWindow(false);
    } 
    return (
        <>
            <ul id="dropDownSelection">
                <li onClick={handleClickGoToProfile}>Profile</li>
                <li onClick={handleClickGoToInventory}>Inventory</li>
                <li onClick={handleClickGoToUserClan}>Your clan</li>
                <li onClick={handleClickGoToUsersTasks}>Your tasks</li>
                <li onClick={handleClickGoToUsersNotif}>Notifications</li>
            </ul>
        </>
    );
};
