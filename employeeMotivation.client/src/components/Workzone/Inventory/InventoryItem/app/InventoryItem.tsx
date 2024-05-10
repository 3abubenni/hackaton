import "../styles/InventoryItemstyles.css"

interface InventoryItem{
    name: string
    image: string
}

export const InventoryItem : React.FC<InventoryItem> = ({name, image}) => {
    return (
        <div className="inventoryItem">
            <div id="inventoryItemContent">
                <img src={image} alt="123" />
                <p>{name}</p>
            </div>
        </div>
    );
};