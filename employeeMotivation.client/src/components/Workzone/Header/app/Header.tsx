import { AiFillCloud } from "react-icons/ai";
import "../styles/Headerstyles.css"
import { useNavigate } from "react-router-dom";
import { DropDownWindow } from "../../DropDownWindow/app/DropDownWindow";
import { FaUserCircle } from "react-icons/fa";
import { useState } from "react";

export const Header = () => {

    const navigate = useNavigate();
    const [showWindow, setShowWindow] = useState(false);

    const handleClickGoToSelections = () =>{
        setShowWindow(!showWindow);
    }
    const handleClickGoToTasks = () =>{
        navigate(`tasks`);
        setShowWindow(false);
    }
    const handleClickGoToClan = () =>{
        navigate(`clans`);
        setShowWindow(false);
    }
    const handleClickGoToStore = () =>{
        navigate(`store`);
        setShowWindow(false);
    }
    return (
        <>
            <nav>
                <ul>
                    <li>
                        <div id='iconCloud'>
                            <AiFillCloud />
                        </div>
                    </li>
                    <li>
                        <span id="notif">2</span>
                        <a href="#" onClick={() => handleClickGoToTasks()}>Tasks</a>
                    </li>
                    <li>
                        <a href="#" onClick={() => handleClickGoToStore()}>Store</a>
                    </li>
                    <li>
                        <a href="#" onClick={() => handleClickGoToClan()}>Clans</a>
                    </li>
                    <li>
                        <div id="iconUser" onClick={() => handleClickGoToSelections()}>
                            <FaUserCircle />
                        </div>
                    </li>
                </ul>
            </nav>
            {showWindow ? <DropDownWindow showDropWindow={setShowWindow}/> : ""}
        </>
    );
};
