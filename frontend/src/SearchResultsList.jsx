import React, { useEffect, useState } from 'react'
import './SearchResultsList.css'
import Listing from './Listing'
import './ListingList.css'
import ScaleLoader from "react-spinners/ScaleLoader";
import axios from 'axios';

export default function SearchResultsList({ search, userData }) {

  const [loading, setLoading] = useState(true);
  const [results, setResults] = useState([]);
  const [availableTickets, setAvailableTickets] = useState(null);

  useEffect(() => {
    setLoading(true);

    axios.post("api/preference/oglasi/filter", {
    "pretraga": search
  },
    {
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(async (response) => {
      let filteredResults = response.data;
      if(userData)
        filteredResults = filteredResults.filter(oglas => oglas.idKorisnika != userData.idKorisnika)

      //prvo cemo filter tako da ostanu samo oglasi koji nisu prodani ili istekli
      console.log("Filtered res: ", filteredResults)
      setResults(filteredResults);
      setLoading(false);
    })
    .catch(error => {
      console.error('Greška prilikom pretrage:', error);
    });
  },[search]);

  useEffect(() => {
    const fetchUserTickets = async () => {
      try {
        // Poziv API-ja za dohvaćanje ulaznica
        const response = await axiosPrivate.get('preference/korisnici/ulaznice');
        const userTickets = response.data;

        // Broji koliko ulaznica ima trenutni korisnik
        const freeTickets = userTickets.filter(ticket => !ticket.oglas).length;
        setAvailableTickets(freeTickets);
      } catch (error) {
        console.error('Greška pri dohvaćanju ulaznica:', error);
        setAvailableTickets(0); // Ako dođe do greške, postavi 0
      }
    };

    if(localStorage.getItem("token"))
      fetchUserTickets();
  }, []);


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
    : <div className="container-fluid mt-5">
      <h2 className="text-center mb-4">{results.length} rezultata pretrage</h2>
      {results.length === 0 ? (
        <p className="text-center">Nema oglasa koji odgovaraju vašem pretraživanju.</p>
      ) : (
        <div className="listingContainer">
          {results.map((listing) =>
                  <Listing
                    key={listing.idOglasa}
                    ulaznica={listing}
                    izvodaci={listing.izvodaci}
                    availableTickets={availableTickets}
                  />
          )}
        </div>
      )}
    </div>}
    </div>
  );
}
