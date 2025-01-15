import React, { useState, useEffect } from 'react';
import './ChooseGenres2.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import axiosPrivate from "./api/axiosPrivate";
import { genreMap } from "../data/genreMap.js"

export default function ChooseGenres({ zanrovi }) {
  const [selectedGenres, setSelectedGenres] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get("token");

    if (token) {
      localStorage.setItem("token", token);

      urlParams.delete("token");
      const newUrl = `${window.location.pathname}`;
      window.history.replaceState({}, document.title, newUrl);
    }
  }, []);


  // Funkcija za selektiranje/odabiranje žanrova

  const handleGenreSelect = (genreId) => {
    if (selectedGenres.includes(genreId)) {
      setSelectedGenres(selectedGenres.filter(item => item !== genreId));
    } else {
      setSelectedGenres([...selectedGenres, genreId]);
    }
  };

  // Funkcija za potvrdu selekcije
  const handleSubmit = () => {
    if (selectedGenres.length >= 1) {
      //console.log('Selected genres:', selectedGenres);
      // saljemo podatke o odabranim zanrovima na backend

      axiosPrivate.post(`preference/zanrovi`, selectedGenres, {
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .then(response => {
          console.log('Data successfully sent:', response.data);
          navigate('/')
        })
        .catch(error => {
          console.error('There was an error sending the data:', error);
        });




    } else {
      navigate('/')
    }


  };

  return (
    <div className="genre-selection-page">
      <h3>Odaberite žanrove glazbe koji Vam se sviđaju</h3>

      <div className="genres-grid">

        {Object.entries(zanrovi).map(([idZanra, imeZanra]) => (
          <div
            key={idZanra}
            className={`genre-card ${selectedGenres.includes(idZanra) ? 'selected' : ''}`}
            onClick={() => handleGenreSelect(idZanra)}>
            <div className="genre-name">{genreMap[imeZanra].name}</div>
          </div>
        ))}

      </div>

      <button onClick={handleSubmit} className="submit-button">
        {
          (selectedGenres.length > 0) ? "Potvrdi odabir i nastavi"
            : "Nastavi bez odabranih preferencija"
        }
      </button>
      
    </div>
  );
}
