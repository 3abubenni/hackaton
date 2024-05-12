import "../styles/SignUpstyles.css"
import { useState } from 'react';
import { TextAnimation } from '../../TextAnimation/app/TextAnimation';
import { IUser } from "../../../../entities/User.interface"
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { FillUserFields } from "../../../../helpers/forReg/fillUserFields/fillUserFields";
import { CheckFields } from "../../../../helpers/forReg/checkFields/checkFields";
import { inputFields } from "../../../../helpers/forReg/inputFields/inputFields";

export const SignUp = () => {

    const [indexField, setIndexField] = useState(0);
    const [showButtons, setShowButtons] = useState(false)
    const [inputValue, setInputValue] = useState("")
    const navigate = useNavigate();
    const [newUser] = useState<IUser>({
        fname: "",
        lname: "",
        email: "",
        password: "",
        bday: new Date()
    });    
    
    const RegisterNewUserPost = async () => {
        const response = await axios.request({
            url: "http://localhost:8080/api/user/register",
            method: "post",
            headers: {
                'Content-Type': 'application/json'
            },
            data: newUser
        });
        localStorage.setItem('accessToken', response.data)
        
    };
    
    const handleClickFinalRegistration = () => {
        if (CheckFields(inputValue, inputFields.length - 1)) {
            FillUserFields(newUser, inputFields.length - 1, inputValue);
            RegisterNewUserPost().then((status) => {
                console.log('Status from Post:', status);
            }).catch(() => {
                alert('Such a user already exists. Please check your data');
            });
            navigate(`/auth`)
        } else {
            alert("Fill fields correctly");
        }
    };

    const handleClickGoToStart = () =>{
        setIndexField(0);
    };

    const handleClickChangeIndex = (value : number) =>{
        if(value < 0){
            indexField + value >= inputFields.length ? setIndexField(3) : setIndexField(indexField + value)
            setShowButtons(false);
        }else{
            if(inputValue){
                if(CheckFields(inputValue, indexField)){
                    indexField + value >= inputFields.length ? setIndexField(3) : setIndexField(indexField + value)
                    setShowButtons(false);
                    FillUserFields(newUser, indexField, inputValue);
                    setInputValue("");
                }else{
                    alert("Fill fields correctly")
                }
            }else{
                alert("Fill filed")
            }
        }
    };

    
    return (
        <div id='containerReg'>
            <h1>Registration</h1>
            <div className="tab">
                <TextAnimation text={inputFields[indexField][0]} setInputValue={setInputValue} showButtons={setShowButtons}/>
            </div>
            {!showButtons ? "" : indexField == inputFields.length - 1 ? <div id="buttons">
                <button onClick={() => handleClickChangeIndex(-1)}>Last stage</button>
                <button onClick={() => handleClickGoToStart()}>To start</button>
                <button onClick={() => handleClickFinalRegistration()}>Final registration</button>
            </div> : indexField == 0 ? <div id="buttons">
                <button onClick={() => handleClickChangeIndex(1)} style={{marginLeft:"auto"}}>Next stage</button>
            </div> : <div id="buttons">
                <button onClick={() => handleClickChangeIndex(-1)}>Last stage</button>
                <button onClick={() => handleClickGoToStart()}>To start</button>
                <button onClick={() => handleClickChangeIndex(1)}>Next stage</button>
            </div>}
        </div>
    );
};