import { useEffect, useState } from 'react'
import "./../node_modules/bootstrap/dist/css/bootstrap.min.css"
import './App.css'
import AppNavbar  from './AppNavbar'
import Button from 'react-bootstrap/Button';
import { Route, Routes, useLocation } from 'react-router-dom';
import LoginGoogle from './LoginGoogle';
import Home from './Home'
import ChooseGenres from './ChooseGenres';
import "@fortawesome/fontawesome-free/css/all.min.css"
import SearchResultsList from './SearchResultsList';
import axios from 'axios';



function App() {

  const location = useLocation();
  const noNavbarRoutes = ['/login', '/ChooseGenres'];
  const [results, setResults] = useState([]);
  

 return (
  <>
 
 {!noNavbarRoutes.includes(location.pathname) && <AppNavbar  setResults={setResults}/>}
  

  <div className='container'>
    <Routes>
    <Route path='/' element={<Home />}/>
    <Route path='/login' element={<LoginGoogle />}/>
    <Route path='/search' element={<SearchResultsList results={results}/>}/>
    <Route path='/ChooseGenres' element={<ChooseGenres />}></Route>
    <Route path='/UserHome' element={<Home />}/>
    </Routes>
  </div>
  </>
 
 )
}

export default App
