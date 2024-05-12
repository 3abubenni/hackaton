import "../styles/Clanstyles.css"
import { IClan } from "../../../../../entities/Items.interface"


export const Clan: React.FC<IClan & {type : string}> = ({name, type}) => {
    return (
        <>
            <div id="clan">
                <div className="clanElements">
                    <div></div>
                    <p>{name}</p>
                    {type == 'show' ? "" : <button>Join</button>}
                </div>
            </div>
        </>
    );
};