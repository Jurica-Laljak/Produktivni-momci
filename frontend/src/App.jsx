import { useEffect, useState, createContext } from 'react'
import "./../node_modules/bootstrap/dist/css/bootstrap.min.css"
import './App.css'
import AppNavbar2 from './AppNavbar2'
import Button from 'react-bootstrap/Button';
import { Route, Routes, useLocation } from 'react-router-dom';
import LoginGoogle from './LoginGoogle';
import Home from './Home'
import "@fortawesome/fontawesome-free/css/all.min.css"
import SearchResultsList from './SearchResultsList';
import axios from 'axios';
import AppFooter from './AppFooter'
import ChooseGenres2 from './ChooseGenres2'
import UserHome from './UserHome'
import Ulaznice from './Ulaznice';
import NavigationButtons from "./NavigationButtons";
import User from './User';
import UserOglasi from './UserOglasi';
import Obavijesti from './Obavijesti';
import AddOglasModal from './AddOglasModal';
import axiosPrivate from "./api/axiosPrivate";

export const Context = createContext()

function App() {

  const location = useLocation();
  const noNavbarRoutes = ['/login', '/ChooseGenres'];
  const [results, setResults] = useState([]);
  const [zanrovi, setZanrovi] = useState([]);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const showNavButtons = ['/user', '/userUlaznice', '/userOglasi']; 
  const [userName, setUserName] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [korisnikID, setKorisnikID] = useState()

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);
  const [userData, setUserData] = useState('');

 
  //dohvacanje svih zanrova
  useEffect(() => {
    

     axios.get(`api/zanrovi`)
     .then(res => {
      console.log(res.data)
      setZanrovi(res.data.reduce((map, zanr) => {
        map[zanr.idZanra] = zanr.imeZanra;
        return map;
      }, {}))
      

     }).finally(console.log(zanrovi))
     .catch(err => console.log(err))

     

  }, [])

 return (
 
  <Context.Provider value={{userName,setUserName,isModalOpen,openModal,closeModal}}>

  <div className='app-container'>
    {/*navbar se prikazuje samo na odredenim putanjama*/ }
 {!noNavbarRoutes.includes(location.pathname) && <AppNavbar2  setResults={setResults} zanrovi={zanrovi}
                                                              userData2={userData} setUserData2={setUserData}/>} 
 
 {/* prikazuje samo na odredenim putanjama*/ }
  {showNavButtons.includes(location.pathname) && <NavigationButtons />}

  <div className='container main-content'>
    <Routes>
    <Route path='/' element={<Home />}/>
    <Route path='/login' element={<LoginGoogle />}/>
    <Route path='/search' element={<SearchResultsList results={results}/>}/>
    <Route path='/ChooseGenres' element={<ChooseGenres2 zanrovi={zanrovi}/>}></Route>
    <Route path='/UserHome' element={<UserHome />}/>
    <Route path='/Home' element={<Home />}/>
    <Route path='/userUlaznice' element={<Ulaznice />}></Route>
    <Route path='/user' element={<User userData={userData}/>}></Route>
    <Route path='/userOglasi' element={<UserOglasi />}></Route>
    <Route path='/notifications' element={<Obavijesti/>}></Route>

    </Routes>
    </div>
    <AppFooter />

     {/* Modal se prikazuje globalno */}
     {isModalOpen && <AddOglasModal />}

    </div>
    </Context.Provider>
 )
}

export default App
