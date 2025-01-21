import React, { useState, useEffect } from "react";
import axiosPrivate from "./api/axiosPrivate";
import Oglas from "./Oglas";
import { useNavigate } from 'react-router-dom';
import "./UserOglasi.css";
import { differenceInDays } from "date-fns";
import ScaleLoader from "react-spinners/ScaleLoader";

export default function UserOglasi() {
    const [zaprimljeneTransakcije, setZaprimljeneTransakcije] = useState([]);
    const [poslanePonude, setPoslanePonude] = useState([]);
    const [oglasiBezPonuda, setOglasiBezPonuda] = useState([]);
    const [error, setError] = useState(null);
    const [kosZaSmece, setKosZaSmece] = useState([]); // Novo stanje za "Koš za smeće"
    const [openedSections, setOpenedSections] = useState({}); // Stanje za praćenje otvorenih sekcija
    const [provedeneTransakcije, setProvedeneTransakcije] = useState([]);
    const [loading, setLoading] = useState(true);

    const initializeOpenedSections = (transakcije, ponude, ulaznice) => {
        const newSections = {};
        // Otvori sve sekcije
        newSections["zaprimljenePonude"] = true;
        newSections["poslanePonude"] = true;
        newSections["ostaliOglasi"] = true;

        // Otvori sve oglase unutar sekcija
        [...transakcije, ...ponude, ...ulaznice].forEach((item) => {
            newSections[item.idTransakcije || item.idOglasa] = true;
        });

        setOpenedSections(newSections);
    };

    const toggleSection = (sectionId) => {
        setOpenedSections((prev) => ({
            ...prev,
            [sectionId]: !prev[sectionId],
        }));
    };

    useEffect(() => {
        const interval = setInterval(() => {
            const danasnjiDatum = new Date();
            setKosZaSmece((prev) =>
                prev.filter((oglas) => {
                    const protekliDani = differenceInDays(danasnjiDatum, new Date(oglas.dateAdded));
                    return protekliDani < 14; // Zadrži oglase mlađe od 14 dana
                })
            );
        }, 24 * 60 * 60 * 1000); // Provjera jednom dnevno (svakih 24 sata)
    
        return () => clearInterval(interval); // Očistite interval prilikom unmounta
    }, []);

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
                    const korisnikPonuda = await axiosPrivate.get(
                        `korisnici/${transakcija.idKorisnikPonuda}`
                    );
                    return {
                        ...transakcija,
                        ulaznicaOglas: ulaznicaOglas.data,
                        ulaznicaPonuda: ulaznicaPonuda.data,
                        korisnikPonuda: korisnikPonuda.data
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
                    const korisnikProdavac = await axiosPrivate.get(
                        `korisnici/${transakcija.idKorisnikOglas}`
                    );
                    return {
                        ...transakcija,
                        ulaznicaOglas: ulaznicaOglas.data,
                        ulaznicaPonuda: ulaznicaPonuda.data,
                        korisnikProdavac: korisnikProdavac.data
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
    const fetchOglasi = async () => {
        try {
            const response = await axiosPrivate.get("preference/oglasi/aktivni");
            const oglasi = response.data.filter(
                (oglas) => oglas.transakcijeIds.length === 0);
            const ponude = await Promise.all(
                oglasi.map(async (oglas) => {
                    const ulaznicaOglas = await axiosPrivate.get(
                        `ulaznice/${oglas.ulaznicaId}`
                    );
                    const korisnikOglas = await axiosPrivate.get(
                        `korisnici/${oglas.korisnikId}`
                    );
                    return {
                        ...oglas,
                        ulaznicaOglas: ulaznicaOglas.data,
                        korisnikOglas: korisnikOglas.data
                    };
                })
            );
            setOglasiBezPonuda(ponude);
            console.log(ponude);
            return ponude;
        } catch (err) {
            console.error("Greška prilikom dohvaćanja ulaznica:", err);
            setError("Došlo je do greške prilikom dohvaćanja ulaznica.");
            return [];
        }
    };

    const fetchProvedeneTransakcije = async () => {
        try {
            const response = await axiosPrivate.get("preference/transakcije/provedene");
            const transakcije = await Promise.all(
                response.data.filter(data => data.statusTransakcije == "PROVEDENA")
                    .map(async (transakcija) => {
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
            setProvedeneTransakcije(transakcije); // Postavi dobivene transakcije u stanje
        } catch (err) {
            console.error("Greška prilikom dohvaćanja provedenih transakcija:", err);
            setError("Došlo je do greške prilikom dohvaćanja provedenih transakcija.");
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            const [transakcije, ponude, ulaznice] = await Promise.all([
                fetchZaprimljeneTransakcije(),
                fetchPoslanePonude(),
                fetchOglasi(),
            ]);
            initializeOpenedSections(transakcije, ponude, ulaznice);
    
            // Dohvati provedene transakcije
            await fetchProvedeneTransakcije();
            setLoading(false);
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
        window.location.reload();
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
        window.location.reload();
    };

    const obrisiTransakciju = async (idTransakcije) => {
        if (!confirm(`Jeste li sigurni da želite obrisati ponudu ${idTransakcije}?`))
            return;

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
        if (!confirm(`Jeste li sigurni da želite obrisati oglas ${idOglas}?`))
            return;

        try {
            await axiosPrivate.delete(`oglasi/izbrisi/${idOglas}`);
            setOglasiBezPonuda((prev) =>
                prev.filter((u) => u.idOglasa !== idOglas)
            );
        } catch (err) {
            console.error("Greška prilikom brisanja oglasa:", err);
            setError("Došlo je do greške prilikom brisanja oglasa.");
        }
    };

    // Renderiranje
    return (
        <div style={{display: loading && "flex", marginTop: loading && "6rem", justifyContent: loading && "center", alignItems: loading && "center"}}>
            {loading ?
                <ScaleLoader
                height={100}
                radius={15}
                width={10}
                margin={4}
                color='#425DFF'
                />
            :
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
                                {openedSections[transakcija.idTransakcije] && (
                                    <Oglas
                                        oglasId={transakcija.idOglas}
                                        danaDo={transakcija.ulaznicaOglas.datumKoncerta}
                                        ulaznica1={transakcija.ulaznicaOglas}
                                        ulaznica2={transakcija.ulaznicaPonuda}
                                        statusPonude={transakcija.status}
                                        naPrihvati={() => prihvatiPonudu(transakcija.idTransakcije)}
                                        naOdbij={() => odbijPonudu(transakcija.idTransakcije)}
                                        naObrisi={() => {
                                            obrisiOglas(transakcija.idOglas);
                                        }}
                                        imePonuditelja={transakcija.korisnikPonuda.imeKorisnika.concat(" ", transakcija.korisnikPonuda.prezimeKorisnika)} 
                                        tipTransakcije="zaPrihvatiti"
                                        idNavigateOglasa={transakcija.ulaznicaPonuda.oglasiIds} //provjerit
                                    />
                                )}
                            </div>
                        ))
                    ) : (
                        <p>Nemate zaprimljenih ponuda.</p>
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
                                {openedSections[transakcija.idTransakcije] && (
                                    <Oglas
                                        idTransakcije={transakcija.idTransakcije}
                                        danaDo={transakcija.ulaznicaOglas.datumKoncerta}
                                        ulaznica1={transakcija.ulaznicaPonuda}
                                        ulaznica2={transakcija.ulaznicaOglas}
                                        statusPonude={transakcija.status}
                                        naObrisi={() => {
                                            obrisiTransakciju(transakcija.idTransakcije);
                                        }}
                                        imePonuditelja={transakcija.korisnikProdavac.imeKorisnika.concat(" ", transakcija.korisnikProdavac.prezimeKorisnika)} 
                                        tipTransakcije="poslanePonude"
                                        idNavigateOglasa={transakcija.ulaznicaOglas.oglasiIds} //provjerit
                                    />
                                )}
                            </div>
                        ))
                    ) : (
                        <p>Nemate poslanih ponuda.</p>
                    )
                )}
            </section>

            {/* Ulaznice koje nisu transakcije */}
            <section>
                <h3 onClick={() => toggleSection("ostaliOglasi")} style={{ color: "#425DFF", cursor: "pointer" }}>
                    {openedSections["ostaliOglasi"] ? "▼" : "▶"} Ostali Oglasi
                </h3>
                {openedSections["ostaliOglasi"] && (
                    oglasiBezPonuda.length > 0 ? (
                        oglasiBezPonuda.map((oglas) => (
                            <div key={oglas.idOglasa}>
                                {openedSections[oglas.idOglasa] && (
                                    <Oglas
                                        oglasId={oglas.idOglasa}
                                        danaDo={oglas.ulaznicaOglas.datumKoncerta}
                                        ulaznica1={oglas.ulaznicaOglas}
                                        ulaznica2={{ datumKoncerta: "N/A" }}
                                        naObrisi={() => {
                                            obrisiOglas(oglas.idOglasa); // Stara funkcija za trajno brisanje
                                        }}
                                        tipTransakcije="ostalo"
                                        // idNavigateOglasa={oglas.idOglasa} 
                                    />
                                )}
                            </div>
                        ))
                    ) : (
                        <p>Nemate ulaznica.</p>
                    )
                )}
            </section>

            <section>
                <h3 onClick={() => toggleSection("provedeneTransakcije")} style={{ color: "#308614", cursor: "pointer" }}>
                    {openedSections["provedeneTransakcije"] ? "▼" : "▶"} Provedene transakcije
                </h3>
                {openedSections["provedeneTransakcije"] && (
                    provedeneTransakcije.length > 0 ? (
                        provedeneTransakcije.map((transakcija) => (
                            <Oglas
                                key={transakcija.idTransakcije}
                                idTransakcije={transakcija.idTransakcije}
                                danaDo={transakcija.ulaznicaPonuda.datumKoncerta} // Prikaz datuma završetka
                                ulaznica1={transakcija.ulaznicaOglas}
                                ulaznica2={transakcija.ulaznicaPonuda}
                                tipTransakcije="provedeno"
                            />
                        ))
                    ) : (
                        <p>Nemate završenih transakcija.</p>
                    )
                )}
            </section>

            </div>}
        </div>
    );
}


