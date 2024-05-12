import {Auth} from '../Auth/Parent/app/Auth'
import { Routes, Route } from 'react-router-dom';
import { Reg } from '../Reg/Parent/app/Reg';
import { Workzone } from '../Workzone/Parent/app/Workzone';
import ErrorPage from '../ErrorPage/ErrorPage';
import { useEffect } from 'react';
import Modal from 'react-modal';

function App() {

  useEffect(() => {
    Modal.setAppElement('body');
  }, []);

  return (
      <>
        <Routes>
            <Route index element={<Auth/>} />
            <Route path='/auth' element={<Auth/>} />
            <Route path='/reg' element={<Reg/>} />
            <Route path='/workzone' element={<Workzone/>}>
              <Route path='tasks'/>
              <Route path='clans'/>
              <Route path='store'/>
              <Route path='profile'/>
              <Route path='inventory'/>
              <Route path='usersTasks'>
              </Route>
              <Route path='notifications'/>
              <Route path='userClan'/>
            </Route>
            <Route path="/*" element={<ErrorPage/>}/>
        </Routes>
      </>
  )
}

export default App;
