import {SignIn} from '../../SignIn/app/SignIn';
import "../styles/Authstyles.css"
import { Header } from '../../Header/app/Header';

export const Auth = () => {
    return (
        <div id='auth'>
            <Header/>
            <div id='form'>
                <SignIn/>
            </div>
        </div>
    );
};
