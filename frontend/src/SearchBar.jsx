import React, { useEffect, useState } from 'react'
import { FaSearch } from "react-icons/fa"
import "./SearchBar.css"
import axios from 'axios';
import SearchResultsList from './SearchResultsList';
import { useNavigate } from 'react-router-dom';

export default function SearchBar({ setResults, zanrovi }) {


  const [searchInput, setSearchInput] = useState("");

  const navigate = useNavigate();






  function handleSearch() {
    if (searchInput.length > 0) {
      axios.post("api/preference/oglasi/filter", {
        "pretraga": searchInput
      },
        {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        .then(async (response) => {

          //prvo cemo filter tako da ostanu samo oglasi koji nisu prodani ili istekli
          const filteredResults = response.data.filter(element => element.status === "AKTIVAN")

          //dohvacamo sve potrebne podatke za svaki oglas
          const oglasiData = await Promise.all(
            filteredResults.map(async (oglas) => {
              // Dohvati podatke o ulaznici
              const ulaznicaData = await axios.get(
                `api/ulaznice/${oglas.ulaznicaId}`
              );

              // Dohvati podatke o izvođačima
              const izvodaciData = await axios.get(
                `api/oglasi/${oglas.idOglasa}/izvodaci`
              );

              // Dohvati sve žanrove
              /* const zanroviData = await axios.get("api/zanrovi");
               const zanroviMap = zanroviData.data.reduce((map, zanr) => {
                 map[zanr.idZanra] = zanr.imeZanra;
                 return map;
               }, {});*/

              // Mapiraj izvođače u odgovarajući format
              console.log("Izvodaci data: ", izvodaciData.data)
              console.log("Ulaznica data: ", ulaznicaData.data)
              /*
              const izvodaci = izvodaciData.data.map((izvodac) => ({
                imeIzvodaca: izvodac.imeIzvodaca,
                prezimeIzvodaca: izvodac.prezimeIzvodaca,
                žanr: zanrovi[izvodac.zanrId] || "Unknown",
                foto: izvodac.fotoIzvodaca,
              })); */

              // Kreiraj konačni format za oglas
              /*
              return {
                datum: ulaznicaData.data.datumKoncerta,
                lokacija: ulaznicaData.data.lokacijaKoncerta,
                zona: ulaznicaData.data.odabranaZona,
                vrstaUlaznice: ulaznicaData.data.vrstaUlaznice,
                izvodaci: izvodaci,
              }; */
              return {
                "ulaznica": ulaznicaData.data,
                "izvodaci": izvodaciData.data
              }
            })
          );

          setResults(oglasiData)
          console.log("Oglasi data:", oglasiData)
          navigate("/search");

        })
        .catch(error => {
          console.error('Greška prilikom pretrage:', error);
        });

    } else {  //query is empty
        navigate("/")
    }


  }

  function handleKeyDown(event) {
    if (event.key === 'Enter') {
      event.preventDefault(); // ovo mozda nije potrebno
      handleSearch();
    }
  }


  function handleChange(event) {
    //event.target.value je unesena vrijednost u search baru
    setSearchInput(event.target.value)
    //fetchData(event.target.value)

  }

  return (
    <div className='input-wrapper'>

      <FaSearch id="search-icon" />
      <input placeholder='Pretražite oglase'
        value={searchInput}
        onChange={handleChange}
        onKeyDown={handleKeyDown}

      />



    </div>


  )
}