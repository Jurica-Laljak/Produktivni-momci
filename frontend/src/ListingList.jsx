import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Listing from './Listing';
import './ListingList.css';
import axiosPrivate from './api/axiosPrivate';

export default function ListingList() {
  const [listings, setListings] = useState([]);

  useEffect(() => {
    // Provjera tokena i pohrana u localStorage
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get("token");
    if (token) {
      localStorage.setItem("token", token);
      urlParams.delete("token");
      const newUrl = `${window.location.pathname}`;
      window.history.replaceState({}, document.title, newUrl);
      window.location.reload();
    }
  }, []);

  useEffect(() => {
    const fetchListings = async () => {
      try {

        let listingsData = [];

        if(localStorage.getItem("token")) {
          const response = await axiosPrivate.get('preference/oglasi');
          listingsData = response.data;
        }
        else {
          const fallbackResponse = await axios.get('/api/oglasi/list/random-max');
          listingsData = fallbackResponse.data;
        }

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

