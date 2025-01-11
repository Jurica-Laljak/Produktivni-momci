import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Listing from './Listing';
import './ListingList.css';

export default function ListingList() {
  const [listings, setListings] = useState([]);

  useEffect(() => {
    const fetchListings = async () => {
      try {
        const response = await axios.get('api/oglasi/list/12'); // Prikaz 12 random oglasa
        const listingsData = response.data;
        console.log(response.data)

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

