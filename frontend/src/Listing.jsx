import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import './Listing.css';
import { remainingDaysToEvent } from './utilities/remainingDaysToEvent'
import { FaHeart, FaRegHeart, FaEye, FaEyeSlash } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import { ticketMap } from '../data/ticketMap'
import axiosPrivate from './api/axiosPrivate';
import { Context } from "./App"
import Izvodac from './Izvodac'

export default function Listing({ ulaznica, izvodaci, idOglasa, availableTickets }) {
  const [isFavorite, setIsFavorite] = useState(false);
  const [isVisible, setIsVisible] = useState(true);
  const [showDetails, setShowDetails] = useState(false);
  const [weather, setWeather] = useState(null);
  const [forecastAvailable, setForecastAvailable] = useState(true);
  const { openRazmijeniModal, userName } = useContext(Context)

  var borderStyle = null
  if (!userName || (!ulaznica.prodaja && (!availableTickets || availableTickets.length == 0))) {  //korisnik ne može razmjeniti/kupiti ulaznicu
    borderStyle = { "border-color": "#555555" }
  }

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

  const handleOpenRazmijeniModal = (oglas) => {

    const mod = {
      idUlaznice: oglas.ulaznicaId,
      idOglasa: oglas.idOglasa,
      ulaznice: availableTickets
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
          const apiKey = import.meta.env.VITE_PROGNOZA_API_KEY;
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

  const handleSale = async () => {
    try {
      const response = await axiosPrivate.post("transakcije/kreiraj", reqBody);

    } catch (error) {
      console.error("Greška prilikom kreiranja oglasa:", error);
      alert("Došlo je do greške prilikom kupnje ulaznice.");
    }
  }

  return (
    <div id={idOglasa}>
      {showDetails ? (
        <div className="outer">
          <div className="overlay" onClick={toggleDetails}></div>
          <div className="detailed-view" style={borderStyle}>

            <button className="close-details" onClick={toggleDetails} style={borderStyle}>
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
                <span className="izvodaci-header">Izvođači</span>
                {izvodaci.map((izvodac) => (
                  <div className="izvodaci-wrapper">
                    <Izvodac key={izvodac["imeIzvodaca"]} izvodac={izvodac} />
                  </div>
                ))}
              </div>

              {localStorage.getItem("token") &&
                <>
                  {
                    ulaznica.prodaja ? <div className="button-container">
                      <Link to="/transaction" className="link" state={{ sentFrom: "listing", idOglasa: idOglasa.toString() }}>
                        <div className="link-flex">
                          <button className="button" id="buy-tickets">
                            <span>Kupi</span>
                            <span>Ulaznicu</span>
                          </button>
                        </div>
                      </Link>
                      <span className="button-description">
                        Transakcija će se nastaviti u skočnom prozoru
                      </span>
                    </div>

                      :
                      < div className="button-flex">
                        <div className="button-container">

                          <button className="button" onClick={() => handleOpenRazmijeniModal(ulaznica)} style={{
                            backgroundColor: availableTickets.length ? '#FFB700' : '', // Dodaje boju ako je uvjet ispunjen
                            color: availableTickets.length ? 'black' : '',
                            pointerEvents: availableTickets.length ? '' : 'none'
                          }}
                          >
                            <span>Zamjeni</span>
                            <span>Ulaznicu</span>
                          </button>
                          <span className="button-description">
                            {availableTickets === null
                              ? "Učitavanje dostupnih ulaznica..." // Prikaz za vrijeme učitavanja
                              : availableTickets.length
                                ? `Imate ${availableTickets.length} slobodnih ulaznica za razmjenu.`
                                : "Nemate dostupne ulaznice za razmjenu."}
                          </span>
                        </div>
                      </div>
                  }
                </>
              }


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

        : <></>
      }


      <div className="custom-card" style={borderStyle} onClick={toggleDetails}>
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

    </div >
  );
}


