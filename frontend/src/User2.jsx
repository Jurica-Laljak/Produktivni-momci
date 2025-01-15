import React, { useState,useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import axiosPrivate from "./api/axiosPrivate";
import "./User2.css";

export default function User(){

    const navigate = useNavigate();

    const handleEditPreferences = () => {
        navigate("/chooseGenres")
    }

    return(

        <div className="user-settings-container">
        <div className="section">
          <h3>Osobni podaci</h3>
          <div className="form-group">
            <label htmlFor="username">Korisničko ime</label>
            <input
              type="text"
              id="username"
              className="form-control"
              value="marko123"
              readOnly
            />
            <button className="btn btn-outline-primary">Uredi</button>
          </div>
          <div className="form-group">
            <label htmlFor="google-account">Google račun</label>
            <input
              type="email"
              id="google-account"
              className="form-control"
              value="marko123@gmail.com"
              readOnly
            />
            <button className="btn btn-outline-primary">Uredi</button>
          </div>
        </div>
  
        <div className="section">
          <h3>Preferencije</h3>
          <button
            className="btn btn-outline-primary preference-btn"
            onClick={handleEditPreferences}
          >
            <span className="icon">⚙️</span> Uredi preferencije
          </button>
        </div>
  
        <div className="section">
          <h3>Obavijesti</h3>
          <div className="checkbox-group">
            <div>
              <input type="checkbox" id="allow-notifications" />
              <label htmlFor="allow-notifications">
                Dozvoli obavijesti unutar aplikacije
              </label>
            </div>
            <div>
              <input type="checkbox" id="notify-ad-response" />
              <label htmlFor="notify-ad-response">
                Obavijesti me kada se netko javi na moj oglas
              </label>
            </div>
            <div>
              <input type="checkbox" id="notify-transaction" />
              <label htmlFor="notify-transaction">
                Obavijesti me po završetku transakcije
              </label>
            </div>
            <div>
              <input type="checkbox" id="notify-events" />
              <label htmlFor="notify-events">
                Obavijesti me o novim događajima u kategorijama koje mi se sviđaju
              </label>
            </div>
          </div>
        </div>
  
        <button className="btn btn-danger delete-account-btn">
          Obriši korisnički račun
        </button>
      </div>
    
        
    )
}