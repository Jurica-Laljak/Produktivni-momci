import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Listing from './Listing';
import './ListingList.css';
import axiosPrivate from './api/axiosPrivate';
import ScaleLoader from "react-spinners/ScaleLoader";
import { remainingDaysToEvent } from './utilities/remainingDaysToEvent'

export default function ListingList() {
  const [listings, setListings] = useState([]);
  const [loading, setLoading] = useState(true);

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

        const stariOglasi = listingsData.filter((oglas) => remainingDaysToEvent(oglas.datumKoncerta) < 0);
        listingsData = listingsData.filter((oglas) => remainingDaysToEvent(oglas.datumKoncerta) >= 0);
        stariOglasi?.map(oglas => deleteStariOglas(oglas));

        console.log("Listings data: ", listingsData)
        setLoading(false);
        setListings(listingsData);
      } catch (error) {
        console.error("Greška pri dohvaćanju oglasa:", error);
      }
    };

    fetchListings();
  }, []);

  const deleteStariOglas = async (oglas) => {
    try {
      const response = await axiosPrivate.delete(`oglasi/izbrisi/${oglas.idOglasa}`);
    } catch (error) {
      console.error("Greška:", error);
    }
  };

  return (
    <div style={{display: loading && "flex", marginTop: loading && "6rem", justifyContent: "center", alignItems: "center"}}>
      {loading ?
        <ScaleLoader
        height={100}
        radius={15}
        width={10}
        margin={4}
        color='#425DFF'
        />
      : <div className="listingContainer">
          {listings.map((listing) => (
            <Listing
              key={listing.idOglasa}
              ulaznica={listing}
              izvodaci={listing.izvodaci}
            />
          ))}
        </div>
      }
    </div>
  );
}

