import { useState } from "react";
import { IoSearchOutline } from "react-icons/io5";
import "../styles/InventoryListstyles.css"
import { InventoryItem } from "../../InventoryItem/app/InventoryItem";

interface InventoryItem {
    image: string;
    name: string;
}

interface InventoryList {
    children: InventoryItem[];
}

export const Inventory = () => {

    const [storeList] = useState<InventoryList>({
        children:[
            {image:"" ,name: "худи"},
            {image:"" ,name: "брюки"},
            {image:"" ,name: "очки"},

        ]
    })

    const [filteredStore, setFilteredStore] = useState<InventoryList>(storeList);

    const SearchStoreItem = (event : React.ChangeEvent<HTMLInputElement>) => {
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
                    <input type="text" onChange={SearchStoreItem}/>   
                </div>
                <div id="inventoryContainer">
                    {filteredStore.children.map((item, index) =>
                        <InventoryItem key={index} name={item.name} image={item.image}/>
                    )}
                </div>
            </div>
        </div>
    );
};
