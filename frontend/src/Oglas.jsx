import React, { useState } from "react";
import Ulaznica from "./Ulaznica";
import { remainingDaysToEvent } from './utilities/remainingDaysToEvent'
import "./Oglas.css";
import { FaArrowDown, FaRegTrashAlt } from "react-icons/fa";
import { FaCheck } from "react-icons/fa6";
import { IoClose } from "react-icons/io5";
import { useNavigate } from 'react-router-dom';
import { FaArrowUpRightFromSquare } from "react-icons/fa6";
import { FaTurnUp } from "react-icons/fa6";


export default function Oglas({
    idTransakcije,
    oglasId,
    danaDo,
    ulaznica1,
    ulaznica2,
    naPrihvati,
    naOdbij,
    naObrisi,
    imePonuditelja,
    tipTransakcije,
    idNavigateOglasa
}) {

    const [isOpen, setIsOpen] = useState(true); // Stanje za otvaranje/zatvaranje

    const toggleOpen = () => {
        setIsOpen((prev) => !prev);
    };

    const navigate = useNavigate();

    const handleOdvediMe = (oglasId) => {
        //console.log("id oglasa:  ")
        //console.log(oglasId)
        navigate(`/#${oglasId}`)
    }

    return (
        <div className={`outer-container ${tipTransakcije === "smece" ? "smece-container" : ""}`}>
            
            <span className="header-container" onClick={toggleOpen} style={{ cursor: "pointer" }}>
                <h3>
                {isOpen ?<span style={{ color: "#425DFF", cursor: "pointer" }}>▼</span> : <span style={{ color: "#425DFF", cursor: "pointer" }}>▶</span>}{" "}
                    {idTransakcije
        ? (tipTransakcije === "provedeno" ? `Transakcija #${idTransakcije}` : `Moja ponuda #${idTransakcije}`)
        : `Moj oglas #${oglasId}`}</h3>
             {isOpen && (   <h5 style={{ color: '#787878' }}>
                    <span style={{ fontWeight: 'bold' }}>
                    {tipTransakcije === "smece" ? (
        <span style={{ fontWeight: "bold" }}>
            {danaDo > 0
                ? `${danaDo} dana preostalo do brisanja`
                : "Oglas će uskoro biti obrisan"}
        </span>
    ) : (
        <span style={{ fontWeight: "bold" }}>
            {remainingDaysToEvent(danaDo)} {remainingDaysToEvent(danaDo) > 1 ? "dana" : "dan"}
        </span>
    )}
                    </span>{" "}
                    do koncerta
                </h5> )}
            </span>
            {isOpen && (
            <div className="oglas-container">
                {/* Prikaz prve ulaznice */}
                <div className="ulaznica-row"><Ulaznica {...ulaznica1}/></div>
                    

                    <div className="button-section-2">
            {(tipTransakcije === "poslanePonude" || tipTransakcije === "zaPrihvatiti") && (
                <button className="view-button" onClick={() => {handleOdvediMe(idNavigateOglasa)}} style={{fontSize: '18px'}}>
                    <FaArrowUpRightFromSquare /> Odvedi me
                </button>
            )}
                <button  className="delete-button" onClick={naObrisi} style={{fontSize:'18px', marginLeft: idTransakcije ? '0px' : '8px'}}>
                    <FaRegTrashAlt/>{idTransakcije ? (tipTransakcije === "provedeno" ? "Obriši transakciju" : "Obriši ponudu") : "Obriši oglas"}
                </button>
            </div>
                {/* Strelica između ulaznica */}
                <div className="arrow"><FaTurnUp/></div>

                {/* Prikaz druge ulaznice */}
                <div className="ulaznica-row">
                    {ulaznica2 && ulaznica2.datumKoncerta === "N/A" ? (
                        <div
                            style={{
                                width: "44vw",
                                height: "14vw",
                                backgroundColor: "#D9D9D9",
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                                fontSize: "20px",
                                textDecoration: "underline",
                                color: "#787878",
                                marginLeft: "0vw",
                                marginTop:"2.5vh",
                                borderRadius:"8px",
                                boxShadow: "0 0 5px #00000032",
                                marginBottom:"0.7rem",
                                marginRight:"0.5vw"
                            }}
                        >
                            Nema ponude
                        </div>
                    ) : (
                        <Ulaznica {...ulaznica2}/>
                    )}
                    </div>

                {/* Gumbi */} 
            </div>

)}
            {isOpen && (
            <div className="button-section">
                {tipTransakcije === "zaPrihvatiti" && (
                    <>
                        <button onClick={naPrihvati} className="accept-button">
                            <FaCheck /> Prihvatite
                        </button>
                        <button onClick={naOdbij} className="reject-button">
                            <IoClose style={{ fontSize: "32px" }} /> Odbijte
                        </button>
                        <span className="ponuda">
                            Ponuda od <span style={{ color: '#425DFF' }}>{imePonuditelja}</span>
                        </span>
                    </>
                )}
                {tipTransakcije === "poslanePonude" && (
                    <span className="ponuda">
                        Čeka na odgovor prodavača (<span style={{ color: '#425DFF' }}>{imePonuditelja}</span>)
                    </span>
                )}
                {tipTransakcije === "ostalo" && null}
                {tipTransakcije === "provedeno" && null}
            </div> )}
     
        </div>


   );
}




