import React, { useEffect, useState } from 'react'
import { FaSearch } from "react-icons/fa"
import "./SearchBar.css"
import axios from 'axios';
import SearchResultsList from './SearchResultsList';
import { useNavigate } from 'react-router-dom';

export default function SearchBar({ setSearch }) {

  const [searchInput, setSearchInput] = useState("");
  const navigate = useNavigate();

  function handleSearch() {
    if (searchInput.length > 0) {
      setSearch(searchInput);
      navigate("/search");
    } else {  //query is empty
      navigate("/");
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