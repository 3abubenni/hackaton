import "../styles/StoreItemstyles.css"

interface StoreItem{
    name: string
    image: string
}

export const StoreItem : React.FC<StoreItem> = ({name, image}) => {
    return (
        <div className="storeItem">
            <div id="storeItemContent">
                <img src={image} alt="123" />
                <p>{name}</p>
            </div>
        </div>
    );
};