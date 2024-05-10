import "../styles/Clanstyles.css"
import { IClan } from "../../../../../entities/Items.interface"


export const Clan: React.FC<IClan> = ({name}) => {
    return (
        <>
            <div id="clan">
                <div className="clanElements">
                    <div></div>
                    <p>{name}</p>
                    <button>Join</button>
                </div>
            </div>
        </>
    );
};