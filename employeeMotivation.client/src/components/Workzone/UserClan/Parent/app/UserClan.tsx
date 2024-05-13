import { useEffect, useState } from "react";
import { IoSearchOutline } from "react-icons/io5";
import Modal from 'react-modal';
import "../styles/UserClanstyles.css"
import { Member } from "../../Members/app/Member";
import { IMember, IMemberList } from "../../../../../entities/Items.interface";
import { MyModalInvitePersons } from "../../../../MyModal/ModalIvitePersons/app/ModalInvitePersons";
import axios from "axios";
import { customStyles } from "../../../../../helpers/styles/customStyleModal";

export const UserClan = () => {

    const [modalIsOpen, setModalIsOpen] = useState(false);

    const [members, setMembers] = useState<IMemberList>({
        children:[

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
        }

        const getClanInfo = async(userClanId : number) =>{
            const accessToken = localStorage.getItem('accessToken')
            const clanId = userClanId
            await axios.request({
                url: `http://localhost:8080/api/clan/${clanId}`,
                method: 'get',
                headers:{
                    Authorization: `${accessToken}`
                }
            })
        }

        getUserClans().then((clanid) => {
            if(clanid){
                getClanInfo(clanid);
            }
        });
    }, [])

    const [filteredMembers, setFilteredMembers] = useState(members)

    const handleChangeSearchMember = (event : React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = event.target.value;
        const filteredData = members.children.filter((item) => item.fname.toLowerCase().includes(searchTerm.toLowerCase()) || item.lname.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredMembers({ children: filteredData });
    }

    const handleClickOpenModal = () => {
        setModalIsOpen(true);
    };

    const handleClickCloseModal = () => {
        setModalIsOpen(false);
    };

    const handleClickRemoveMember = (member : IMember) => {
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
                    <input type="text" onChange={handleChangeSearchMember}/>   
                </div>
                <button onClick={handleClickOpenModal}>Invite new member</button>
                <div id="membersContainer">
                    {filteredMembers.children.map((item, index) =>
                            <Member id={index} fname={item.fname} lname={item.lname} remove={handleClickRemoveMember}/>
                        )}
                </div>
            </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={handleClickCloseModal}
            style={customStyles}
            >
                <MyModalInvitePersons/>
            </Modal>
        </div>
    );
};