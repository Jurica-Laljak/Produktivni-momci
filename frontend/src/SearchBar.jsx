import React, { useEffect, useState } from 'react'
import {FaSearch} from "react-icons/fa"
import "./SearchBar.css"
import axios from 'axios';


export default function SearchBar({setResults}){

    const[searchInput, setSearchInput] = useState("");

    function fetchData(searchInput){
        //get zahtjev na backend
        axios.get()
        .then(response => {
            const results = response.filter(oglas =>{
                return(
                    oglas.artist.toLowerCase().includes(value)
                )
            })
            setResults(results)
        })

    }


    function handleChange(event){
        setSearchInput(event.target.value)

    }

    return(
        <div className='input-wrapper'>
            <FaSearch id="search-icon" />
            <input placeholder='Type to search...' 
                    value={searchInput}
                    onChange={handleChange}
            /> 
             </div>

    )
}