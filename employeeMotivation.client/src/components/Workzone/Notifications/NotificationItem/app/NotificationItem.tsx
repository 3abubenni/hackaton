import { FC, useEffect, useState } from "react";
import { INotifItem } from "../../../../../entities/Items.interface";
import "../styles/NotificationItemstyles.css"
import axios from "axios";

const NotificationItem : FC<INotifItem> = ({id, title, description, type}) => {


    const [clanName, setClanName] = useState('')
    useEffect(() =>{
        const getClanInfo = async() =>{
            console.log('ClanInfo')
            const accessToken = localStorage.getItem('accessToken')
            const response = await axios.request({
                url: `http://localhost:8080/api/clan/${id}`,
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`,
                },
            })

            console.log(response)
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
                    {type == 'invite' ? <div className="buttons"><button id="decline">Decline</button><button id="accept">Accept</button></div> : ""}
                </div>
            </div>
        </div>
    );
};

export default NotificationItem;