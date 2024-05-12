import { useEffect, useState } from "react";
import { Clan } from "../../Clan/app/Clan";
import "../styles/ClanListstyles.css"
import { IoSearchOutline } from "react-icons/io5";
import Modal from 'react-modal';
import { MyModal } from "../../../../MyModal/Modal/app/MyModal";
import { IClanList } from "../../../../../entities/Items.interface"
import { customStyles } from "../../../../../helpers/styles/customStyleModal";
import { FilterTextForClan } from "../../../../../functions/filterText/FilterText";
import axios from "axios";

export const ClanList = () => {
    
    const [clans] = useState<IClanList>({
        children: [
        ]
    });

    useEffect(() =>{
        const getAllClans = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const query = ""
            const response = await axios.request({
                url: `http://localhost:8080/api/clan/search/?query=${query}`,
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`,
                }
            })

            console.log(response)
        }

        getAllClans();
    })

    const [filteredClans, setFilteredClans] = useState<IClanList>(clans);
    
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const handleClickOpenModal = () => {
        setModalIsOpen(true);
    };

    const handleClickCloseModal = () => {
        setModalIsOpen(false);
    };

    const handleChangeSearchClan = (event : React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = event.target.value;
        const filteredData = clans.children.filter((item) => item.name.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredClans({ children: filteredData });
    }

    return (
        <>
            <div className="mainView">
                <div className="clanListMainView">
                    <div className="searchConatiner">
                        <div className="searchIcon">
                            <IoSearchOutline />
                        </div>
                        <div>{">"}</div>
                        <input type="text" onChange={handleChangeSearchClan}/>   
                    </div>
                    <button onClick={handleClickOpenModal}>Add clan</button>
                    <div id="clansContainer">
                        {FilterTextForClan(filteredClans.children).map((item, index) =>
                            <Clan id={index} key={index} name={item.name} type="show_join"/>
                        )}
                    </div>
                </div>
            </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={handleClickCloseModal}
            style={customStyles}
            >
                <MyModal type="createClan" closeModal={handleClickCloseModal}/>
            </Modal>
        </>
    );
};
