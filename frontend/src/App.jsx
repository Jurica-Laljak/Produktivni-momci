import { useEffect, useState, createContext } from 'react'
import "./../node_modules/bootstrap/dist/css/bootstrap.min.css"
import './App.css'
import AppNavbar2 from './AppNavbar2'
import Button from 'react-bootstrap/Button';
import { Route, Routes, useLocation } from 'react-router-dom';
import Home from './Home'
import "@fortawesome/fontawesome-free/css/all.min.css"
import SearchResultsList from './SearchResultsList';
import axios from 'axios';
import AppFooter from './AppFooter'
import ChooseGenres2 from './ChooseGenres2'
import Ulaznice from './Ulaznice';
import NavigationButtons from "./NavigationButtons";
import User from './User';
import UserOglasi from './UserOglasi';
import Obavijesti from './Obavijesti';
import ProtectedComponent from './auth/ProtectedComponent'
import AddOglasModal from './AddOglasModal';
import axiosPrivate from "./api/axiosPrivate";
import Admin from './Admin';
import Listing from "./Listing";
import ExchangeModal from './ExchangeModal';

export const Context = createContext()

function App() {

  const location = useLocation();
  const noNavbarRoutes = ['/chooseGenres'];
  const [results, setResults] = useState([]);
  const [zanrovi, setZanrovi] = useState([]);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const showNavButtons = ['/user', '/userUlaznice', '/userOglasi', '/admin'];
  const [userName, setUserName] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [korisnikID, setKorisnikID] = useState()
  const [razmijeniModalData, setRazmijeniModalData] = useState(null);
  const [ulaznice, setUlaznice] = useState([]); // Drži podatke o ulaznicama
  const [izvodaci, setIzvodaci] = useState([]);
  const [isRazmijeniModalOpen, setIsRazmijeniModalOpen] = useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);
  const [userData, setUserData] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const ulazniceResponse = await axiosPrivate.get('preference/korisnici/ulaznice');
        setUlaznice(ulazniceResponse.data); // Postavljanje podataka o ulaznicama

        const izvodaciResponse = await axiosPrivate.get('izvodaci'); // Zamijenite s vašim endpointom za izvođače
        setIzvodaci(izvodaciResponse.data); // Postavljanje podataka o izvođačima
      } catch (error) {
        console.error("Greška pri dohvaćanju podataka:", error);
      }
    };
    fetchData();
  }, []);

  const openRazmijeniModal = (data) => {
    setRazmijeniModalData(data);
    setIsRazmijeniModalOpen(true);
  };
  const closeRazmijeniModal = () => {
    setIsRazmijeniModalOpen(false);
    setRazmijeniModalData(null);
  };
  

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
 
  <Context.Provider value={{userName,setUserName,isModalOpen,openModal,closeModal,isRazmijeniModalOpen,openRazmijeniModal,closeRazmijeniModal, razmijeniModalData}}>

      <div className='app-container'>
        {/*navbar se prikazuje samo na odredenim putanjama*/}
        {!noNavbarRoutes.includes(location.pathname) && <AppNavbar2 setResults={setResults} zanrovi={zanrovi}
          userData2={userData} setUserData2={setUserData} />}

        {/* prikazuje samo na odredenim putanjama*/}
        {showNavButtons.includes(location.pathname) && <NavigationButtons />}

        <div className='container main-content'>
          <Routes>
            {/* nezaštićene komponente */}
            <Route path='/' element={<Home />} />
            <Route path='/search' element={<SearchResultsList results={results} />} />

            {/* zaštićene komponente */}
            <Route path='/chooseGenres' element={
              <ProtectedComponent>
                <ChooseGenres2 zanrovi={zanrovi} userData={userData} />
              </ProtectedComponent>
            }></Route>
            <Route path='/userUlaznice' element={
              <ProtectedComponent>
                <Ulaznice />
              </ProtectedComponent>
            }></Route>
            <Route path='/user' element={
              <ProtectedComponent>
                <User userData={userData} />
              </ProtectedComponent>
            }></Route>
            <Route path='/userOglasi' element={
              <ProtectedComponent>
                <UserOglasi />
              </ProtectedComponent>
            }></Route>
            <Route path='/notifications' element={
              <ProtectedComponent>
                <Obavijesti />
              </ProtectedComponent>
            }></Route>
            <Route path='/admin' element={
              <ProtectedComponent>
                <Admin userData={userData}/>
              </ProtectedComponent>
            }></Route>

          </Routes>
        </div>
        <AppFooter />
        {/* Modal se prikazuje globalno */}
        {isModalOpen && <AddOglasModal />}
        {isRazmijeniModalOpen && <ExchangeModal />}

      </div>
    </Context.Provider>
  )
}

export default App
