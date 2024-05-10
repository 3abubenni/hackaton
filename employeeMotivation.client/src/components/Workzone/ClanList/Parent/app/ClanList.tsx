import { useState } from "react";
import { Clan } from "../../Clan/app/Clan";
import "../styles/ClanListstyles.css"
import { IoSearchOutline } from "react-icons/io5";
import Modal from 'react-modal';
import { MyModal } from "../../../../MyModal/Modal/app/MyModal";
import {IClan, IClanList} from "../../../../../entities/Items.interface"

export const ClanList = () => {

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
            transition: 'background-color 0.5s ease'  // добавляем анимацию изменения цвета фона
        }
    };
    
    const [clans] = useState<IClanList>({
        children: [
            {id:0, name:'Красивые'},
            {id:1, name: 'Cool Boys'},
            {id:2, name: 'Фанаты Полуяна'},

        ]
    });
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };
    const [filteredClans, setFilteredClans] = useState<IClanList>(clans);

    const FilterText = (filteredData : IClan[]) =>{
        return filteredData.map((item) => {
            if (item.name.length > 42) {
                return { name: item.name.slice(0, 35) + "..." };
            } else {
                return item;
            }
        });
    }

    const SearchClan = (event : React.ChangeEvent<HTMLInputElement>) => {
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
                        <input type="text" onChange={SearchClan}/>   
                    </div>
                    <button onClick={openModal}>Add clan</button>
                    <div id="clansContainer">
                        {FilterText(filteredClans.children).map((item, index) =>
                            <Clan id={index} key={index} name={item.name}/>
                        )}
                    </div>
                </div>
            </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            style={customStyles}
            >
                <MyModal inputValue="Enter clan name"/>
            </Modal>
        </>
    );
};
