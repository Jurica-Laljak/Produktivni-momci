import React, { useState } from 'react';
import './Listing.css';
import { FaHeart, FaRegHeart, FaEye, FaEyeSlash } from 'react-icons/fa';

export default function Listing({ listing }) {
  const [isFavorite, setIsFavorite] = useState(false);
  const [isVisible, setIsVisible] = useState(true);

  const toggleFavorite = () => {
    setIsFavorite(!isFavorite);
  };

  const toggleVisibility = () => {
    setIsVisible(!isVisible);
  };
  console.log(listing)
  //console.log("In Listing, ulaznica: ", ulaznica, " Izvodaci: ", izvodaci)

  return (
    <div className="custom-card">
      <div className="image-container">
       <img
          src={listing.urlSlika}
          alt={`Poster za ${listing.lokacijaKoncerta}`}
          className="card-img"
        />
        { /*
        <div className="icon-container">
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
        </div>
        */ }
      </div>

      {/* Detalji oglasa */}
      <div className="card-details">
        <p className="event-title">
          {listing.izvodaci.map((izvodac) => (
            <span key={izvodac.imeIzvodaca}>
              {izvodac.imeIzvodaca} {izvodac.prezimeIzvodaca},{' '}
              {new Date(listing.datumKoncerta).toLocaleDateString('hr-HR')},{' '}
              {listing.lokacijaKoncerta}
            </span>
          ))}
        </p>
        <p className="event-info">{listing.odabranaZona}</p>
      </div>
    </div>
  );
}



