import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import Listing from './Listing';
import './ListingList.css';
import axiosPrivate from './api/axiosPrivate';
import ScaleLoader from "react-spinners/ScaleLoader";
import { remainingDaysToEvent } from './utilities/remainingDaysToEvent'
import { useLocation } from 'react-router-dom';
import { Context } from "./App"

export default function ListingList() {
  const [listings, setListings] = useState([]);
  const [loading, setLoading] = useState(true);
  const location = useLocation();
  const [selectedListing, setSelectedListing] = useState(null);
  const [availableTickets, setAvailableTickets] = useState([]);
  const [transakcije, setTransakcije] = useState([]);
  const { closeRazmijeniModal } = useContext(Context)
  

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

  useEffect(() => {
    const fetchUserTickets = async () => {
      try {
        // Poziv API-ja za dohvaćanje ulaznica
        const response = await axiosPrivate.get('preference/korisnici/ulaznice');
        const userTickets = response.data;

        const tran = await axiosPrivate.get('preference/transakcije/poslane-ponude');
        const tr = tran.data;

        // Broji koliko ulaznica ima trenutni korisnik
        setAvailableTickets(userTickets);
        setTransakcije(tr);
      } catch (error) {
        console.error('Greška pri dohvaćanju ulaznica:', error);
        setAvailableTickets(0); // Ako dođe do greške, postavi 0
      }
    };

    if(localStorage.getItem("token"))
      fetchUserTickets();
  }, [closeRazmijeniModal]);

  useEffect(() => {
    
    if(!loading) {
      const elementId = location.hash.replace('#', ''); // Uzimamo ID iz hash-a
    const element = document.getElementById(elementId);
    console.log("element")
    console.log(elementId)
    console.log(element)
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' }); // Pomakni na element
      setSelectedListing(elementId);

    }
    }
    
  }, [loading])

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
            <div
            key={listing.idOglasa}
            id={`oglas-${listing.idOglasa}`}
           className={`listing-item ${selectedListing == listing.idOglasa ? 'selected' : ''}`}

            
          >
            <Listing
              key={listing.idOglasa}
              ulaznica={listing}
              izvodaci={listing.izvodaci}
              idOglasa={listing.idOglasa}
              availableTickets={availableTickets?.filter(ticket => transakcije?.filter(tr => ticket.idUlaznice == tr.idUlaznicaPonuda)?.filter(tr => tr.idOglas == listing.idOglasa).length == 0)}
            />
            </div>
          ))}
        </div>
      }
    </div>
  );
}

