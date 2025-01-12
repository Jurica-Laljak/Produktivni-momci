import React, { useEffect, useState } from 'react'
import './SearchResultsList.css'
import Listing from './Listing'
import './ListingList.css'

export default function SearchResultsList({ results }) {
  return (
    <div className="container-fluid mt-5">
      <h2 className="text-center mb-4">{results.length} rezultata pretrage</h2>
      {results.length === 0 ? (
        <p className="text-center">Nema oglasa koji odgovaraju vašem pretraživanju.</p>
      ) : (
        <div className="listingContainer">
          {results.map((oglas, index) => {
            //  Formatiramo datum u "dd.mm.yyyy" format
            console.log("Oglas: ", oglas, " Index: ", index)

            return (
              <Listing
                        key={index}
                        listing={oglas}/>
            )
            /*
            const formattedDate = new Date(oglas.datum).toLocaleDateString("hr-HR");

            return (
              <div key={index} className="col-md-4 mb-4">
                <div className="card shadow-sm h-100 bg-light">
                  <div className="card-body">
                    
                    <h5 className="card-title text-primary" style={{ color: '#455dfb' }}>
                      <strong>{oglas.lokacija}</strong>
                    </h5>

                   
                    <p className="card-text"><strong>Datum:</strong> {formattedDate}</p>
                    
                    <p className="card-text"><strong>Zona:</strong> {oglas.zona}</p>
                    <p className="card-text"><strong>Vrsta Ulaznice:</strong> {oglas.vrstaUlaznice}</p>

                    <div className="izvodaci">
                      <h6 className="text-secondary">Izvođači:</h6>
                      {oglas.izvodaci.map((izvodac, idx) => (
                        <p key={idx} className="mb-1">
                          <span className="font-weight-bold">
                            {izvodac.imeIzvodaca} {izvodac.prezimeIzvodaca}
                          </span> ({izvodac.žanr})
                        </p>
                      ))}
                    </div>
                  </div>
                </div>
              </div>
            ); */
          })}
        </div>
      )}
    </div>
  );
}
