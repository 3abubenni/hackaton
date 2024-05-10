import { useState } from "react";
import "../styles/ModalInvitePersonstyles.css"
import { IMemberList } from "../../../../entities/Items.interface";
import { IoSearchOutline } from "react-icons/io5";
import axios from "axios";

export const MyModalInvitePersonstyles = () => {

    const [members, setMembers] = useState<IMemberList>({
        children:[
        ]
    })

    const [serachedUser, setSearchedUser] = useState(''); 

    const GetUsersByEmailOrName = async() =>{
        const accessToken = localStorage.getItem('accessToken')
        const response = await axios.request({
            url: `http://localhost:8080/api/user/search?query=${serachedUser}`,
            method: 'get',
            headers: {
                Authorization: `${accessToken}`
            }
        })
        setFilteredMembers({ children: response.data})
    }

    const FindUsers = () => {
        GetUsersByEmailOrName()
    }

    const [filteredMembers, setFilteredMembers] = useState(members)

    const SearchMember = (event : React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = event.target.value;
        setSearchedUser(searchTerm);
        const filteredData = members.children.filter((item) => item.fname.toLowerCase().includes(searchTerm.toLowerCase()) || item.lname.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredMembers({ children: filteredData });
    }


    const InviteUser = async(userId: number) =>{
        const accessToken = localStorage.getItem('accessToken')
        const clanId = sessionStorage.getItem('userClanId')
        const response = axios.request({
            url: `http://localhost:8080/api/invite/clan/${clanId}/user/${userId}`,
            method: 'post',
            headers: {
                Authorization: `${accessToken}`
            }
        })

        console.log(response)
    }


    const handleClickInviteUser = (userId: number) =>{
        InviteUser(userId),
        RemoveFromUserList(userId)
    }

    const RemoveFromUserList = (index : number) => {
        console.log(filteredMembers)
        const afterRemoveData = filteredMembers.children.filter(member_inList => index !== member_inList.id);
        const updatedAfterRemoveData = afterRemoveData.map((member, index) => ({
            ...member,
            id: index,
        }));
        setMembers({ children: updatedAfterRemoveData });
        setFilteredMembers({ children: updatedAfterRemoveData });
    };

    return (
        <div className="myModalView_invite">
            <h1>Persons</h1>
            <div className="searchConatiner">
                    <div className="searchIcon">
                        <IoSearchOutline />
                    </div>
                    <div>{">"}</div>
                    <input type="text" onChange={SearchMember}/>
                </div>
            <div className="membersContainer">
                {filteredMembers.children.map((item) =>
                            <div className="member">
                                <p>{item.fname}</p>
                                <p>{item.lname}</p>
                                <p>{item.email}</p>
                                <button onClick={() => handleClickInviteUser(item.id)}>Invite</button>
                            </div>
                    )}
            </div>
            <button onClick={FindUsers}>Find persons</button>
        </div>
    );
};