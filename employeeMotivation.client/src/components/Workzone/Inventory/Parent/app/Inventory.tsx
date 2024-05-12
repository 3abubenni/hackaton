import { useEffect, useState } from "react";
import { IoSearchOutline } from "react-icons/io5";
import "../styles/InventoryListstyles.css"
import { InventoryItem } from "../../InventoryItem/app/InventoryItem";
import { IInventoryList } from "../../../../../entities/Items.interface";
import axios from "axios";

export const Inventory = () => {

    const [inventoryList] = useState<IInventoryList>({
        children:[
        ]
    })

    useEffect(() =>{
        const getUsersInventoryItem = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const userClanId = sessionStorage.getItem('userClanId')
            const response = await axios.request({
                url: `http://localhost:8080/api/item/member/${userClanId}`,
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`,
                }
            })
            
            console.log(response)
        }

        getUsersInventoryItem();
    })

    const [filteredInventory, setFilteredInventory] = useState<IInventoryList>(inventoryList);

    const handleChangeSearchStoreItem = (event : React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = event.target.value;
        const filteredData = storeList.children.filter((item) => item.name.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredStore({ children: filteredData });
    }

    return (
        <div className="mainView">
            <div className="inventoryListMainView">
                <div className="searchConatiner">
                    <div className="searchIcon">
                        <IoSearchOutline />
                    </div>
                    <div>{">"}</div>
                    <input type="text" onChange={handleChangeSearchStoreItem}/>   
                </div>
                <div id="inventoryContainer">
                    {filteredInventory.children.map((item, index) =>
                        <InventoryItem key={index} id={item.id} name={item.name} description={item.description}/>
                    )}
                </div>
            </div>
        </div>
    );
};
