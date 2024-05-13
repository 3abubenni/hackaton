import axios from "axios";
import { FC, useState } from "react";
import { IStoreItem } from "../../entities/Items.interface";

const ModalBodyViewStore: FC<{item : IStoreItem} & {type: string} & {remove :(item : IStoreItem) => void} & {closeModal :() => void}> = ({item, type, closeModal}) => {

    const [amount, setAmount] = useState(0)

    const BuyProduct = async() =>{
        const accessToken = localStorage.getItem('accessToken')
        const response = await axios.request({
            url: `http://localhost:8080/api/shop/buy/item/${item.id}?amount=${amount}`,
            method: 'post',
            headers:{
                Authorization: `${accessToken}`,
            }
        })

        console.log(response)
    }

    const handleClickBuyProduct = () =>{
        if(!isNaN(amount)){
            BuyProduct().then(() => closeModal()).catch((error) =>{
                if(error.response.status === 406){
                    alert(`You haven't enough money`)
                }else{
                    if(error.response.status === 403){
                        alert(`You haven't clan`)
                    }
                }
            })
        }else{
            alert(`Enter correct amount`)
        }
        
    }

    const handleChagneAmount = (event: React.ChangeEvent<HTMLInputElement>) =>{
        setAmount(Number(event.target.value))
    }

    return (
        <div className="input">
        <label htmlFor="">{'Name of product'}</label>
        <div className="inputValue">
            <div id='cursor'>{">"}</div>
            <input type="text" readOnly={true} value={item.name}/>
        </div>
        <label htmlFor="">Description {'(more then 10 symbols)'}</label>
        <div className="inputValue" id="description">
            <div id='cursor'>{">"}</div>
            <textarea name="" id="task_description" readOnly={true} value={item.description}></textarea>
        </div>
        <label htmlFor="">Count of items in stock</label>
        <div className="inputValue">
            <div id='cursor'>{">"}</div>
            <input type="text" readOnly={true} value={item.count}/>
        </div>
        <label htmlFor="">Enter amount of items</label>
        <div className="inputValue">
            <div id='cursor'>{">"}</div>
            <input type="text" onChange={handleChagneAmount}/>
        </div>

        {type === 'buy' ? <button onClick={handleClickBuyProduct}>Buy</button> : ""}
    </div>
    );
};

export default ModalBodyViewStore;