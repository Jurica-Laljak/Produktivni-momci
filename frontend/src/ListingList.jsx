import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Listing from './Listing';
import './ListingList.css';

export default function ListingList() {
  const [listings, setListings] = useState([]);

  // Dohvati oglase prilikom inicijalnog rendera
  useEffect(() => {
    const fetchListings = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/oglasi/list/12'); // Prikaz 12 random oglasa
        const listingsData = response.data;

        const listingsWithDetails = await Promise.all(
          listingsData.map(async (listing) => {
            const izvodaciResponse = await axios.get(`http://localhost:8080/api/oglasi/${listing.idOglasa}/izvodaci`);
            const ulaznicaResponse = await axios.get(`http://localhost:8080/api/ulaznice/${listing.ulaznicaId}`);
            
            const izvodaciWithGenres = await Promise.all(
              izvodaciResponse.data.map(async (izvodac) => {
                try {
                  const genreResponse = await axios.get(`http://localhost:8080/api/zanrovi/${izvodac.zanrId}`);
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
    <div className="container listingContainer mt-4">
      <div className="row">
        {listings.map((listing) => (
          <div key={listing.idOglasa} className="col-md-4 col-lg-3 mb-5">
            <Listing
              idOglasa={listing.idOglasa}
              status={listing.status}
              ulaznica={listing.ulaznica}
              izvodaci={listing.izvodaci}
            />
          </div>
        ))}
      </div>
    </div>
  );
}

