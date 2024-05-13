import "../styles/InventoryItemstyles.css"
import { IInventoryItem } from "../../../../../entities/Items.interface"

export const InventoryItem : React.FC<IInventoryItem> = ({name, count}) => {
    return (
        <div className="inventoryItem">
            <div id="inventoryItemContent">
                <p>{name}</p>
                <p id="count">Count: {count}</p>
            </div>
        </div>
    );
};