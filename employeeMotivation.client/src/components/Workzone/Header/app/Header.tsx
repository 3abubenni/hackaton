import { AiFillCloud } from "react-icons/ai";
import "../styles/Headerstyles.css"
import { useNavigate } from "react-router-dom";
import { DropDownWindow } from "../../DropDownWindow/app/DropDownWindow";
import { FaUserCircle } from "react-icons/fa";
import { useState } from "react";

export const Header = () => {

    const navigate = useNavigate();
    const [showWindow, setShowWindow] = useState(false);

    const GoToSelections = () =>{
        setShowWindow(!showWindow);
    }
    const GoToTasks = () =>{
        navigate(`tasks`);
        setShowWindow(false);
    }
    const GoToClan = () =>{
        navigate(`clans`);
        setShowWindow(false);
    }
    const GoToStore = () =>{
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
                        <a href="#" onClick={() => GoToTasks()}>Tasks</a>
                    </li>
                    <li>
                        <a href="#" onClick={() => GoToStore()}>Store</a>
                    </li>
                    <li>
                        <a href="#" onClick={() => GoToClan()}>Clans</a>
                    </li>
                    <li>
                        <div id="iconUser" onClick={() => GoToSelections()}>
                            <FaUserCircle />
                        </div>
                    </li>
                </ul>
            </nav>
           {showWindow ? <DropDownWindow showDropWindow={setShowWindow}/> : ""}
        </>
    );
};
