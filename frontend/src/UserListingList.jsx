import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Listing from './Listing';
import './ListingList.css';
import axiosPrivate from "./api/axiosPrivate";

export default function UserListingList() {
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
    }
  }, []);

  useEffect(() => {
    const fetchListings = async () => {
      try {
        // Dohvaćanje oglasa po preferencama
        let response = await axiosPrivate.get('preference/oglasi');
        let listingsData = response.data;

        // Ako nema oglasa po preferencama, dohvaćamo default oglase
        if (listingsData.length === 0) {
          const fallbackResponse = await axios.get('api/oglasi/list/12');
          listingsData = fallbackResponse.data;
        }

        const listingsWithDetails = await Promise.all(
          listingsData.map(async (listing) => {
            const izvodaciResponse = await axios.get(`api/oglasi/${listing.idOglasa}/izvodaci`);
            const ulaznicaResponse = await axios.get(`api/ulaznice/${listing.ulaznicaId}`);

            const izvodaciWithGenres = await Promise.all(
              izvodaciResponse.data.map(async (izvodac) => {
                try {
                  const genreResponse = await axios.get(`api/zanrovi/${izvodac.zanrId}`);
                  return {
                    ...izvodac,
                    zanr: genreResponse.data.imeZanra,
                  };
                } catch {
                  return {
                    ...izvodac,
                    zanr: "Nepoznato",
                  };
                }
              })
            );

            return {
              ...listing,
              izvodaci: izvodaciWithGenres,
              ulaznica: ulaznicaResponse.data,
            };
          })
        );

        setListings(listingsWithDetails);
      } catch (error) {
        console.error("Greška pri dohvaćanju oglasa i detalja:", error);
      }
    };

    fetchListings();
  }, []);

  return (
    <div className="listingContainer">
      {listings.map((listing) => (
        <Listing
          key={listing.idOglasa}
          idOglasa={listing.idOglasa}
          status={listing.status}
          ulaznica={listing.ulaznica}
          izvodaci={listing.izvodaci}
        />
      ))}
    </div>
  );
}
