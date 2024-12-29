import React, { useEffect, useState } from 'react'
import {FaSearch} from "react-icons/fa"
import "./SearchBar.css"
import axios from 'axios';
import SearchResultsList from './SearchResultsList';
import { useNavigate } from 'react-router-dom';

export default function SearchBar({setResults,zanrovi}){

   
    const[searchInput, setSearchInput] = useState("");
   
    const navigate = useNavigate();

    
      
      
      

    function handleSearch(){
        const artistName = searchInput.split(" ")[0]
        const artistSurname = searchInput.split(" ")[1]
        
        axios.post("api/preference/oglasi/filter", {
            "imeIzvodaca": artistName,
            "prezimeIzvodaca": artistSurname
           
          }, 
           {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then( async (response) => {
           
             
            //prvo cemo filter tako da ostanu samo oglasi koji nisu prodani ili istekli
            const filteredResults = response.data.filter(element => element.status === "Available")
            
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
                  const izvodaci = izvodaciData.data.map((izvodac) => ({
                    imeIzvodaca: izvodac.imeIzvodaca,
                    prezimeIzvodaca: izvodac.prezimeIzvodaca,
                    žanr: zanrovi[izvodac.zanrId] || "Unknown",
                    foto: izvodac.fotoIzvodaca,
                  }));
          
                  // Kreiraj konačni format za oglas
                  return {
                    datum: ulaznicaData.data.datumKoncerta,
                    lokacija: ulaznicaData.data.lokacijaKoncerta,
                    zona: ulaznicaData.data.odabranaZona,
                    vrstaUlaznice: ulaznicaData.data.vrstaUlaznice,
                    izvodaci: izvodaci,
                  };
                })
              );
          
                setResults(oglasiData)
                navigate("/search");

          })
          .catch(error => {
            console.error('Greška prilikom pretrage:', error);
          }); 
                  

          
         
    }

    function handleKeyDown(event){
        if (event.key === 'Enter') {
            event.preventDefault(); // ovo mozda nije potrebno
            handleSearch();
          }
    }


    function handleChange(event){
        //event.target.value je unesena vrijednost u search baru
        setSearchInput(event.target.value)
        //fetchData(event.target.value)

    }

    return(
        <div className='input-wrapper'>
            
            <FaSearch id="search-icon" />
            <input placeholder='Type to search...' 
                    value={searchInput}
                    onChange={handleChange}
                    onKeyDown={handleKeyDown}
                    
            /> 
            

           
             </div>
             

    )
}