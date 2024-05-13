import { FC, useEffect, useState } from "react";
import { INotifItem } from "../../../../../entities/Items.interface";
import "../styles/NotificationItemstyles.css"
import axios from "axios";

const NotificationItem : FC<INotifItem> = ({idClan, title, description, type}) => {


    const [clanName, setClanName] = useState('')

    useEffect(() =>{
        const getClanInfo = async() =>{
            const accessToken = localStorage.getItem('accessToken')
            const response = await axios.request({
                url: `http://localhost:8080/api/clan/${idClan}`,
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`,
                },
            })
            setClanName(response.data.name)
        }

        getClanInfo();
    }, [])
    
    return (
        <div>
            <div id="notifItem">
                <div className="notifItemElements">
                    <p>{title}</p>
                    <p id="description">{description}<div>{clanName}</div></p>
                    {type == 'invite' ? <div className="buttons"><button id="decline" disabled={true}>Decline</button><button id="accept" disabled={true}>Accept</button></div> : ""}
                </div>
            </div>
        </div>
    );
};

export default NotificationItem;