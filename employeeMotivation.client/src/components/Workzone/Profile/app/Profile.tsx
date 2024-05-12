import {useState, useEffect } from "react";
import "../styles/Profilestyles.css"
import { FaPen, FaRegUserCircle } from "react-icons/fa";
import axios from "axios";
import { IUser } from "../../../../entities/User.interface";
import { Clan } from "../../ClanList/Clan/app/Clan";
import { IClanList } from "../../../../entities/Items.interface";
import { CheckWrongDate } from "../../../../helpers/checkWrongDate/checkWrongDate";

export const Profile = () => {

    const [editInputs, setEditInputs] = useState(false);
    const [userBday, setUserBday] = useState("");
    const [clans, setClans] = useState<IClanList>({
        children: [
        ]
    })

    const [userData, setUserData] = useState<IUser>({
        fname: "",
        lname: "",
        bday: new Date(),
        email: "",
        password: "",
        money: -1,
        exp: -1,
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
            // userData.bday = response.data.bday
            setUserData({
                fname:  response.data.fname,
                lname: response.data.lname,
                email: response.data.email,
                password: response.data.password,
                bday: new Date(),
            })
            // setUserBday(userData.bday.toString().split('T')[0])
        }

        const getMemberClanInf = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const response = await axios.request({
                url: `http://localhost:8080/api/member/user`,
                method: 'get',
                headers:{
                    Authorization: `${accessToken}`
                }
            })

            setUserData(prevUserData => ({
                ...prevUserData,
                money: response.data[0].money,
                exp: response.data[0].exp
            }));
        }

        const getUsersClans = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const response = await axios.request({
                url: "http://localhost:8080/api/clan/user",
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`
                }
            })
            sessionStorage.setItem('userClanId', response.data[0].id)
            setClans({children : response.data})
        }

        getUserInf();

        getMemberClanInf();

        getUsersClans();


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

    
    const handleClickSaveEdit = () =>{
        try{
            const newUserBday = new Date(userBday)
            if(userBday.toString().split('-').length !== 3 || userBday.toString().split('-')[2] === '' || userBday.toString().split('-')[1] === '' 
            || userBday.toString().split('-')[0] === '' || CheckWrongDate(newUserBday) || userData.fname.length < 2 
            || userData.lname.length < 2){
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

    const handleChangeFName = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setUserData({ ...userData, fname: event.target.value });
    }
    const handleChangeLName = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setUserData({ ...userData, lname: event.target.value });
    }
    const handleChangeBday= (event : React.ChangeEvent<HTMLInputElement>) =>{
        setUserBday(event.target.value)
    }

    console.log(userData)

    return (
        <div className="mainView">
            <div className="profileContent">
                <h1>Your profile</h1>
                <div className="image">
                    <div id="iconUser"><FaRegUserCircle /></div>
                </div>
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
                    <li>
                        <div className="item">
                            <label htmlFor="">Your money {">"} </label>
                            <input type="text" readOnly={true} value={userData.money} onChange={handleChangeLName}/>
                        </div>
                    </li>
                    <li>
                        <div className="item">
                            <label htmlFor="">Your experience {">"} </label>
                            <input type="text" readOnly={true} value={userData.exp} onChange={handleChangeLName}/>
                        </div>
                    </li>
                </ul>
                <div className="buttons">
                    <button disabled={!editInputs} onClick={handleClickSaveEdit}>Save edit</button>
                    <button onClick={handleClickStartEdit}>Edit</button>
                </div>
                <div id="clansContainer">
                        {clans.children.map((item, index) =>
                            <Clan id={index} key={index} name={item.name} type="show"/>
                        )}
                </div>
            </div>
        </div>
    );
};
