/*
import React from 'react';
import { Link } from 'react-router-dom';
import SearchBar from './SearchBar';
import { FcGoogle } from 'react-icons/fc';
import axios from 'axios';
import './AppNavbar.css';

export default function AppNavbar2({ setResults,zanrovi }) {
  return (
    <nav className="navbar navbar-expand-lg navbar-light custom-navbar">
      <div className="container">
        <Link className="navbar-brand" to="/"><img src='/Ticket4Ticket_transparent.png' alt='t4t' style={{ height: '40px' }}></img></Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav" style={{ marginLeft: '-100px' }}>
          <div className="d-flex ms-auto w-100" >
            <SearchBar setResults={setResults} zanrovi={zanrovi} /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <ul className="navbar-nav ms-3">
              <li className="nav-item">
                <Link className="nav-link" to="/login" >
                  Log in with Google <FcGoogle style={{ marginLeft: '5px' }} />
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>
  );
} */
  
  import React, { useState, useEffect } from 'react';
  import { Link, useLocation, useNavigate } from 'react-router-dom';
  import SearchBar from './SearchBar';
  import { FcGoogle } from 'react-icons/fc';
  import { FaUserCircle, FaHome } from 'react-icons/fa';
  import './AppNavbar.css';
  import axiosPrivate from "./api/axiosPrivate";
  
  export default function AppNavbar2({ setResults, zanrovi }) {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState('');
    const location = useLocation();
    const navigate = useNavigate();

    // Provjera tokena u localStorage prilikom svakog renderiranja
    useEffect(() => {
      const token = localStorage.getItem('token');
      if (token) {
        setIsLoggedIn(true);
        try {
          const googleID = JSON.parse(atob(token.split('.')[1])); // Decode JWT token 
        

          const getUserData = async () => {
            try{
                const response = await  axiosPrivate.get(`korisnici/g/${googleID.sub}`)
                const userData = response.data;
                const imePrezime = userData.imeKorisnika + " " + userData.prezimeKorisnika
                setUserName(imePrezime); 
              }
            catch(err){
                console.log("Doslo je do greske", err);
            }
          } 
           //request za dobit podatke o useru
           getUserData();

         
          
        } catch (error) {
          console.error('Error decoding token:', error);
        }
      } else {
        setIsLoggedIn(false);
      }
    }, [location]);
  
    const handleLogout = () => {
      localStorage.removeItem('token'); // Briše token iz localStorage
      setIsLoggedIn(false);
      navigate('/');
    };
  
    const isUserRoute = location.pathname === '/user' || location.pathname === '/userUlaznice' || location.pathname === '/userOglasi';

    return (
      <nav className="navbar navbar-expand-lg navbar-light custom-navbar">
        <div className="container">
          <Link className="navbar-brand" to="/">
            <img src="/Ticket4Ticket_transparent.png" alt="t4t" style={{ height: '40px' }} />
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav" style={{ marginLeft: '-100px' }}>
            <div className="d-flex ms-auto w-100">
              {isLoggedIn ? (
                isUserRoute  ? (
                  // Prilagođeni sadržaj za rutu /user 
                  <>
                    <span className="welcome-text">Dobrodošao {userName}</span>
                    <div className="ms-auto d-flex align-items-center">
                      <Link to="/UserHome" className="nav-link">
                        <FaHome style={{ fontSize: '3rem', color: '#0d6efd' }} />
                      </Link>
                      <button onClick={handleLogout} className="btn btn-primary ms-2">
                        Odjavite se
                      </button>
                    </div>
                  </>
                ) : (
                  // Prilagođeni sadržaj za ostale rute kada je korisnik prijavljen
                  <>
                    <SearchBar setResults={setResults} zanrovi={zanrovi} />
                    <div className="d-flex align-items-center">
                      <Link to="/create-ad" className="btn btn-primary me-2">
                        + Kreiraj oglas
                      </Link>
                      <Link to="/user" className="nav-link">
                        <FaUserCircle style={{ fontSize: '2.5rem' }} />
                      </Link>
                    </div>
                  </>
                )
              ) : (
                // Sadržaj za neprijavljene korisnike
                <>
                  <SearchBar setResults={setResults} zanrovi={zanrovi} />
                  <ul className="navbar-nav ms-3">
                    <li className="nav-item">
                      <Link className="nav-link" to="/login">
                        Log in with Google <FcGoogle style={{ marginLeft: '5px' }} />
                      </Link>
                    </li>
                  </ul>
                </>
              )}
            </div>
          </div>
        </div>
      </nav>
    );
  }
  


