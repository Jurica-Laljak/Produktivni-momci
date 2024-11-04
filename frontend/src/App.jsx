import { useState } from 'react'
import "./../node_modules/bootstrap/dist/css/bootstrap.min.css"
import './App.css'
import AppNavbar  from './AppNavbar'
import Button from 'react-bootstrap/Button';
import { Route, Routes, useLocation } from 'react-router-dom';
import Login from './Login'
import Home from './Home'
import SignUp from './SignUp'
import SignUp2 from './SignUp2'
import "@fortawesome/fontawesome-free/css/all.min.css"
import SearchResults from './SearchResults';

function App() {

  const location = useLocation();
  const noNavbarRoutes = ['/login', '/signup'];

  const [results, setResults] = useState([]);

 return (
  <>
 
 {!noNavbarRoutes.includes(location.pathname) && <AppNavbar setResults={setResults} />}
  <SearchResults results={results}/>

  <div className='container'>
    <Routes>
    <Route path='/' element={<Home />}/>
    <Route path='/signup' element={<SignUp2 />}/>
    <Route path='/login' element={<Login />}/>
    </Routes>
  </div>
  </>
 
 )
}

export default App
