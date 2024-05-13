import {useState, useEffect } from "react";
import "../styles/Profilestyles.css"
import { FaPen, FaRegUserCircle } from "react-icons/fa";
import axios from "axios";
import { IUser } from "../../../../entities/User.interface";
import { Clan } from "../../ClanList/Clan/app/Clan";
import { IClanList } from "../../../../entities/Items.interface";

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
        bday: "",
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

            console.log(response)

            if(response.data.bday !== null){
                const bday = response.data.bday.split('T')[0]
                const [year, month, day] = bday.split('-')
                setUserData({
                fname:  response.data.fname,
                lname: response.data.lname,
                email: response.data.email,
                password: response.data.password,
                bday: `${day}-${month}-${year}`,
            })

            setUserBday(`${day}-${month}-${year}`)
            }
            setUserData({
                fname:  response.data.fname,
                lname: response.data.lname,
                email: response.data.email,
                password: response.data.password,
                bday: "",
            })
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

            sessionStorage.setItem('userIdInClan', response.data[0].id)
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

    const UpdateUserData = async(bday : string) =>{
        const accessToken = localStorage.getItem('accessToken')
        await axios.request({
            url: 'http://localhost:8080/api/user',
            method: 'put',
            data: {
                fname: userData.fname,
                lname: userData.lname,
                bday: bday,
            },
            headers: {
                Authorization: `${accessToken}`
            }
        })
    }

    
    const handleClickSaveEdit = () =>{
        try{
            console.log('Birthday', userBday);
            const [day, month, year] = userBday.split('-')
            const newBday = `${year}-${month}-${day}`
            if(userData.fname.length > 2 && userData.lname.length > 2){
                setUserData({...userData, bday: newBday});
                UpdateUserData(newBday);
                setEditInputs(!editInputs);
            } else {
                alert('Fill fields correctly');
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
                            <label htmlFor="">Your birthday{'(dd-mm-yyyy)'} {">"} </label>
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
