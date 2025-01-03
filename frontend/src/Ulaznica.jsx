import React from "react";
import "./Ulaznica.css";
import QRCode from "react-qr-code";

export default function Ulaznica({ datumKoncerta, lokacijaKoncerta, odabranaZona, sifraUlaznice, urlSlika, vrstaUlaznice, urlInfo }) {
    return (
        <div className="ticket-container">
          <div className="ticket">
            <div className="qr-code">
              
              <QRCode value={urlInfo} size={128} />
            </div>
            <div className="ticket-info">
              <div className="event-details">
                <p><strong>{lokacijaKoncerta}</strong></p>
                <p>{odabranaZona}</p> 
                <p>{vrstaUlaznice}</p> 
                <p>{datumKoncerta}</p> 
                <p>{sifraUlaznice}</p> 
              </div>
              <div className="event-image">
                <img src={urlSlika} alt="Event Image" /> 
              </div>
            </div>
          </div>
        </div>
    );
}
