import { Route, Routes } from "react-router-dom";
import { Header } from "../../Header/app/Header"
import ErrorPage from "../../../ErrorPage/ErrorPage";
import { Tasks } from "../../Tasks/Parent/app/Tasks";
import { Profile } from "../../Profile/app/Profile";
import { Store } from "../../Store/Parent/app/Store";
import "../styles/Workzonestyles.css"
import { ClanList } from "../../ClanList/Parent/app/ClanList";
import { Inventory } from "../../Inventory/Parent/app/Inventory";
import {UsersTasks} from "../../UsersTasks/app/UsersTasks";
import { Notifications } from "../../Notifications/Parent/app/Notifications";
import { UserClan } from "../../UserClan/Parent/app/UserClan";

export const Workzone = () => {

    return (
        <>
            <Header/>
            <Routes>
                <Route path='tasks' element={<Tasks/>}/>
                <Route path='clans' element={<ClanList/>}/>
                <Route path='store' element={<Store/>}/>
                <Route path='profile' element={<Profile/>}/>
                <Route path='inventory' element={<Inventory/>}/>
                <Route path='usersTasks' element={<UsersTasks/>}>
                </Route>
                <Route path='userClan' element={<UserClan/>}/>
                <Route path='notifications' element={<Notifications/>}/>
                <Route path='/*' element={<ErrorPage/>} />
            </Routes>
        </>
    );
};