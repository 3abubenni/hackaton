import { useEffect, useState } from "react";
import { IoSearchOutline } from "react-icons/io5";
import { StoreItem } from "../../StoreItem/app/StoreItem";
import "../styles/Storestyles.css";
import Modal from 'react-modal';
import { MyModal } from "../../../../MyModal/Modal/app/MyModal";
import { IStoreList } from "../../../../../entities/Items.interface";
import { customStyles } from "../../../../../helpers/styles/customStyleModal";
import axios from "axios";

export const Store = () => {

    const [modalIsOpen, setModalIsOpen] = useState(false);

    useEffect(() => {
        const getShopItems = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const userClanId = sessionStorage.getItem('userClanId')
            const response = await axios.request({
                url: `http://localhost:8080/api/shop/clan/item/${userClanId}`,
                method: `get`,
                headers: {
                    Authorization: `${accessToken}`,
                }
            })
            
            console.log(response)
        }

        getShopItems();
    })

    const [storeList] = useState<IStoreList>({
        children:[
        ]
    })

    const handleClickOpenModal = () => {
        setModalIsOpen(true);
    };
    
    const handleClickCloseModal = () => {
        setModalIsOpen(false);
    };

    const [filteredStore, setFilteredStore] = useState<IStoreList>(storeList);

    const handleChangeSearchStoreItem = (event : React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = event.target.value;
        const filteredData = storeList.children.filter((item) => item.name.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredStore({ children: filteredData });
    }

    return (
        <>
        <div className="mainView">
            <div className="storeListMainView">
                <div className="searchConatiner">
                    <div className="searchIcon">
                        <IoSearchOutline />
                    </div>
                    <div>{">"}</div>
                    <input type="text" onChange={handleChangeSearchStoreItem}/>   
                </div>
                <button onClick={handleClickOpenModal}>Add store item</button>
                <div id="storeContainer">
                    {filteredStore.children.map((item, index) =>
                        <StoreItem key={index} id={item.id} name={item.name} description={item.description} cost={item.cost} amount={item.amount}/>
                    )}
                </div>
            </div>
        </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={handleClickCloseModal}
            style={customStyles}
            >
                <MyModal type="store" closeModal={handleClickCloseModal}/>
            </Modal>
        </>
    );
};
