import React, { useState, useEffect } from "react";
import axiosPrivate from "./api/axiosPrivate";
import Oglas from "./Oglas";
import { useNavigate } from 'react-router-dom';
import "./UserOglasi.css";

export default function UserOglasi() {
    const [zaprimljeneTransakcije, setZaprimljeneTransakcije] = useState([]);
    const [poslanePonude, setPoslanePonude] = useState([]);
    const [ulazniceKojeNisuTransakcija, setUlazniceKojeNisuTransakcija] = useState([]);
    const [error, setError] = useState(null);
    const [openedSections, setOpenedSections] = useState({}); // Stanje za praćenje otvorenih sekcija

    // Placeholder ulaznica
    const placeholderUlaznica = {
        idUlaznice: 0,
        datumKoncerta: "N/A",
        lokacijaKoncerta: "Nepoznato",
        odabranaZona: "N/A",
        vrstaUlaznice: "N/A",
        urlSlika: "https://via.placeholder.com/150",
        urlInfo: "#",
    };

    const initializeOpenedSections = (transakcije, ponude, ulaznice) => {
        const newSections = {};
        // Otvori sve sekcije
        newSections["zaprimljenePonude"] = true;
        newSections["poslanePonude"] = true;
        newSections["ostaleUlaznice"] = true;

        // Otvori sve oglase unutar sekcija
        [...transakcije, ...ponude, ...ulaznice].forEach((item) => {
            newSections[item.idTransakcije || item.idUlaznice] = true;
        });

        setOpenedSections(newSections);
    };

    const toggleSection = (sectionId) => {
        setOpenedSections((prev) => ({
            ...prev,
            [sectionId]: !prev[sectionId],
        }));
    };

    // Funkcija za dohvaćanje transakcija koje čekaju potvrdu
    const fetchZaprimljeneTransakcije = async () => {
        try {
            const response = await axiosPrivate.get("preference/transakcije/za-potvrditi");
            const transakcije = await Promise.all(
                response.data.map(async (transakcija) => {
                    const ulaznicaOglas = await axiosPrivate.get(
                        `ulaznice/${transakcija.idUlaznicaOglas}`
                    );
                    const ulaznicaPonuda = await axiosPrivate.get(
                        `ulaznice/${transakcija.idUlaznicaPonuda}`
                    );
                    return {
                        ...transakcija,
                        ulaznicaOglas: ulaznicaOglas.data,
                        ulaznicaPonuda: ulaznicaPonuda.data,
                    };
                })
            );
            setZaprimljeneTransakcije(transakcije);
            return transakcije;
        } catch (err) {
            console.error("Greška prilikom dohvaćanja zaprimljenih transakcija:", err);
            setError("Došlo je do greške prilikom dohvaćanja zaprimljenih transakcija.");
            return [];
        }
    };


    // Funkcija za dohvaćanje poslanih ponuda
    const fetchPoslanePonude = async () => {
        try {
            const response = await axiosPrivate.get("preference/transakcije/poslane-ponude");
            const ponude = await Promise.all(
                response.data.map(async (transakcija) => {
                    const ulaznicaOglas = await axiosPrivate.get(
                        `ulaznice/${transakcija.idUlaznicaOglas}`
                    );
                    const ulaznicaPonuda = await axiosPrivate.get(
                        `ulaznice/${transakcija.idUlaznicaPonuda}`
                    );
                    return {
                        ...transakcija,
                        ulaznicaOglas: ulaznicaOglas.data,
                        ulaznicaPonuda: ulaznicaPonuda.data,
                    };
                })
            );
            setPoslanePonude(ponude);
            return ponude;
        } catch (err) {
            console.error("Greška prilikom dohvaćanja poslanih ponuda:", err);
            setError("Došlo je do greške prilikom dohvaćanja poslanih ponuda.");
            return [];
        }
    };

    // Funkcija za dohvaćanje svih ulaznica
    const fetchUlaznice = async () => {
        try {
            const response = await axiosPrivate.get("preference/korisnici/ulaznice");
            const ulaznice = response.data.filter(
                (ulaznica) =>
                    ulaznica.transakcijePonudaIds.length === 0 &&
                    ulaznica.transakcijeOglasIds.length === 0
            );
            setUlazniceKojeNisuTransakcija(ulaznice);
            return ulaznice;
        } catch (err) {
            console.error("Greška prilikom dohvaćanja ulaznica:", err);
            setError("Došlo je do greške prilikom dohvaćanja ulaznica.");
            return [];
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            const [transakcije, ponude, ulaznice] = await Promise.all([
                fetchZaprimljeneTransakcije(),
                fetchPoslanePonude(),
                fetchUlaznice(),
            ]);
            initializeOpenedSections(transakcije, ponude, ulaznice);
        };

        fetchData();
    }, []);

    const prihvatiPonudu = async (idTransakcije) => {
        try {
            await axiosPrivate.put(`transakcije/${idTransakcije}/prihvati`);
            setZaprimljeneTransakcije((prev) =>
                prev.filter((t) => t.idTransakcije !== idTransakcije)
            );
        } catch (err) {
            console.error("Greška prilikom prihvaćanja ponude:", err);
            setError("Došlo je do greške prilikom prihvaćanja ponude.");
        }
    };

    const odbijPonudu = async (idTransakcije) => {
        try {
            await axiosPrivate.put(`transakcije/${idTransakcije}/odbij`);
            setZaprimljeneTransakcije((prev) =>
                prev.filter((t) => t.idTransakcije !== idTransakcije)
            );
        } catch (err) {
            console.error("Greška prilikom odbijanja ponude:", err);
            setError("Došlo je do greške prilikom odbijanja ponude.");
        }
    };

    const obrisiTransakciju = async (idTransakcije) => {
        try {
            await axiosPrivate.delete(`transakcije/izbrisi/${idTransakcije}`);
            setPoslanePonude((prev) =>
                prev.filter((t) => t.idTransakcije !== idTransakcije)
            );
        } catch (err) {
            console.error("Greška prilikom brisanja transakcije:", err);
            setError("Došlo je do greške prilikom brisanja transakcije.");
        }
    };

    const obrisiOglas = async (idOglas) => {
        try {
            await axiosPrivate.delete(`oglasi/${idOglas}`);
            setUlazniceKojeNisuTransakcija((prev) =>
                prev.filter((u) => u.idUlaznice !== idOglas)
            );
        } catch (err) {
            console.error("Greška prilikom brisanja oglasa:", err);
            setError("Došlo je do greške prilikom brisanja oglasa.");
        }
    };

    // Renderiranje
    return (
        <div>
            <h3>Moji oglasi</h3>
            {error && <p style={{ color: "red" }}>{error}</p>}

            {/* Zaprimljene Ponude */}
            <section>
                <h3 onClick={() => toggleSection("zaprimljenePonude")} style={{ color: "#425DFF", cursor: "pointer" }}>
                    {openedSections["zaprimljenePonude"] ? "▼" : "▶"} Zaprimljene Ponude
                </h3>
                {openedSections["zaprimljenePonude"] && (
                    zaprimljeneTransakcije.length > 0 ? (
                        zaprimljeneTransakcije.map((transakcija) => (
                            <div key={transakcija.idTransakcije}>
                              {/*   <h3
                                    onClick={() => toggleAd(transakcija.idTransakcije)}
                                    style={{ color: "#425DFF", cursor: "pointer" }}
                                >
                                    {openedSections[transakcija.idTransakcije] ? "▼" : "▶"} Oglas #{transakcija.idTransakcije}
                                </h3> */}
                                {openedSections[transakcija.idTransakcije] && (
                                    <Oglas
                                        oglasId={transakcija.idOglas}
                                        danaDo={ulaznicaOglas.datumKoncerta} //treba popravit
                                        ulaznica1={transakcija.ulaznicaOglas}
                                        ulaznica2={transakcija.ulaznicaPonuda}
                                        statusPonude={transakcija.status}
                                        naPrihvati={() => prihvatiPonudu(transakcija.idTransakcije)}
                                        naOdbij={() => odbijPonudu(transakcija.idTransakcije)}
                                        naObrisi={() => obrisiTransakciju(transakcija.idTransakcije)}
                                        imePonuditelja={transakcija.imePonuditelja} //treba popravit
                                        tipTransakcije="zaPrihvatiti"
                                    />
                                )}
                            </div>
                        ))
                    ) : (
                        <p>Nema zaprimljenih ponuda.</p>
                    )
                )}
            </section>

            {/* Poslane Ponude */}
            <section>
                <h3 onClick={() => toggleSection("poslanePonude")} style={{ color: "#425DFF", cursor: "pointer" }}>
                    {openedSections["poslanePonude"] ? "▼" : "▶"} Poslane Ponude
                </h3>
                {openedSections["poslanePonude"] && (
                    poslanePonude.length > 0 ? (
                        poslanePonude.map((transakcija) => (
                            <div key={transakcija.idTransakcije}>
                                 {/*<h3
                                    onClick={() => toggleAd(transakcija.idTransakcije)}
                                    style={{ color: "#425DFF", cursor: "pointer" }}
                                >
                                    {openedSections[transakcija.idTransakcije] ? "▼" : "▶"} Oglas #{transakcija.idTransakcije}
                                </h3> */}
                                {openedSections[transakcija.idTransakcije] && (
                                    <Oglas
                                        idTransakcije={transakcija.idTransakcije}
                                     //   oglasId={transakcija.idTransakcije}
                                        danaDo={transakcija.datumKoncerta} //treba popravit
                                        ulaznica1={transakcija.ulaznicaPonuda}
                                        ulaznica2={transakcija.ulaznicaOglas}
                                        statusPonude={transakcija.status}
                                        naObrisi={() => obrisiTransakciju(transakcija.idTransakcije)}
                                        imePonuditelja={transakcija.imePonuditelja} //treba popravit
                                        tipTransakcije="poslanePonude"
                                    />
                                )}
                            </div>
                        ))
                    ) : (
                        <p>Nema poslanih ponuda.</p>
                    )
                )}
            </section>

            {/* Ulaznice koje nisu transakcije */}
            <section>
                <h3 onClick={() => toggleSection("ostaleUlaznice")} style={{ color: "#425DFF", cursor: "pointer" }}>
                    {openedSections["ostaleUlaznice"] ? "▼" : "▶"} Ostale Ulaznice
                </h3>
                {openedSections["ostaleUlaznice"] && (
                    ulazniceKojeNisuTransakcija.length > 0 ? (
                        ulazniceKojeNisuTransakcija.map((ulaznica) => (
                            <div key={ulaznica.idUlaznice}>
                              {/*  <h3
                                    onClick={() => toggleAd(ulaznica.idUlaznice)}
                                    style={{ color: "#425DFF", cursor: "pointer" }}
                                >
                                    {openedSections[ulaznica.idUlaznice] ? "▼" : "▶"} Oglas #{ulaznica.idUlaznice}
                                </h3> */}
                                {openedSections[ulaznica.idUlaznice] && (
                                    <Oglas
                                        oglasId={ulaznica.idUlaznice}
                                        danaDo={ulaznica.datumKoncerta}
                                        ulaznica1={ulaznica}
                                        ulaznica2={{ datumKoncerta: "N/A" }}
                                        naObrisi={() => obrisiOglas(ulaznica.idUlaznice)}
                                        tipTransakcije="ostalo"
                                    />
                                )}
                            </div>
                        ))
                    ) : (
                        <p>Nema ulaznica.</p>
                    )
                )}
            </section>
        </div>
    );
}


