import { useState } from "react";
import { IoSearchOutline } from "react-icons/io5";
import { StoreItem } from "../../StoreItem/app/StoreItem";
import "../styles/Storestyles.css";
import Modal from 'react-modal';
import { MyModal } from "../../../../MyModal/Modal/app/MyModal";
import { IStoreList } from "../../../../../entities/Items.interface";

export const Store = () => {

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

    const [modalIsOpen, setModalIsOpen] = useState(false);

    const openModal = () => {
        setModalIsOpen(true);
    };
    const closeModal = () => {
        setModalIsOpen(false);
    };

    const [storeList] = useState<IStoreList>({
        children:[
            {image:"" ,name: "худи"},
            {image:"" ,name: "брюки"},
            {image:"" ,name: "очки"},

        ]
    })

    const [filteredStore, setFilteredStore] = useState<IStoreList>(storeList);

    const SearchStoreItem = (event : React.ChangeEvent<HTMLInputElement>) => {
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
                    <input type="text" onChange={SearchStoreItem}/>   
                </div>
                <button onClick={openModal}>Add store item</button>
                <div id="storeContainer">
                    {filteredStore.children.map((item, index) =>
                        <StoreItem key={index} name={item.name} image={item.image}/>
                    )}
                </div>
            </div>
        </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            style={customStyles}
            >
                <MyModal inputValue="Enter name of product"/>
            </Modal>
        </>
    );
};
