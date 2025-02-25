
/*
import React, { useState, useEffect } from "react";
import axiosPrivate from "./api/axiosPrivate"; 
import "./Obavijesti2.css";
import { FaTrashAlt, FaArrowRight } from "react-icons/fa";

export default function Obavijesti() {
    const [obavijesti, setObavijesti] = useState([]);

    const fetchAdditionalData = async ( oglasId) => {
        try {
            
            const[izvodaciResponse, ulaznicaInfo] = await Promise.all([
                axiosPrivate.get(`oglasi/${oglasId}/izvodaci`),
                axiosPrivate.get(`ulaznice/oglas-id/${oglasId}`)
            ]);
           
            
            const korisnikId = ulaznicaInfo.data.idKorisnika;
            const korisnikResponse = await axiosPrivate.get(`korisnici/${korisnikId}`);
            return {
                imeKorisnika: korisnikResponse.data.imeKorisnika,
                prezimeKorisnika: korisnikResponse.data.prezimeKorisnika,
                imeIzvodaca: izvodaciResponse.data[0].imeIzvodaca + " " + izvodaciResponse.data[0].prezimeIzvodaca ,
                datum: ulaznicaInfo.data.datumKoncerta, 
                lokacija: ulaznicaInfo.data.lokacijaKoncerta, 
                odabranaZona: ulaznicaInfo.data.odabranaZona, 
            };
        } catch (err) {
            console.log("Greška prilikom dohvaćanja dodatnih podataka", err);
            return {};
        }
    };

    const obrisiObavijest = async(idObavijesti) => {
        try {
            await axiosPrivate.get(`obavijesti/izbrisi/${idObavijesti}`);
            // Ažuriranje stanja nakon uspješnog brisanja
            setObavijesti((prevObavijesti) =>
                prevObavijesti.filter((obavijest) => obavijest.idObavijesti !== idObavijesti)
            );
        } catch (err) {
            console.error("Greška prilikom brisanja obavijesti:", err);
        }
    }

    useEffect(() => {
        const getObavijesti = async () => {
            try {
                const response = await axiosPrivate.get("preference/obavijesti");
                const enrichedObavijesti = await Promise.all(
                    response.data.map(async (item) => {
                        const additionalData = await fetchAdditionalData(item.oglasId);
                        return {
                            ...item,
                            ...additionalData,
                            tip: item.obavijestType
                        };
                    })
                );
                setObavijesti(enrichedObavijesti);
            } catch (err) {
                console.log("Greška prilikom dohvata obavijesti", err);
            }
        };

        getObavijesti();
    }, []);

    const getObavijestText = (obavijest) => {
        const { imeKorisnika, prezimeKorisnika, imeIzvodaca, datum, lokacija, odabranaZona } = obavijest;
        switch (obavijest.tip) {
            case "PRIHVATIO":
                return `${imeKorisnika} ${prezimeKorisnika} je prihvatio vašu ponudu ulaznice za ${imeIzvodaca}, ${datum} ${lokacija} ${odabranaZona}`;
            case "ODBIO":
                return `${imeKorisnika} ${prezimeKorisnika} je odbio vašu ponudu ulaznice za ${imeIzvodaca}, ${datum} ${lokacija} ${odabranaZona}`;
            case "PONUDIO":
                return `${imeKorisnika} ${prezimeKorisnika} je ponudio ulaznice na vašem oglasu za ${imeIzvodaca}, ${datum} ${lokacija} ${odabranaZona}`;
            case "OGLAS":
                return `${imeKorisnika} ${prezimeKorisnika} je objavio oglas za ${imeIzvodaca}, ${datum} ${lokacija} ${odabranaZona}`;
            default:
                return "Nepoznata obavijest";
        }
    };

    return (
        <div className="obavijesti-container">
            {obavijesti.map((obavijest) => (
                <div
                    key={obavijest.idObavijesti}
                    className="obavijest" >
                    <h5 className="obavijest-text">{getObavijestText(obavijest)}</h5>
                    <div className="actions">
                        <button className="obrisi" onClick={() => obrisiObavijest(obavijest.idObavijesti)}>
                            <FaTrashAlt /> Obriši
                        </button>
                        <button className="odvedi">
                            <FaArrowRight /> Odvedi me
                        </button>
                    </div>
                </div>
            ))}
        </div>
    );
}
*/

import React, { useState, useEffect } from "react";
import axiosPrivate from "./api/axiosPrivate";
import "./Obavijesti2.css";
import { FaTrashAlt, FaArrowRight } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';
import ScaleLoader from "react-spinners/ScaleLoader";

export default function Obavijesti() {
    const [obavijesti, setObavijesti] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const obrisiObavijest = async (idObavijesti) => {
        try {
            await axiosPrivate.get(`obavijesti/izbrisi/${idObavijesti}`);
            setObavijesti((prevObavijesti) =>
                prevObavijesti.filter((obavijest) => obavijest.idObavijesti !== idObavijesti)
            );
        } catch (err) {
            console.error("Greška prilikom brisanja obavijesti:", err);
        }
    };

    const handleOdvediMe = (obavijest) => {

        switch (obavijest.obavijestType) {
            case "PRIHVATIO":
                navigate(`/userOglasi#${obavijest.transakcijaIdObavijest}`);
                return;
            case "PONUDIO":
                navigate(`/userOglasi#${obavijest.oglasIdObavijest}`);
                return;
            case "OGLAS":
                navigate(`/#${obavijest.oglasIdObavijest}`);
                return;
        }
    }

    useEffect(() => {
        const getObavijesti = async () => {
            try {
                const response = await axiosPrivate.get("preference/obavijesti");
                const transakcije = await axiosPrivate.get("preference/transakcije/za-potvrditi");
                console.log("DATAAA:")
                console.log(response.data)
                const obs = response.data.filter(ob => ob.obavijestType == "PONUDIO").map(ob => {ob.oglasIdObavijest = transakcije.data?.find(tr => tr.idTransakcije == ob.transakcijaIdObavijest).idOglas; return ob;})
                setObavijesti([...obs, ...response.data.filter(obavijest => obavijest.obavijestType != "PONUDIO")]);
                setLoading(false);
            } catch (err) {
                console.log("Greška prilikom dohvata obavijesti", err);
            }
        };

        getObavijesti();
    }, []);

    const getObavijestText = (obavijest) => {
        const { autorOglasIme, autorOglasPrezime, izvodaci, ulaznicaPreporuka, ulaznicaPonuda, ulaznicaOglas,korisnikPonudaIme,korisnikPonudaPrezime,korisnikOglasIme,korisnikOglasPrezime } = obavijest;

        switch (obavijest.obavijestType) {
            case "PRIHVATIO":
                return `${korisnikOglasIme} ${korisnikOglasPrezime} je prihvatio vašu ponudu ulaznice za ${izvodaci[0].imeIzvodaca} ${izvodaci[0].prezimeIzvodaca}, ${ulaznicaPonuda.datumKoncerta} ${ulaznicaPonuda.lokacijaKoncerta} ${ulaznicaPonuda.odabranaZona}`;
            case "ODBIO":
                return `${korisnikOglasIme} ${korisnikOglasPrezime} je odbio vašu ponudu ulaznice za ${izvodaci[0].imeIzvodaca} ${izvodaci[0].prezimeIzvodaca}, ${ulaznicaPonuda.datumKoncerta} ${ulaznicaPonuda.lokacijaKoncerta} ${ulaznicaPonuda.odabranaZona}`;
            case "PONUDIO":
                return `${korisnikPonudaIme} ${korisnikPonudaPrezime} je ponudio ulaznicu na vašem oglasu za ${izvodaci[0].imeIzvodaca} ${izvodaci[0].prezimeIzvodaca}, ${ulaznicaOglas.datumKoncerta} ${ulaznicaOglas.lokacijaKoncerta} ${ulaznicaOglas.odabranaZona}`;
            case "OGLAS":
                return `${autorOglasIme} ${autorOglasPrezime} je objavio oglas za ${izvodaci[0].imeIzvodaca} ${izvodaci[0].prezimeIzvodaca}, ${ulaznicaPreporuka.datumKoncerta} ${ulaznicaPreporuka.lokacijaKoncerta} ${ulaznicaPreporuka.odabranaZona}`;
            default:
                return "Nepoznata obavijest";
        }
    };

    const getOdvediMe = (obavijest) => {
        switch (obavijest.obavijestType) {
            case "PRIHVATIO":
                return obavijest.transakcijaIdObavijest;
            case "ODBIO":
                return null;
            case "PONUDIO":
                return obavijest.oglasIdObavijest;
            case "OGLAS":
                return obavijest.oglasIdObavijest;
            default:
                return null;
        }
    }

    return (
        <div style={{display: loading && "flex", marginTop: loading && "6rem", justifyContent: "center", alignItems: "center"}}>
        {loading ?
            <ScaleLoader
            height={100}
            radius={15}
            width={10}
            margin={4}
            color='#425DFF'
            />
        : <div className="obavijesti-container">
            {obavijesti.map((obavijest) => (
                <div key={obavijest.idObavijesti} className="obavijest">
                    <h5 className="obavijest-text">{getObavijestText(obavijest)}</h5>
                    <div className="actions">
                        <button className="obrisi" onClick={() => obrisiObavijest(obavijest.idObavijesti)}>
                            <FaTrashAlt /> Obriši
                        </button>

                        {getOdvediMe(obavijest) &&
                            <button
                            className="odvedi"
                            onClick={() => {
                                handleOdvediMe(obavijest)
                                    //console.log(obavijest.oglasIdObavijest)
                            }}
                        >
                            <FaArrowRight /> Odvedi me
                        </button>}
                    </div>
                </div>
            ))}
        </div>}
        </div>
    );
}



