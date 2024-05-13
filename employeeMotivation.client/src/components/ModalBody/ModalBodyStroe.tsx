import axios from 'axios';
import React, { FC, useState } from 'react';

const ModalBodyStore : FC<{closeModal: () => void}> = ({closeModal}) => {

    const [name, setName] = useState('')
    const [amount, setAmount] = useState(0)
    const [cost, setCost] = useState(0)
    const [description, setDescription] = useState('')


    const handleChangeName = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setName(event.target.value)
    }

    const handleChangeDescription = (event : React.ChangeEvent<HTMLTextAreaElement>) =>{
        setDescription(event.target.value)
    }

    const handleChangeCostItems = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setCost(Number(event.target.value))
    }

    const handleChangeAmount = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setAmount(Number(event.target.value))
    }
    const handleClickAddProduct = () =>{
        if(name.length > 3 && description.length > 10 && amount > 0 && cost > 0){
            AddProductToStore().then(() => closeModal()).catch((error) =>{
                if(error.response.status === 403){
                    alert(`You cant add product`)
                }
            });
        }else{
            alert('Fill fields correctly')
        }
    }

    const AddProductToStore = async() =>{   
        const accessToken = localStorage.getItem('accessToken')
        const userClanId = sessionStorage.getItem('userClanId')
        const response = await axios.request({
            url: `http://localhost:8080/api/item/clan/${userClanId}`,
            method: 'post',
            data: {
                name: name,
                description: description,
                cost: cost,
                amount: amount,
            },
            headers: {
                Authorization: `${accessToken}`
            }
        })
        console.log(response)
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
            <label htmlFor="">Enter cost</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="text" onChange={handleChangeCostItems}/>
            </div>
            <label htmlFor="">Enter amount</label>
            <div className="inputValue">
                <div id='cursor'>{">"}</div>
                <input type="text" onChange={handleChangeAmount}/>
            </div>

            <button onClick={handleClickAddProduct}>Add product</button>

        </>
    );
};

export default ModalBodyStore;