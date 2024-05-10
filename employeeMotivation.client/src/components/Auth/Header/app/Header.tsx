import { AiFillCloud } from "react-icons/ai";
import "../styles/Header.css"
import { useNavigate } from 'react-router-dom';

export const Header = () => {

    const navigate = useNavigate();

    const GoToLogIn = () =>{
        navigate(`/auth`)
    }
    return (
        <div id='headerContainer'>
            <div id='iconCloud' onClick={GoToLogIn}>
                <AiFillCloud />
            </div>
        </div>
    );
};
