import { useEffect, useState } from 'react'
import "./../node_modules/bootstrap/dist/css/bootstrap.min.css"
import './App.css'
import AppNavbar  from './AppNavbar'
import AppNavbar2 from './AppNavbar2'
import Button from 'react-bootstrap/Button';
import { Route, Routes, useLocation } from 'react-router-dom';
import LoginGoogle from './LoginGoogle';
import Home from './Home'
import ChooseGenres from './ChooseGenres';
import "@fortawesome/fontawesome-free/css/all.min.css"
import SearchResultsList from './SearchResultsList';
import axios from 'axios';
import AppFooter from './AppFooter'
import UserHome from './UserHome'


function App() {

  const location = useLocation();
  const noNavbarRoutes = ['/login', '/ChooseGenres'];
  const [results, setResults] = useState([]);
  const [zanrovi, setZanrovi] = useState([]);


  useEffect(() => {
     axios.get("api/zanrovi")
     .then(res => {
      console.log(res.data)
      setZanrovi(res.data.reduce((map, zanr) => {
        map[zanr.idZanra] = zanr.imeZanra;
        return map;
      }, {}))
     })
     .catch(err => console.log(err))
  }, [])

 return (
 
  <div className='app-container'>
 {!noNavbarRoutes.includes(location.pathname) && <AppNavbar2  setResults={setResults} zanrovi={zanrovi} />}
 

  <div className='container main-content'>
    <Routes>
    <Route path='/' element={<Home />}/>
    <Route path='/login' element={<LoginGoogle />}/>
    <Route path='/search' element={<SearchResultsList results={results}/>}/>
    <Route path='/ChooseGenres' element={<ChooseGenres zanrovi={zanrovi}/>}></Route>
    <Route path='/UserHome' element={<UserHome />}/>
    <Route path='/Home' element={<Home />}/>
    </Routes>
    </div>
    <AppFooter />
  
    </div>
 )
}

export default App
