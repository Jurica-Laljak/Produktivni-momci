import React from "react";
import Ulaznica from "./Ulaznica";
import { remainingDaysToEvent } from './utilities/remainingDaysToEvent'
import "./Oglas.css";
import { FaArrowDown, FaRegTrashAlt } from "react-icons/fa";
import { FaCheck } from "react-icons/fa6";
import { IoClose } from "react-icons/io5";

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
    tipTransakcije, // Dodan prop za prepoznavanje tipa transakcije
}) {
    return (
        <div className={`outer-container ${tipTransakcije === "smece" ? "smece-container" : ""}`}>
            
            <span className="header-container">
                <h3>{tipTransakcije === "poslanePonude"
        ? `Moja ponuda #${idTransakcije}`
        : `Moj oglas #${oglasId}`}</h3>
                <h5 style={{ color: '#787878' }}>
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
                </h5>
            </span>

            <div className="oglas-container">
                {/* Prikaz prve ulaznice */}
                <div className="ulaznica-row">
                    <Ulaznica {...ulaznica1} />
                </div>

                {/* Strelica između ulaznica */}
                <div className="arrow"><FaArrowDown /></div> 

                {/* Prikaz druge ulaznice */}
                <div className="ulaznica-row">
                    {ulaznica2.datumKoncerta === "N/A" ? (
                        <div
                            style={{
                                width: "50vw",
                                height: "15vw",
                                backgroundColor: "#D9D9D9",
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                                fontSize: "20px",
                                textDecoration: "underline",
                                color: "#787878",
                                marginLeft: "7.5vw",
                            }}
                        >
                            Nema ponude
                        </div>
                    ) : (
                        <Ulaznica {...ulaznica2} />
                    )}
                </div>
                {/* Gumbi */}
            <div className="button-section">
                <button  className="delete-button" onClick={() => {naObrisi(); premjestiUKos();}} style={{fontSize:'18px'}}>
                    <FaRegTrashAlt/>Obriši
                </button>
            </div>
            </div>

            {/* Prikaz statusa i gumba */}
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
            </div>
        </div>
    );
}



