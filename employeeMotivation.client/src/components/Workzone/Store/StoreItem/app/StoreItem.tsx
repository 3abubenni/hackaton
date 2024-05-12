import { IStoreItem } from "../../../../../entities/Items.interface";
import "../styles/StoreItemstyles.css"

export const StoreItem : React.FC<IStoreItem> = ({name, image}) => {
    return (
        <div className="storeItem">
            <div id="storeItemContent">
                <img src={image} alt="123" />
                <p>{name}</p>
            </div>
        </div>
    );
};