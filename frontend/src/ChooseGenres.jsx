


  import React, { useState } from 'react';
import './ChooseGenres.css';

const genres = [
  { name: 'Pop', bgColor: '#e74c3c', image: 'path_to_pop_image.jpg' },
  { name: 'Rock', bgColor: '#f1c40f', image: 'path_to_rock_image.jpg' },
  { name: 'Hip Hop', bgColor: '#9b59b6', image: 'path_to_hiphop_image.jpg' },
  { name: 'R&B', bgColor: '#16a085', image: 'path_to_rb_image.jpg' },
  { name: 'Country', bgColor: '#f39c12', image: 'path_to_country_image.jpg' },
  { name: 'Electronic', bgColor: '#1abc9c', image: 'path_to_electronic_image.jpg' },
  { name: 'Jazz', bgColor: '#2ecc71', image: 'path_to_jazz_image.jpg' },
  { name: 'Blues', bgColor: '#34495e', image: 'path_to_blues_image.jpg' },
  { name: 'Classical', bgColor: '#3498db', image: 'path_to_classical_image.jpg' },
  { name: 'Reggae', bgColor: '#27ae60', image: 'path_to_reggae_image.jpg' },
  { name: 'Metal', bgColor: '#c0392b', image: 'path_to_metal_image.jpg' },
  { name: 'Folk', bgColor: '#2980b9', image: 'path_to_folk_image.jpg' },
  { name: 'Gospel', bgColor: '#9b59b6', image: 'path_to_gospel_image.jpg' },
  { name: 'Latin', bgColor: '#f39c12', image: 'path_to_latin_image.jpg' },
  { name: 'Alternative', bgColor: '#8e44ad', image: 'path_to_alternative_image.jpg' }
];

export default function ChooseGenres({zanrovi} ) {
  const [selectedGenres, setSelectedGenres] = useState([]);

  const handleGenreSelect = (genre) => {
    if (selectedGenres.includes(genre)) {
      setSelectedGenres(selectedGenres.filter(item => item !== genre));
    } else {
      setSelectedGenres([...selectedGenres, genre]);
    }
  };

  const handleSubmit = () => {
    if (selectedGenres.length >= 3) {
      console.log('Selected genres:', selectedGenres);
      // posalji podatke na back i preusmjeri korisnika na Home ili UserHome


    } else {
      alert('Please select at least 3 genres.');
    }
  };

  return (
    <div className="genre-selection-page">
      <h2>Select at least 3 music genres</h2>
      <div className="genres-grid">
        {genres.map((genre, index) => (
          <div
            key={index}
            className={`genre-card ${selectedGenres.includes(genre) ? 'selected' : ''}`}
            onClick={() => handleGenreSelect(genre)}
            style={{ backgroundColor: genre.bgColor }}
          >
            <div className="genre-name">{genre.name}</div>
          </div>
        ))}
      </div>
      <button onClick={handleSubmit} className="submit-button">
        Confirm Selection
      </button>
    </div>
  );
}

