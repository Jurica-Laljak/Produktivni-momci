import React, { useState,useEffect } from 'react';
import './ChooseGenres2.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


export default function ChooseGenres({ zanrovi }) {
  const [selectedGenres, setSelectedGenres] = useState([]);

  const navigate = useNavigate();

  

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
      
      axios.post('api/preference/zanrovi', selectedGenres, {
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(response => {
        console.log('Data successfully sent:', response.data);
        navigate('/UserHome')
      })
      .catch(error => {
        console.error('There was an error sending the data:', error);
      });

     
      
      
    } else {
      navigate('/UserHome')
    }


  };

  return (
    <div className="genre-selection-page">
      <h2>Odaberite od 0 do 15 žanrova glazbe</h2>
      <div className="genres-grid">
       
        {Object.entries(zanrovi).map(([idZanra, imeZanra]) => (
          <div
            key={idZanra}
            className={`genre-card ${selectedGenres.includes(idZanra) ? 'selected' : ''}`}
            onClick={() => handleGenreSelect(idZanra)}
            
          >
            <div className="genre-name">{imeZanra}</div>
          </div>
        ))}
      </div>
      <button onClick={handleSubmit} className="submit-button">
        Next
      </button>
    </div>
  );
}
