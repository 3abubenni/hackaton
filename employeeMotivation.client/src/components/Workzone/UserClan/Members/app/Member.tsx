import { FC, useState } from 'react';
import Modal from 'react-modal';
import "../styles/Memberstyles.css"
import { IMember } from '../../../../../entities/Items.interface';
import { MyModalClanMember } from '../../../../MyModal/MyModalClanMember/app/MyModalClanMember';

export const Member : FC<IMember & {remove: (member : IMember) => void}> = ({id, fname, lname, remove}) => {

    const [modalIsOpen, setModalIsOpen] = useState(false);

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
    
    const openModal = () => {
        setModalIsOpen(true);
    };
    const closeModal = () => {
        setModalIsOpen(false);
    };

    return (
        <>
            <div id="memberItem" onClick={openModal}>
                <div className="memberItemElements">
                    <p>{fname}</p>
                    <p>{lname}</p>
                </div>
            </div>
            <Modal
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            style={customStyles}
            >
                <MyModalClanMember id={id} fname={fname} lname={lname} remove={remove} closeModal={closeModal}/>
            </Modal>
        </>
        
    );
};