import {Auth} from '../Auth/Parent/app/Auth'
import { Routes, Route } from 'react-router-dom';
import { Reg } from '../Reg/Parent/app/Reg';
import { Workzone } from '../Workzone/Parent/app/Workzone';
import ErrorPage from '../ErrorPage/ErrorPage';

function App() {

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
              <Route path='usersTasks'/>
              <Route path='notifications'/>
              <Route path='userClan'/>
            </Route>
            <Route path="/*" element={<ErrorPage/>}/>
        </Routes>
      </>
  )
}

export default App;
