import React, { useEffect, useState, useContext  } from 'react';
import axios from 'axios';
import './Listing.css';
import { remainingDaysToEvent } from './utilities/remainingDaysToEvent'
import { FaHeart, FaRegHeart, FaEye, FaEyeSlash } from 'react-icons/fa';
import { ticketMap } from '../data/ticketMap'
import axiosPrivate from './api/axiosPrivate';
import {Context} from "./App"

export default function Listing({ ulaznica, izvodaci }) {
  const [isFavorite, setIsFavorite] = useState(false);
  const [isVisible, setIsVisible] = useState(true);
  const [showDetails, setShowDetails] = useState(false);
  const [weather, setWeather] = useState(null);
  const [forecastAvailable, setForecastAvailable] = useState(true);
  const {openRazmijeniModal} = useContext(Context)
  const [availableTickets, setAvailableTickets] = useState(null);

  const toggleFavorite = (e) => {
    e.stopPropagation();
    setIsFavorite(!isFavorite);
  };

  const toggleVisibility = (e) => {
    e.stopPropagation();
    setIsVisible(!isVisible);
  };

  const toggleDetails = () => {
    setShowDetails(!showDetails);
  };

  useEffect(() => {
    const fetchUserTickets = async () => {
      try {
        // Poziv API-ja za dohvaćanje ulaznica
        const response = await axiosPrivate.get('preference/korisnici/ulaznice');
        const userTickets = response.data;
  
        // Broji koliko ulaznica ima trenutni korisnik
        const freeTickets = userTickets.filter(ticket => !ticket.oglas).length;
        setAvailableTickets(freeTickets);
      } catch (error) {
        console.error('Greška pri dohvaćanju ulaznica:', error);
        setAvailableTickets(0); // Ako dođe do greške, postavi 0
      }
    };

    fetchUserTickets();
}, []);

const handleOpenRazmijeniModal = (oglas) => {

  const mod = {
    idUlaznice: oglas.ulaznicaId,
    idOglasa: oglas.idOglasa
  };

  openRazmijeniModal(mod);
};


  useEffect(() => {
    if (showDetails) {
      // Izračunaj razliku u danima između trenutnog datuma i datuma koncerta
      const today = new Date();
      const eventDate = new Date(ulaznica.datumKoncerta);
      const diffInTime = eventDate - today;
      const diffInDays = Math.ceil(diffInTime / (1000 * 60 * 60 * 24)); // Pretvori razliku u dane

      if (diffInDays > 14) {
        setForecastAvailable(false); // Vremenska prognoza nije dostupna
        return;
      }

      const fetchWeather = async () => {
        try {
          const apiKey = '6bb6d2c8662145aa872134433251001';
          const response = await axios.get(
            `https://api.weatherapi.com/v1/forecast.json?key=${apiKey}&q=${ulaznica.lokacijaKoncerta}&dt=${ulaznica.datumKoncerta}&aqi=no&alerts=no`
          );

          console.log('API Response:', response.data); // Provjera odgovora

          // Provjeri postoji li 'forecast' i 'forecastday' u odgovorima
          const forecastData = response.data?.forecast?.forecastday;

          if (forecastData && forecastData.length > 0) {
            const forecast = forecastData[0]?.day; // Pristupi prvom danu
            if (forecast) {
              setWeather({
                tempMin: forecast.mintemp_c,
                tempMax: forecast.maxtemp_c,
                condition: forecast.condition?.text || 'Nepoznato',
                icon: forecast.condition?.icon || '',
              });
            } else {
              console.error('Nema podataka o danu vremenske prognoze.');
            }
          } else {
            console.error('Nema vremenskih podataka za traženi datum ili lokaciju.');
          }
        } catch (error) {
          console.error('Greška pri dohvaćanju vremenske prognoze:', error);
        }
      };

      fetchWeather();
    }
  }, [showDetails, ulaznica.lokacijaKoncerta, ulaznica.datumKoncerta]);

  return (
    <div>
      {showDetails ? (
        <div className="outer">
          <div className="overlay" onClick={toggleDetails}></div>
          <div className="detailed-view">

            <button className="close-details" onClick={toggleDetails}>
              ✖
            </button>

            <div className="header-container">
              <div className="detailed-header">
                <h2><span>
                  {izvodaci.map((izvodac) => (
                    <span className="title" key={izvodac.imeIzvodaca}>
                      {izvodac.imeIzvodaca}{izvodac.prezimeIzvodaca ? " " + izvodac.prezimeIzvodaca : <></>},{' '}
                      {new Date(ulaznica.datumKoncerta).toLocaleDateString('hr-HR')},{' '}
                      {ulaznica.lokacijaKoncerta}
                    </span>
                  ))}
                </span></h2>
                <p className="event-info" style={{
                  textDecoration: remainingDaysToEvent(ulaznica.datumKoncerta) <= 14 ? "underline" : "none"
                }}>{remainingDaysToEvent(ulaznica.datumKoncerta) ? remainingDaysToEvent(ulaznica.datumKoncerta) : ""}
                  {remainingDaysToEvent(ulaznica.datumKoncerta) > 1
                    ? " dana do koncerta"
                    : remainingDaysToEvent(ulaznica.datumKoncerta) == 0 ? "Koncert je danas" : " dan do koncerta"}
                </p>
              </div>


              <p className="posted-by">
                <span>Objavio</span>
                <span className="blue-text">{ulaznica.imeKorisnika} {ulaznica.prezimeKorisnika}</span>
              </p>
            </div>

            <div className="grid-container">
              <div className="detailed-img-container">
                <img
                  src={ulaznica.urlSlika}
                  alt={`Poster za ${ulaznica.lokacijaKoncerta}`}
                  className="detailed-img"
                />
              </div>

              <div className="details-right">
                <p className="availability-info">
                  Vrsta ulaznice: {ulaznica.vrstaUlaznice}
                </p>
              </div>

              {localStorage.getItem("token") &&
                <div className="button-flex">
                <button className="button" onClick={() => handleOpenRazmijeniModal(ulaznica)} style={{
                    backgroundColor: availableTickets > 0 ? '#FFB700' : '', // Dodaje boju ako je uvjet ispunjen
                    color: availableTickets > 0 ? 'black' : ''
                  }}
                >

                  Razmijeni ulaznice
                </button>
                <span className="button-description">
                {availableTickets === null
      ? "Učitavanje dostupnih ulaznica..." // Prikaz za vrijeme učitavanja
      : availableTickets > 0
      ? `Imate ${availableTickets} slobodnih ulaznica za razmjenu.`
      : "Nemate dostupne ulaznice za razmjenu."}

                </span>
              </div>}


              {weather ? (
                <div className="weather-flex">
                  <div className="weather-container">
                    <div className="weather-description">
                      Vrijeme za {ulaznica.lokacijaKoncerta}
                    </div>
                    <div className="other-container">
                      <div className="weather-description">
                        {new Date(ulaznica.datumKoncerta).toLocaleDateString('hr-HR')}
                      </div>
                      <div className="weather-info">
                        <div className="weather">
                          {weather.condition}
                        </div>
                        <img
                          src={`https:${weather.icon}`}
                          alt={weather.condition}
                          className="weather-icon"
                        />
                        <span className="temeperature">
                          <span>
                            {Math.round(weather.tempMax)}°C
                          </span>
                          <span>
                            {Math.round(weather.tempMin)}°C
                          </span>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              )
                : (<></>
                )}

            </div>
          </div>
        </div>)

        : <></>}


      <div className="custom-card" onClick={toggleDetails}>
        <div className="image-container">
          <img
            src={ulaznica.urlSlika}
            alt={`Poster za ${ulaznica.lokacijaKoncerta}`}
            className="card-img"
          />
          { /*   <div className="icon-container">
              <button onClick={toggleFavorite} className="icon-button">
                {isFavorite ? (
                  <FaHeart className="icon favorite-icon" />
                ) : (
                  <FaRegHeart className="icon favorite-icon" />
                )}
              </button>
              <button onClick={toggleVisibility} className="icon-button">
                {isVisible ? (
                  <FaEye className="icon visibility-icon" />
                ) : (
                  <FaEyeSlash className="icon visibility-icon" />
                )}
              </button>
            </div> */}
        </div>
        <div className="card-details">
          <p className="event-title">
            {izvodaci.map((izvodac) => (
              <span key={izvodac.imeIzvodaca}>
                {izvodac.imeIzvodaca}{izvodac.prezimeIzvodaca ? " " + izvodac.prezimeIzvodaca : <></>},{' '}
                {new Date(ulaznica.datumKoncerta).toLocaleDateString('hr-HR')},{' '}
                {ulaznica.lokacijaKoncerta}
              </span>
            ))}
          </p>
          <div className="event-description">
            <p className='event-creator'>Objavio <span className="name">{ulaznica.imeKorisnika} {ulaznica.prezimeKorisnika}</span></p>
            <div className="event-info-container">
              <p className="event-info">{ticketMap[ulaznica.vrstaUlaznice]} ulaznica</p>
              <p className="event-info" style={{
                color: remainingDaysToEvent(ulaznica.datumKoncerta) <= 14 ? "#425dff" : "none"
                , fontWeight: remainingDaysToEvent(ulaznica.datumKoncerta) <= 14 ? "bold" : "none"
              }}>{remainingDaysToEvent(ulaznica.datumKoncerta) ? remainingDaysToEvent(ulaznica.datumKoncerta) : ""}
              {remainingDaysToEvent(ulaznica.datumKoncerta) > 1
                ? " dana do koncerta"
                : remainingDaysToEvent(ulaznica.datumKoncerta) == 0 ? "Koncert je danas" : " dan do koncerta"}
              </p>
            </div>
          </div>
        </div>
      </div>

    </div>
  );
}


