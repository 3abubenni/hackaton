import { Header } from '../../../Auth/Header/app/Header';
import { SignUp } from '../../SignUp/app/SignUp';
import "../styles/Regstyles.css"

export const Reg = () => {
    return (
        <>
            <div id='reg'>
                <Header/>
                <div id='form'>
                    <SignUp/>
                </div>
            </div>
        </>
    );
};