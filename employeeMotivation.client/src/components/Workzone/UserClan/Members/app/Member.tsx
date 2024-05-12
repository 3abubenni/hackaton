import { FC, useState } from 'react';
import Modal from 'react-modal';
import "../styles/Memberstyles.css"
import { IMember } from '../../../../../entities/Items.interface';
import { MyModalClanMember } from '../../../../MyModal/MyModalClanMember/app/MyModalClanMember';
import { customStyles } from '../../../../../helpers/styles/customStyleModal';

export const Member : FC<IMember & {remove: (member : IMember) => void}> = ({id, fname, lname, remove}) => {

    const [modalIsOpen, setModalIsOpen] = useState(false);
    
    const handleClickOpenModal = () => {
        setModalIsOpen(true);
    };
    const handleClickCloseModal = () => {
        setModalIsOpen(false);
    };

    return (
        <>
            <div id="memberItem" onClick={handleClickOpenModal}>
                <div className="memberItemElements">
                    <p>{fname}</p>
                    <p>{lname}</p>
                </div>
            </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={handleClickCloseModal}
            style={customStyles}
            >
                <MyModalClanMember id={id} fname={fname} lname={lname} remove={remove} closeModal={handleClickCloseModal}/>
            </Modal>
        </>
        
    );
};