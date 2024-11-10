import React, { useEffect, useState } from 'react'
import './SearchResultsList.css'

export default function SearchResultsList({results}){
    return(
      <div className="container-fluid mt-5">
      <h2 className="text-center mb-4">Rezultati Pretrage</h2>
      {results.length === 0 ? (
        <p className="text-center">Nema oglasa koji odgovaraju vašem pretraživanju.</p>
      ) : (
        <div className="row">
          {results.map((oglas, index) => (
            <div key={index} className="col-md-4 mb-4">
              <div className="card shadow-sm h-100 bg-light">
                <div className="card-body">
                  <h5 className="card-title text-primary" style={{ color: '#455dfb !important' }}><strong>{oglas.datum}</strong> </h5>
                  <p className="card-text"><strong>Lokacija:</strong> {oglas.lokacija}</p>
                  <p className="card-text"><strong>Zona:</strong> {oglas.zona}</p>
                  <p className="card-text"><strong>Vrsta Ulaznice:</strong> {oglas.vrstaUlaznice}</p>

                  <div className="izvodaci">
                    <h6 className="text-secondary">Izvođači:</h6>
                    {oglas.izvodaci.map((izvodac, idx) => (
                      <p key={idx} className="mb-1">
                        <span className="font-weight-bold">{izvodac.imeIzvodaca} {izvodac.prezimeIzvodaca}</span> ({izvodac.žanr})
                      </p>
                    ))}
                  </div>
                </div>
                <div className="card-footer text-center">
                  <button className="btn btn-success btn-block">View Details</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
    )
}