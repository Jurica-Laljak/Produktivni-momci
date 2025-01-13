import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Listing from './Listing';
import './ListingList.css';
import axiosPrivate from './api/axiosPrivate';

export default function ListingList() {
  const [listings, setListings] = useState([]);

  useEffect(() => {
    const fetchListings = async () => {
      try {
        const response = await axios.get('/api/oglasi/list/random-max'); // Prikaz najveceg broja random oglasa
        const listingsData = response.data;
        console.log("Listings data: ", listingsData)
        setListings(listingsData);
      } catch (error) {
        console.error("Greška pri dohvaćanju oglasa:", error);
      }
    };

    fetchListings();
  }, []);

  return (
    <div className="listingContainer">
      {listings.map((listing) => (
        <Listing
          key={listing.idOglasa}
          ulaznica={listing}
          izvodaci={listing.izvodaci}
        />
      ))}
    </div>
  );
}

