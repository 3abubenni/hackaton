import { AiFillCloud } from "react-icons/ai";
import "../styles/Header.css"
import { useNavigate } from 'react-router-dom';

export const Header = () => {

    const navigate = useNavigate();

    const handleClickGoToLogIn = () =>{
        navigate(`/auth`)
    }
    return (
        <div id='headerContainer'>
            <div id='iconCloud' onClick={handleClickGoToLogIn}>
                <AiFillCloud />
            </div>
        </div>
    );
};
