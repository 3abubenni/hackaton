import axios from 'axios';
import React, { FC, useState } from 'react';

const ModalBodyStore : FC<{closeModal: () => void}> = ({closeModal}) => {

    const [name, setName] = useState('')
    const [amount, setAmount] = useState(0)
    const [countItems, setCountItems] = useState(0)
    const [image, setImage] = useState('')
    const [description, setDescription] = useState('')


    const handleChangeName = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setName(event.target.value)
    }

    const handleChangeDescription = (event : React.ChangeEvent<HTMLTextAreaElement>) =>{
        setDescription(event.target.value)
    }

    const handleChangeCountOfItems = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setCountItems(Number(event.target.value))
    }

    const handleChangeAmount = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setAmount(Number(event.target.value))
    }

    const handleChangeImage = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setImage(event.target.value)
    }

    const handleClickAddProduct = () =>{
        if(name.length > 3 && description.length > 10 && amount > 0 && countItems > 0){
            AddProductToStore().then(() => closeModal());
        }else{
            alert('Fill fields correctly')
        }
    }

    const AddProductToStore = async() =>{   
        const accessToken = localStorage.getItem('accessToken')
        const userClanId = sessionStorage.getItem('userClanId')
        const response = await axios.request({
            url: `http://localhost:8080/api/shop/add/item/${userClanId}?amount=${amount}`
        })
    }

    return (
        <>
            <label htmlFor="">{'Enter name of product (more then 3 symbols)'}</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="text" onChange={handleChangeName}/>
            </div>
            <label htmlFor="">Enter description {'(more then 10 symbols)'}</label>
            <div className="inputValue" id="description">
                <div id='cursor'>{">"}</div>
                <textarea name="" id="task_description" onChange={handleChangeDescription}></textarea>
            </div>
            <label htmlFor="">Enter count of items in stock</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="text" onChange={handleChangeCountOfItems}/>
            </div>
            <label htmlFor="">Enter amount</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="text" onChange={handleChangeAmount}/>
            </div>
            <label htmlFor="">Choose image</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="file" onChange={handleChangeImage}/>
            </div>

            <button onClick={handleClickAddProduct}>Add product</button>

        </>
    );
};

export default ModalBodyStore;