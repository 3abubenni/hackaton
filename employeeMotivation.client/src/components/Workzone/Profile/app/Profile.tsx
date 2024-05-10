import { useRef, useState, useEffect } from "react";
import "../styles/Profilestyles.css"
import { FaPen, FaRegUserCircle } from "react-icons/fa";
import axios from "axios";
import { IUser } from "../../../../entities/User.interface";
import { Clan } from "../../ClanList/Clan/app/Clan";
import { IClanList } from "../../../../entities/Items.interface";

export const Profile = () => {

    const [editInputs, setEditInputs] = useState(false);
    const [image] = useState<File | undefined>(undefined)
    const inputRef = useRef<HTMLInputElement>(null);
    const [userBday, setUserBday] = useState("");
    const [clans, setClans] = useState<IClanList>({
        children: [
            {id:0, name:'Красивые'},
            {id:1, name: 'Cool Boys'},
            {id:2, name: 'Фанаты Полуяна'},
        ]
    })
    const [userData, setUserData] = useState<IUser>({
        fname: "",
        lname: "",
        bday: new Date(),
        email: "",
        password: "",
    })

    useEffect(() => {
        const getUserInf = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const response = await axios.request({
                url: `http://localhost:8080/api/user`,
                method: 'get',
                headers:{
                    Authorization: `${accessToken}`
                }
            })
            userData.bday = response.data.bday
            userData.email = response.data.email
            userData.fname = response.data.fname
            userData.lname = response.data.lname
            setUserBday(userData.bday.toString().split('T')[0])
        }

        getUserInf();
    }, [])

    const handleClickStartEdit = () =>{
        setEditInputs(!editInputs)
    }

    const UpdateUserData = async() =>{
        const accessToken = localStorage.getItem('accessToken')
        const response = await axios.request({
            url: 'http://localhost:8080/api/user',
            method: 'put',
            data: {
                fname: userData.fname,
                lname: userData.lname,
                bday: userData.bday,
            },
            headers: {
                Authorization: `${accessToken}`
            }
        })

        console.log(response)
    }

    const CheckWrongDate = (userDate : Date) =>{
        const today = new Date()
        if(userDate.getFullYear() >= today.getFullYear()-10) return true
        return false
    }

    
    const handleClickSaveEdit = () =>{
        try{
            const newUserBday = new Date(userBday)
            if(userBday.toString().split('-').length !== 3 || userBday.toString().split('-')[2] === '' || userBday.toString().split('-')[1] === '' || userBday.toString().split('-')[0] === '' || CheckWrongDate(newUserBday) || userData.fname.length < 2 || userData.lname.length < 2){
                alert('Fill fields correctly')
            }else{
                setUserData({...userData, bday: newUserBday})
                UpdateUserData()
                setEditInputs(!editInputs)
            }
        }catch{
            alert('Fill fields correctly')
        }
    }

    const handleClickImage = () =>{
        if (inputRef.current && editInputs) {
            inputRef.current.click();
        } else {
            alert("Can not without Edit Mode")
        }
    };

    const handleClickChangeLogo = () => {
        alert('You cant change image')
    };

    const handleChangeFName = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setUserData({ ...userData, fname: event.target.value });
    }
    const handleChangeLName = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setUserData({ ...userData, lname: event.target.value });
    }
    const handleChangeBday= (event : React.ChangeEvent<HTMLInputElement>) =>{
        setUserBday(event.target.value)
    }

    return (
        <div className="mainView">
            <div className="profileContent">
                <h1>Your profile</h1>
                <div className="image">
                    {image ? <img src={URL.createObjectURL(image)} alt="" onClick={handleClickImage}/> : <div id="iconUser" onClick={handleClickImage}><FaRegUserCircle /></div>}
                </div>
                <input type="file" ref={inputRef} onChange={handleClickChangeLogo} className="hiddenInput"/>
                <ul>
                    <li>
                        <div className="item">
                            <label htmlFor="">Your email {">"} </label>
                            <input type="text" readOnly={true} value={userData.email}/>
                        </div>
                    </li>
                    <li>
                        <div className="item">
                            <label htmlFor="">Your birthday {">"} </label>
                            <input type="text"  readOnly={!editInputs} value={userBday} onChange={handleChangeBday}/> {editInputs ? <FaPen /> : ""}
                        </div>
                    </li>
                    <li>
                        <div className="item">
                            <label htmlFor="">Your first name {">"}</label>
                            <input type="text" readOnly={!editInputs} value={userData.fname} onChange={handleChangeFName}/> {editInputs ? <FaPen /> : ""}
                        </div>
                    </li>
                    <li>
                        <div className="item">
                            <label htmlFor="">Your last name {">"} </label>
                            <input type="text" readOnly={!editInputs} value={userData.lname} onChange={handleChangeLName}/> {editInputs ? <FaPen /> : ""}
                        </div>
                    </li>
                </ul>
                <div className="buttons">
                    <button disabled={!editInputs} onClick={handleClickSaveEdit}>Save edit</button>
                    <button onClick={handleClickStartEdit}>Edit</button>
                </div>
                <div id="clansContainer">
                        {clans.children.map((item, index) =>
                            <Clan id={index} key={index} name={item.name}/>
                        )}
                </div>
            </div>
        </div>
    );
};
