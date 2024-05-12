import "../styles/InventoryItemstyles.css"
import { IInventoryItem } from "../../../../../entities/Items.interface"

export const InventoryItem : React.FC<IInventoryItem> = ({name, image}) => {
    return (
        <div className="inventoryItem">
            <div id="inventoryItemContent">
                <img src={image} alt="123" />
                <p>{name}</p>
            </div>
        </div>
    );
};