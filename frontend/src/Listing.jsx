import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Listing.css';
import { remainingDaysToEvent } from './utilities/remainingDaysToEvent'
import { FaHeart, FaRegHeart, FaEye, FaEyeSlash } from 'react-icons/fa';
import { ticketMap } from '../data/ticketMap'

export default function Listing({ ulaznica, izvodaci }) {
  const [isFavorite, setIsFavorite] = useState(false);
  const [isVisible, setIsVisible] = useState(true);
  const [showDetails, setShowDetails] = useState(false);
  const [weather, setWeather] = useState(null);
  const [forecastAvailable, setForecastAvailable] = useState(true);

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
            `http://api.weatherapi.com/v1/forecast.json?key=${apiKey}&q=${ulaznica.lokacijaKoncerta}&dt=${ulaznica.datumKoncerta}&aqi=no&alerts=no`
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
                }}>{remainingDaysToEvent(ulaznica.datumKoncerta)}
                  {remainingDaysToEvent(ulaznica.datumKoncerta) > 1
                    ? " dana"
                    : " dan"} do koncerta {/* (oglas je objavljen {new Date(ulaznica.datumOglasa).toLocaleDateString('hr-HR')}) */}
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

              <div className="button-flex">
                <button className="button">
                  Razmijeni ulaznice
                </button>
                <span className="button-description">
                  {/* tu ide tekst o količini ulaznica koje korisnik ima
                      - potrebno je dohvatiti koliko korisnik ima "slobodnih" ulaznica i taj
                      broj staviti kao i tekst
                      
                    Neka zamijeni ono što piše ispod*/}
                  Nemate dostupne ulaznice za razmjenu
                </span>
              </div>


              {weather ? (
                <div className="weather-flex">
                  <div className="weather-container">
                    <div className="weather-description">
                      Vrijeme za {ulaznica.lokacijaKoncerta},<br></br>
                      {new Date(ulaznica.datumKoncerta).toLocaleDateString('hr-HR')}
                    </div>
                    <div className="weather-info">
                      <div className="weather">
                        {weather.condition}
                      </div>
                      <img
                        src={`https:${weather.icon}`}
                        alt={weather.condition}
                        className="wether-icon"
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
              }}>{remainingDaysToEvent(ulaznica.datumKoncerta)}
                {remainingDaysToEvent(ulaznica.datumKoncerta) > 1
                  ? " dana"
                  : " dan"} do koncerta
              </p>
            </div>
          </div>
        </div>
      </div>

    </div>
  );
}


