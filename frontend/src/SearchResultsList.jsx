import React, { useEffect, useState } from 'react'
import './SearchResultsList.css'

export default function SearchResultsList({results}){
    return(
        <div className="search-results-wrapper">
      {results.length > 0 ? (
        results.map((result, index) => (
          <div key={index} className="search-result-item">
            {result.firstName} {/* Prikaz podataka za pojedini rezultat */}
          </div>
        ))
      ) : (
        <div className="no-results">No results found</div>
      )}
    </div>
    )
}