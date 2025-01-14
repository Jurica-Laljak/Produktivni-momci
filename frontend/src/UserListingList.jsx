import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Listing from './Listing';
import './ListingList.css';
import axiosPrivate from './api/axiosPrivate';

export default function UserListingList() {
  const [listings, setListings] = useState([]);
  const [weatherData, setWeatherData] = useState({});

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
        let response = await axiosPrivate.get('preference/oglasi');
        let listingsData = response.data;
        console.log("Listings data: ", listingsData)
        if (listingsData.length === 0) {
          const fallbackResponse = await axios.get('/api/oglasi/list/random-max');
          listingsData = fallbackResponse.data;
        }

        const uniqueListings = Array.from(
          new Set(listingsData.map((listing) => listing.idOglasa))
        ).map((idOglasa) =>
          listingsData.find((listing) => listing.idOglasa === idOglasa)
        );

        setListings(uniqueListings);

        
        const weatherResponses = await Promise.all(
          uniqueListings.map((listing) =>
            fetchWeather(listing.lokacijaKoncerta, listing.datumKoncerta)
          )
        );

        
        const weatherMap = uniqueListings.reduce((acc, listing, index) => {
          acc[listing.idOglasa] = weatherResponses[index];
          return acc;
        }, {});

        setWeatherData(weatherMap);
      } catch (error) {
        console.error('Greška pri dohvaćanju oglasa:', error);
      }
    };

    fetchListings();
  }, []);

  const fetchWeather = async (location, date) => {
    try {
      const apiKey = '6bb6d2c8662145aa872134433251001';
      const response = await axios.get(
        `https://api.weatherapi.com/v1/forecast.json?key=${apiKey}&q=${location}&dt=${date}&aqi=no&alerts=no`
      );

      const forecastDay = response.data.forecast.forecastday[0];
      return {
        maxTemp: forecastDay.day.maxtemp_c,
        minTemp: forecastDay.day.mintemp_c,
        condition: forecastDay.day.condition.text,
        icon: forecastDay.day.condition.icon,
      };
    } catch (error) {
      console.error('Greška pri dohvaćanju vremenske prognoze:', error);
      return { maxTemp: null, minTemp: null, condition: 'N/A', icon: '' };
    }
  };

  return (
    <div className="listingContainer">
      {listings.map((listing) => (
        <Listing
          key={listing.idOglasa}
          ulaznica={listing}
          izvodaci={listing.izvodaci}
          weather={weatherData[listing.idOglasa]} 
        />
      ))}
    </div>
  );
}
