
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
}


