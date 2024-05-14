import { FC, useEffect, useState } from 'react';
import Modal from 'react-modal';
import "../styles/Memberstyles.css"
import { IMember } from '../../../../../entities/Items.interface';
import { MyModalClanMember } from '../../../../MyModal/MyModalClanMember/app/MyModalClanMember';
import { customStyles } from '../../../../../helpers/styles/customStyleModal';
import axios from 'axios';

export const Member : FC<IMember & {remove: (member : IMember) => void}> = ({id, userId, remove, placeInRating}) => {

    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [fname, setFName] = useState("");
    const [lname, setLName] = useState("");

    useEffect(() =>{
        const getUserData = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const response = await axios.request({
                url: `http://localhost:8080/api/user/${userId}`,
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`,
                }
            })
            console.log('Userid', id, response)
            setFName(response.data.fname)
            setLName(response.data.lname)
        }

        getUserData();
    })
    
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
                    <p id='place'>{placeInRating}</p>
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