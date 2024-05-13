import { FC, useState } from "react";
import { IStoreItem } from "../../../../../entities/Items.interface";
import { customStyles } from "../../../../../helpers/styles/customStyleModal";
import "../styles/StoreItemstyles.css"
import Modal from 'react-modal';
import { MyModalStoreItem } from "../../../../MyModal/ModalItems/StoreItem/app/MyModalStoreItem"

export const StoreItem : FC<IStoreItem & { remove: (item : IStoreItem) => void } & {type : string} & {wasChange : (value : boolean) => void}> = ({id, name, description, remove, type, cost, count ,amount, wasChange}) => {

    const [modalIsOpen, setModalIsOpen] = useState(false);
    
    const handleClickOpenModal = () => {
        setModalIsOpen(true);
    };
    const handleClickCloseModal = () => {
        setModalIsOpen(false);
        wasChange(true)
    };

    return (
        <>
            <div className="storeItem" onClick={handleClickOpenModal}>
                <div id="storeItemContent">
                    <p>{name}</p>
                </div>
            </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={handleClickCloseModal}
            style={customStyles}
            >
                <MyModalStoreItem id={id} name={name} description={description} closeModal={handleClickCloseModal} type={type} remove={remove} cost={cost} amount={amount} count={count}/>
            </Modal>
        </>
    );
};