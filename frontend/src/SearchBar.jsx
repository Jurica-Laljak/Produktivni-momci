import React, { useEffect, useState } from 'react'
import {FaSearch} from "react-icons/fa"
import "./SearchBar.css"
import axios from 'axios';
import SearchResultsList from './SearchResultsList';

export default function SearchBar({setResults}){

   
    const[searchInput, setSearchInput] = useState("");
   
/*
    function fetchData(searchInput){
        //get zahtjev na backend
        axios.get('/data/data.json')
        .then(response => {
            const rezultati = response.data.filter(oglas =>{
                return(
                    oglas.firstName.toLowerCase().includes(searchInput)
                    ||
                    oglas.lastName.toLowerCase().includes(searchInput)
                    ||
                    oglas.city.toLowerCase.includes(searchInput)
                )
            })
            setResults(rezultati)
        })

    } */

    function handleSearch(){
        const artistName = searchInput.split(" ")[0]
        const artistSurname = searchInput.split(" ")[1]
        
      /*  axios.post("api/preference/oglasi/filter", {
            "imeIzvodaca": artistName,
            "prezimeIzvodaca": artistSurname
           
          }, 
           {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then(response => {
           // console.log('Rezultati pretrage:', response.data);
           // dodati onda logiku za prikaz oglasa koji su vraceni
                   // console.log(response)
           // napraviti novi get(ustvari post vjerojatno a ne get) gdje cemo sa idOglasa ili cime vec dobit izvodaca
           // i sa idKoncerta mjesto odrzavanja koncerta -> novi post a ne get, isto tako i za prvi
            // trebo bi radit map kroz vracene oglase i unutar tog mapa radit te getove

            //prvo cemo filter tako da ostanu samo oglasi koji nisu prodani ili istekli
            //const  filteredResults = response.data.filter(element => element.status === "Available")
            //console.log(filteredResults)
            console.log(response.data)
          })
          .catch(error => {
            console.error('Greška prilikom pretrage:', error);
          }); 
                  */
          fetch('api/preference/oglasi/filter', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "imeIzvodaca": artistName,
                "prezimeIzvodaca": artistSurname
            })
        })
        .then(response => response.json())  // Pretvaranje odgovora u JSON
        .then(data => {
            console.log(data);
            // Logika za prikaz oglasa koji su vraćeni
            // Možeš filtrirati oglase ili raditi dodatne zahtjeve prema id-u oglasa ili izvođača
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