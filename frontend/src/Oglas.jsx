import React from "react";
import Ulaznica from "./Ulaznica";
import "./Oglas.css";

export default function Oglas({
    oglasId,
    ulaznica1,
    ulaznica2,
    statusPonude,
    naUredi,
    naObrisi,
    naPogledaj,
}) {
    return (
        <div className="oglas-container">
            <h3>Vaš oglas #{oglasId}</h3>

            {/* Prikaz prve ulaznice */}
            <div className="ulaznica-row">
                <Ulaznica {...ulaznica1} />
            </div>

            {/* Strelica između ulaznica */}
            <div className="arrow">⬇️</div>

            {/* Prikaz druge ulaznice */}
            <div className="ulaznica-row">
                <Ulaznica {...ulaznica2} />
            </div>

            {/* Status ponude 
            <div className="status-section">
                <p>{statusPonude ? `Ponuda od ${statusPonude}` : "Nema ponude"}</p>
            </div>
            */}

            {/* Gumbi */}
            <div className="button-section">
               {/*<button onClick={naUredi} className="edit-button">Uredi</button>*/}
                <button onClick={naPogledaj} className="view-button">Pogledaj</button> 
                <button onClick={naObrisi} className="delete-button">Obriši</button>
            </div>
        </div>
    );
}


