import "../styles/SignUpstyles.css"
import { useState } from 'react';
import { TextAnimation } from '../../TextAnimation/app/TextAnimation';
import { IUser } from "../../../../entities/User.interface"
import axios from "axios";
import { useNavigate } from "react-router-dom";

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

    const inputFields : string[][] = [
        ["Your first name (min 2 symbol):", "First name..."],
        ["Your second name (min 2 symbol):", "Second name..."],
        ["Your email:", "Email..."],
        ["Your password (min 8 symbols):", "Password..."],
        ["Your birthday (DD/MM/YYYY):", "Birthday..."],
    ];

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
    
    const FinalRegistration = () => {
        if (CheckFields(inputValue, inputFields.length - 1)) {
            FillUserFields(inputFields.length - 1);
            console.log(newUser)
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

    const FillUserFields = (value: number) => {
        switch(value) {
            case 0:
                newUser.fname = inputValue
                break;
        
            case 1:
                newUser.lname = inputValue;
                break;
    
            case 2:
                newUser.email = inputValue
                break;
    
            case 3:
                newUser.password = inputValue
                break;
        
            case 4:
                newUser.bday = new Date(inputValue)
                break;
            default:
                break;
        }
    }

    const GoToStart = () =>{
        setIndexField(0);
    }

    const CheckWrongDate = (userDate : Date) =>{
        const today = new Date()
        if(userDate.getFullYear() >= today.getFullYear()-10) return true
        return false
    }

    function convertDateFormat(input : string) {
        const parts = input.split('/');
        if (parts.length === 3) {
            const day = parts[0];
            const month = parts[1];
            const year = parts[2];
            const dateInNewFormat = `${year}-${month}-${day}`;
            return dateInNewFormat;
        } else {
            return "Invalid input format";
        }
    }

    const CheckFields = (value : string, index : number) => {
        if(value.length < 2) return false
        if(value.length < 8 && index == 3) return false
        const userInputDate = new Date(convertDateFormat(value));
        console.log(value)
        if((CheckWrongDate(userInputDate) || isNaN(userInputDate.getTime())) && index == 4) return false
        if((!value.includes('@') || value.indexOf('@') == value.length-1) && index == 2) return false
        return true
    }

    const ChangeIndex = (value : number) =>{
        if(value < 0){
            indexField + value >= inputFields.length ? setIndexField(3) : setIndexField(indexField + value)
            setShowButtons(false);
        }else{
            if(inputValue){
                if(CheckFields(inputValue, indexField)){
                    indexField + value >= inputFields.length ? setIndexField(3) : setIndexField(indexField + value)
                    setShowButtons(false);
                    FillUserFields(indexField);
                    setInputValue("");
                }else{
                    alert("Fill fields correctly")
                }
            }else{
                alert("Fill filed")
            }
        }
    }

    
    return (
        <div id='containerReg'>
            <h1>Registration</h1>
            <div className="tab">
                <TextAnimation text={inputFields[indexField][0]} setInputValue={setInputValue} showButtons={setShowButtons}/>
            </div>
            {!showButtons ? "" : indexField == inputFields.length - 1 ? <div id="buttons">
                <button onClick={() => ChangeIndex(-1)}>Last stage</button>
                <button onClick={() => GoToStart()}>To start</button>
                <button onClick={() => FinalRegistration()}>Final registration</button>
            </div> : indexField == 0 ? <div id="buttons">
                <button onClick={() => ChangeIndex(1)} style={{marginLeft:"auto"}}>Next stage</button>
            </div> : <div id="buttons">
                <button onClick={() => ChangeIndex(-1)}>Last stage</button>
                <button onClick={() => GoToStart()}>To start</button>
                <button onClick={() => ChangeIndex(1)}>Next stage</button>
            </div>}
        </div>
    );
};