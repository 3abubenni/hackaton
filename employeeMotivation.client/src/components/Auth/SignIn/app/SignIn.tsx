import { useState } from "react";
import "../styles/signInstyles.css"
import { useNavigate } from 'react-router-dom';
import axios from "axios";

export const SignIn = () => {

    const navigate = useNavigate();
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const handleClickToRegistration = () =>{
        navigate(`/reg`);
    }

    const AuthorizationUser = async() => {
        const response = await axios.request({
            url: "http://localhost:8080/api/user/login",
            method: "post",
            data: {
                email: email,
                password: password
            },
        })
        localStorage.setItem('accessToken', response.data)
        sessionStorage.setItem('userEmail', email)
        return response.status;
    }

    const handleClickAuthorization = () =>{
        if(email && password){
            AuthorizationUser().then(() => {
                navigate(`/workzone/profile`)
            }).catch((error) => {
                if(error.response.status == 401){
                    alert('Wrong email or password')
                }else{
                    navigate(`/error`)
                }
            });
        }else{
            alert("Fill field");
        }
    }

    const handleEmailChange = (event : React.ChangeEvent<HTMLInputElement>) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event : React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    };

    return (
        <div id='container'>
            <h1>Log In</h1>
            <input type="text" placeholder='Email' onChange={handleEmailChange}/>
            <input type="password" placeholder='Password' onChange={handlePasswordChange}/>
            <div className="remindPass">
                <a href="">Forgot password?</a>
            </div>
            <div id='buttons'>
                <button onClick={() => handleClickAuthorization()}>Sign in</button>
                <button onClick={() => handleClickToRegistration()}>Registration</button>
            </div>
        </div>
    );
};