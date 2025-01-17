import React from "react";
import Ulaznica from "./Ulaznica";
import { remainingDaysToEvent } from './utilities/remainingDaysToEvent'
import "./Oglas.css";

export default function Oglas({
    oglasId,
    danaDo,
    ulaznica1,
    ulaznica2,
    statusPonude,
    naUredi,
    naObrisi,
    naPogledaj,
}) {
    return (
        <div className="outer-container">
            <span className="header-container">
        <h3>Moj oglas #{oglasId}</h3>
        <h5 style={{color:'#787878'}}><span style={{fontWeight:'bold'}}>{remainingDaysToEvent(danaDo)}
            {remainingDaysToEvent(danaDo) > 1
              ? " dana"
              : " dan"}</span> do koncerta</h5>
              </span>
        <div className="oglas-container">

            {/* Prikaz prve ulaznice */}
            <div className="ulaznica-row">
                <Ulaznica {...ulaznica1} />
            </div>

            {/* Strelica između ulaznica */}
            <div className="arrow">⬇️</div> 

            {/* Prikaz druge ulaznice */}
            <div className="ulaznica-row">
           {/* <img src="src\assets\arrow.png" style={{height:'74px'}}></img> */}
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
                <button onClick={naPogledaj} className="view-button" style={{fontSize:'18px'}}><img src="src\assets\view.png" style={{height:'15px'}}/>Pogledaj</button> 
                <button onClick={naObrisi} className="delete-button" style={{fontSize:'18px'}}><img src="src\assets\trash.png"style={{height:'15px'}}/>Obriši</button>
            </div>
        </div>
        <div className="button-section">
                <button className="accept-button"><img src="src\assets\tick.png" style={{height:'15px'}}/>Prihvatite</button> 
                <span className="ponuda">Ponuda od <span style={{color:'#425DFF'}}>Ime Prezime</span></span>
                <button className="reject-button"><img src="src\assets\x.png"style={{height:'15px'}}/>Odbijte</button>
                </div>
        </div>
    );
}


