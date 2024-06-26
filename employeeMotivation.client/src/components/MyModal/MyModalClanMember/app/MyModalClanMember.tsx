import { FC, useState } from "react";
import "../styles/MyModalClanMemberstyles.css"
import { IMyModalClanMember } from "../../../../entities/Modal.interface";
import { IMember } from "../../../../entities/Items.interface";

export const MyModalClanMember : FC<IMyModalClanMember & { remove: (member : IMember) => void} & { closeModal: () => void}> = ({id, fname, lname, remove, closeModal}) => {

    const [member] = useState<IMember>({
        id: id,
        fname: fname,
        lname: lname,
    })

    const handleClickRemoveMember = () =>{
        remove(member);
        closeModal();
    }

    return (
        <div className="myModalItemsView_clan">
            <div className="elements">
                <p>{fname}</p>
                <p>{lname}</p>
            </div>
            <button onClick={handleClickRemoveMember}>Kick</button>
        </div>
    );
};