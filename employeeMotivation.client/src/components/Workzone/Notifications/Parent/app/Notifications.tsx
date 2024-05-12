import { useEffect, useState } from "react";
import NotificationItem from "../../NotificationItem/app/NotificationItem";
import "../styles/Notificationstyles.css"
import axios from "axios";
import { INotifList } from "../../../../../entities/Items.interface";

export const Notifications = () => {


    const [notifications, setNotifications] = useState<INotifList>({
        children:[]
    })

    useEffect(() =>{
        const getUserInvites = async() =>{
            console.log('Request')
            const accessToken = localStorage.getItem('accessToken')
            const response = await axios.request({
                url: `http://localhost:8080/api/invite/user`,
                method: 'get',
                headers: {
                    Authorization: `${accessToken}`,
                },
            })

            console.log(response)
            setNotifications({children: response.data})
            console.log(notifications)
        }

        getUserInvites();
    }, [])



    return (
        <div className="mainView">
            <div className="notificationsList">
                {notifications.children.map((invite) =>
                        <NotificationItem id={invite.id} idClan={invite.idClan} title="Вас пригласили" description="Вас пригласил клан - " type="invite"/>
                    )}
            </div>
        </div>
    );
};