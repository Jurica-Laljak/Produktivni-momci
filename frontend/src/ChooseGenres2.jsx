import React, { useState,useEffect } from 'react';
import './ChooseGenres2.css';
import axios from 'axios';

// Komponenta sada prima zanrovi kao prop
export default function ChooseGenres({ zanrovi }) {
  const [selectedGenres, setSelectedGenres] = useState([]);



  

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
    if (selectedGenres.length >= 3) {
      console.log('Selected genres:', selectedGenres);
      // saljemo podatke o odabranim zanrovima na backend
      
      axios.post('api/preference/zanrovi', selectedGenres, {
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(response => {
        console.log('Data successfully sent:', response.data);
        // Ovdje možete dodati preusmjeravanje korisnika na Home ili UserHome
      })
      .catch(error => {
        console.error('There was an error sending the data:', error);
      });

      // redirect na homePage(userHome)
      
    } else {
      alert('Please select at least 3 genres.');
    }
  };

  return (
    <div className="genre-selection-page">
      <h2>Select at least 3 music genres</h2>
      <div className="genres-grid">
        {/* Generiraj žanrove iz props zanrovi */}
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
        Confirm Selection
      </button>
    </div>
  );
}
