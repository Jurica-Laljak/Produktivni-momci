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
  
  import React, { useState, useEffect, useContext } from 'react';
  import { Link, useLocation, useNavigate } from 'react-router-dom';
  import SearchBar from './SearchBar';
  import { FcGoogle } from 'react-icons/fc';
  import { FaUserCircle, FaHome, FaBell } from 'react-icons/fa'; // Dodali smo FaBell
  import './AppNavbar.css';
  import axiosPrivate from './api/axiosPrivate';
  import {Context} from "./App"
  
  export default function AppNavbar2({ setResults, zanrovi }) {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useContext(Context)
    const location = useLocation();
    const navigate = useNavigate();
    
  
    useEffect(() => {
      const token = localStorage.getItem('token');
      if (token) {
        setIsLoggedIn(true);
        try {
          const googleID = JSON.parse(atob(token.split('.')[1]));
  
          const getUserData = async () => {
            try {
              const response = await axiosPrivate.get(`korisnici/g/${googleID.sub}`);
              const userData = response.data;
              const imePrezime = userData.imeKorisnika + ' ' + userData.prezimeKorisnika;
              setUserName(imePrezime);
             console.log("user data: ")
              console.log(userData)
            } catch (err) {
              console.log('Doslo je do greske', err);
            }
          };
          getUserData();
        } catch (error) {
          console.error('Error decoding token:', error);
        }
      } else {
        setIsLoggedIn(false);
      }
    }, [location]);
  
    const handleLogout = () => {
      localStorage.removeItem('token');
      setIsLoggedIn(false);
      navigate('/');
    };
  
    const isUserRoute =
      location.pathname === '/user' ||
      location.pathname === '/userUlaznice' ||
      location.pathname === '/userOglasi';
  
    return (
      <nav className="navbar navbar-expand-lg navbar-light custom-navbar">
        <div className="d-flex justify-content-between align-items-center w-100">
          <Link className="navbar-brand" to="/">
            <img src="/Ticket4Ticket_transparent.png" alt="t4t" style={{ height: '40px' }} />
          </Link>
  
          {isLoggedIn ? (
            isUserRoute ? (
              <div className="d-flex justify-content-between align-items-center w-100">
                <span className="welcome-text" >Dobrodošli <span style={{color:'#425DFF'}}>{userName}</span></span>
                <Link to="/UserHome" className="nav-link">
                  <FaHome style={{ fontSize: '3rem', color: '#425DFF' }} />
                </Link>
                <button onClick={handleLogout} className="btn btn-primary ms-2" style={{backgroundColor:'#425DFF', color:'white', border:'none'}}>
                  Odjavite se
                </button>
              </div>
            ) : (
              <div className="d-flex justify-content-between align-items-center w-100">
                <SearchBar setResults={setResults} zanrovi={zanrovi} />
                <Link to="/create-ad" className="btn btn-create-ad me-2">
                  + Kreiraj oglas
                </Link>
                {/* Dodajemo ikonu zvona */}
                <Link to="/notifications" className="nav-link">
                  <FaBell style={{ fontSize: '2.5rem', color: '#425DFF' }} />
                </Link>
                <Link to="/user" className="nav-link">
                  <FaUserCircle style={{ fontSize: '2.5rem', color: '#425DFF' }} />
                </Link>
              </div>
            )
          ) : (
            <div className="d-flex justify-content-between align-items-center w-100">
              <SearchBar setResults={setResults} zanrovi={zanrovi} />
              <ul className="navbar-nav ms-3">
                <li className="nav-item">
                  <Link
                    className="nav-link"
                    to="/login"
                    style={{
                      color: 'black',
                      border: 'solid 2px',
                      borderRadius: '15px',
                      backgroundColor: 'white',
                    }}
                  >
                    <FcGoogle style={{ marginLeft: '5px' }} /> Sign in with Google
                  </Link>
                </li>
              </ul>
            </div>
          )}
        </div>
      </nav>
    );
  }
  
  


