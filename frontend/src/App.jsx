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

  useEffect(() => {
    const brojRandomOglasa = 5; // Zamijeni s stvarnim brojem oglasa kojeg želiš
  
    /* 
    axios.get("api/oglasi/list/3")
      .then(response => {
        console.log( response.data);
      })
      .catch(error => {
        console.error("error", error);
      }); */

      axios.post("api/preference/oglasi/filter", {
        imeIzvođača: "Taylor",
        prezimeIzvođača: "Swift"
       
      })
      .then(response => {
       // console.log('Rezultati pretrage:', response.data);
       // dodati onda logiku za prikaz oglasa koji su vraceni
                console.log(response)
       // napraviti novi get(ustvari post vjerojatno a ne get) gdje cemo sa idOglasa ili cime vec dobit izvodaca
       // i sa idKoncerta mjesto odrzavanja koncerta -> novi post a ne get, isto tako i za prvi
        // trebo bi radit map kroz vracene oglase i unutar tog mapa radit te getove

      })
      .catch(error => {
        console.error('Greška prilikom pretrage:', error);
      });

  }, []);


  const location = useLocation();
  const noNavbarRoutes = ['/login', '/signup', '/ChooseGenres'];
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
    </Routes>
  </div>
  </>
 
 )
}

export default App
