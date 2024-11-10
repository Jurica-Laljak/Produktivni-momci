import React from 'react';
import './Listing.css';

export default function Listing({ idOglasa, status, ulaznica, izvodaci }) {
  return (
    <div className="card shadow-sm h-100 bg-light custom-card">
      <div className="card-body">
        <h5 className="card-title" style={{ color: '#455dfb' }}><strong>{ulaznica.datumKoncerta}</strong> </h5>
        <p className="card-text"><strong>Lokacija:</strong> {ulaznica.lokacijaKoncerta}</p>
        <p className="card-text"><strong>Zona:</strong> {ulaznica.odabranaZona}</p>
        <p className="card-text"><strong>Vrsta Ulaznice:</strong> {ulaznica.vrstaUlaznice}</p>
        
        <div className="izvodaci mt-4">
          <h6 className="text-secondary">Izvođači:</h6>
          {izvodaci.map((izvodac, idx) => (
            <div key={idx} className="mb-3">
              <p className="font-weight-bold mb-0">
                {izvodac.imeIzvodaca} {izvodac.prezimeIzvodaca} ({izvodac.zanr})
              </p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}




