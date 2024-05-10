import { useEffect, useState } from "react";
import { IoSearchOutline } from "react-icons/io5";
import Modal from 'react-modal';
import "../styles/UserClanstyles.css"
import { Member } from "../../Members/app/Member";
import { IMember, IMemberList } from "../../../../../entities/Items.interface";
import { MyModalInvitePersonstyles } from "../../../../MyModal/ModalIvitePersons/app/ModalInvitePersons";
import axios from "axios";

export const UserClan = () => {

    const [modalIsOpen, setModalIsOpen] = useState(false);

    const [members, setMembers] = useState<IMemberList>({
        children:[
            {id: 0, fname: 'Arseniy', lname: 'Korolev', email:'korol@mail.ru'},
            {id: 1, fname: 'Ivan', lname: 'Kuznetsov', email:'kuz@mail.ru'},
            {id: 2, fname: 'Arthur', lname: 'Shayahmetov', email:'shaya@mail.ru'},
            {id: 3, fname: 'Vladislav', lname: 'Couch', email:'couch@mail.ru'},
        ]
    })

    useEffect(() =>{
        const getUserClans = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const response = await axios.request({
                url: `http://localhost:8080/api/clan/user`,
                method: 'get',
                headers:{
                    Authorization: `${accessToken}`
                }
            })

            if(response.data.length > 0){
                sessionStorage.setItem('userClanId', response.data[0].id)
                return response.data[0].id;
            }

            console.log(response)
        }

        const getClanInfo = async(userClanId : number) =>{
            const accessToken = localStorage.getItem('accessToken')
            const clanId = userClanId
            const response = await axios.request({
                url: `http://localhost:8080/api/clan/${clanId}`,
                method: 'get',
                headers:{
                    Authorization: `${accessToken}`
                }
            })

            console.log(response)
        }

        getUserClans().then((clanid) => {
            if(clanid){
                getClanInfo(clanid);
            }
        });
    })

    const customStyles = {
        content: {
            top: '50%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)',
            backgroundColor: 'rgb(22, 62, 73)',
            borderRadius: '20px',
        },
        overlay: {
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            transition: 'background-color 0.5s ease'
        }
    };

    const [filteredMembers, setFilteredMembers] = useState(members)

    const SearchMember = (event : React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = event.target.value;
        const filteredData = members.children.filter((item) => item.fname.toLowerCase().includes(searchTerm.toLowerCase()) || item.lname.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredMembers({ children: filteredData });
    }

    const openModal = () => {
        setModalIsOpen(true);
    };
    const closeModal = () => {
        setModalIsOpen(false);
    };

    const RemoveMember = (member : IMember) => {
        const afterRemoveData = filteredMembers.children.filter(member_inList => member.id !== member_inList.id);
        const updatedAfterRemoveData = afterRemoveData.map((member, index) => ({
            ...member,
            id: index,
        }));
        setMembers({ children: updatedAfterRemoveData });
        setFilteredMembers({ children: updatedAfterRemoveData });
    };

    return (
        <div className="mainView">
            <div className="membersListMainView">
                <div className="searchConatiner">
                    <div className="searchIcon">
                        <IoSearchOutline />
                    </div>
                    <div>{">"}</div>
                    <input type="text" onChange={SearchMember}/>   
                </div>
                <button onClick={openModal}>Invite new member</button>
                <div id="membersContainer">
                    {filteredMembers.children.map((item, index) =>
                            <Member id={index} fname={item.fname} lname={item.lname} email={item.email} remove={RemoveMember}/>
                        )}
                </div>
            </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            style={customStyles}
            >
                <MyModalInvitePersonstyles/>
            </Modal>
        </div>
    );
};