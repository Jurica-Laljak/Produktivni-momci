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
  
  export default function AppNavbar2({ setResults, zanrovi, userData2, setUserData2 }) {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);
    const {openModal,userName,setUserName} = useContext(Context)
    //const [userName, setUserName] = imeUsera;
    const location = useLocation();
    const navigate = useNavigate();
    
    const backendUrl = import.meta.env.VITE_BACKEND_URL;
    const frontednUrl = import.meta.env.VITE_FRONTEND_URL;
  
    useEffect(() => {
      const token = localStorage.getItem('token');
      if (token) {
        setIsLoggedIn(true);
        try {
          const googleID = JSON.parse(atob(token.split('.')[1]));

          if(googleID.roles.includes("ROLE_ADMIN"))
            setIsAdmin(true);

          console.log("User is Admin: " + isAdmin);
  
          const getUserData = async () => {
            try {
              const response = await axiosPrivate.get(`korisnici/g/${googleID.sub}`);
              const userData = response.data;
              const imePrezime = userData.imeKorisnika + ' ' + userData.prezimeKorisnika;
              setUserName(imePrezime);
              localStorage.setItem("ID",userData.idKorisnika)
              console.log("user data: ")
              console.log(userData)
              setUserData2(userData)
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

    const login = () => {
      window.location.href = `${backendUrl}/oauth2/authorization/google?redirect_uri=${frontednUrl}`;
    }
  
    const isUserRoute =
      location.pathname === '/user' ||
      location.pathname === '/userUlaznice' ||
      location.pathname === '/userOglasi' ||
      location.pathname === '/admin';
  
    return (
      <nav className="navbar navbar-expand-lg navbar-light custom-navbar">
        <div className="d-flex justify-content-between align-items-center w-100">
          <Link className="navbar-brand" to="/">
            <img src="/Ticket4Ticket_transparent.png" alt="t4t" style={{ height: '40px' }} />
          </Link>
  
          {isLoggedIn ? (
            isUserRoute ? (
              <div className="d-flex justify-content-between align-items-center w-100">
                <span className="welcome-text" >Dobrodošli <span style={{color:isAdmin ? "#FFB700" : '#425DFF'}}>{userName}</span></span>
                <Link to="/" className="nav-link">
                  <FaHome style={{ fontSize: '3rem', color: '#425DFF' }} />
                </Link>
                <button onClick={handleLogout} className="btn btn-primary ms-2" style={{backgroundColor:'#425DFF', color:'white', border:'none'}}>
                  Odjavite se
                </button>
              </div>
            ) : (
              <div className="d-flex justify-content-between align-items-center w-100">
                <SearchBar setResults={setResults} zanrovi={zanrovi} />
                  <button
                  className="btn btn-create-ad me-2"
                  onClick={openModal}
                  >
                  + Kreiraj oglas
                  </button>
                {/* Dodajemo ikonu zvona */}
                <Link to="/notifications" className="nav-link">
                  <FaBell style={{ fontSize: '2.5rem', color: '#425DFF' }} />
                </Link>
                <Link to="/user" className="nav-link">
                  <FaUserCircle style={{ fontSize: '2.5rem', color: isAdmin ? "#FFB700" : '#425DFF' }} />
                </Link>
              </div>
            )
          ) : (
            <div className="d-flex justify-content-between align-items-center w-100">
              <SearchBar setResults={setResults} zanrovi={zanrovi} />
              <ul className="navbar-nav ms-3">
                <li className="nav-item">
                  <button
                    className="nav-link"
                    onClick={login}
                    style={{
                      color: 'black',
                      border: 'solid 2px',
                      borderRadius: '15px',
                      backgroundColor: 'white',
                    }}
                  >
                    <FcGoogle style={{ marginLeft: '5px' }} /> Sign in with Google
                  </button>
                </li>
              </ul>
            </div>
          )}
        </div>
      </nav>
    );
  }
  
  


