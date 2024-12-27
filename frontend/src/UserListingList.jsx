import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Listing from './Listing';
import './ListingList.css';

export default function UserListingList() {
  const [listings, setListings] = useState([]);


//   // Dohvati oglase po preferencama prilikom inicijalnog rendera
  useEffect(() => {
    
    const fetchListings = async () => {
      try {
        let response = await axios.get('api/preference/oglasi'); // Dohvaćanje oglasa po preferencama
        let listingsData = response.data;

        // Provjeravamo je li listingsData prazan niz
        if (listingsData.length === 0) {
          // Ako je prazan, dohvaćamo oglase putem drugog endpointa
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
    <div className="container mt-4">
  <div className="row">
    {listings.map((listing) => (
      <div key={listing.idOglasa} className="col-12 col-md-4 col-lg-3 mb-5">
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
