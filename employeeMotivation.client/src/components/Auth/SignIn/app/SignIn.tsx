import { useState } from "react";
import "../styles/signInstyles.css"
import { useNavigate } from 'react-router-dom';
import axios from "axios";

export const SignIn = () => {

    const navigate = useNavigate();
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const ToRegistration = () =>{
        navigate(`/reg`);
    }

    const AuthorizationUser_PostRequest = async() => {
        const accessToken = localStorage.getItem("accessToken");
        console.log(accessToken);
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

    const Authorization = () =>{
        if(email && password){
            AuthorizationUser_PostRequest().then(() => {
                navigate(`/workzone/tasks`)
            }).catch(() => {
                navigate(`/error`)
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
                <button onClick={() => Authorization()}>Sign in</button>
                <button onClick={() => ToRegistration()}>Registration</button>
            </div>
        </div>
    );
};