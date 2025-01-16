import axios from "axios";
import React, { useState, useEffect } from "react";
import axiosPrivate from "./api/axiosPrivate";
import Ulaznica from "./Ulaznica";
import Oglas from "./Oglas";
import { useNavigate } from 'react-router-dom';

export default function UserOglasi() {
    const [ulaznicaId, setUlaznicaId] = useState("");
    const [ulaznicaData, setUlaznicaData] = useState([]);
    const [error, setError] = useState(null);

    // ✅ Funkcija za dohvaćanje svih ulaznica korisnika
    const fetchUlaznice = async () => {
        try {
            const ulazniceResponse = await axiosPrivate.get("preference/korisnici/ulaznice");
            setUlaznicaData(ulazniceResponse.data);
            console.log("Podaci o ulaznicama:", ulazniceResponse.data);
        } catch (err) {
            console.error("Greška prilikom dohvaćanja ulaznica:", err);
            setError("Došlo je do greške prilikom dohvaćanja ulaznica.");
        }
    };

    // ✅ Pozivanje API-ja prilikom inicijalnog učitavanja komponente
    useEffect(() => {
        fetchUlaznice();
    }, []);

  /*  // ✅ Funkcija za kreiranje oglasa
    const kreirajOglas = async () => {
        try {
            if (!ulaznicaId) {
                alert("Unesite ID ulaznice!");
                return;
            }

            // Korak 1: Kreiraj oglas
            const kreirajOglasResponse = await axiosPrivate.post(
                "preference/oglasi/kreiraj",
                { ulaznicaId },
            );

            console.log("Oglas kreiran:", kreirajOglasResponse.data);

            // ✅ Korak 2: Ponovno dohvaćanje ulaznica nakon kreiranja oglasa
            fetchUlaznice();
        } catch (err) {
            console.error("Greška:", err);
            setError("Došlo je do greške prilikom obrade zahtjeva.");
        }
    }; */

    return (
        <div> 
            <h1>U IZRADI (!)</h1>
        {  /*  <input
                type="text"
                value={ulaznicaId}
                onChange={(e) => setUlaznicaId(e.target.value)}
                placeholder="Unesite ID ulaznice"
            />
            <button onClick={kreirajOglas}>Kreiraj Oglas</button>

            {error && <p style={{ color: "red" }}>{error}</p>} */}

            {ulaznicaData && ulaznicaData.length > 0 && (
    <div>
        {ulaznicaData.map((ulaznica, index) => (
            <Oglas
                key={index}
                oglasId={ulaznica.idUlaznice}
                ulaznica1={ulaznica}
                ulaznica2={{
                    datumKoncerta: "Placeholder datum",
                    lokacijaKoncerta: "Placeholder lokacija",
                    odabranaZona: "Placeholder zona",
                    sifraUlaznice: "Placeholder šifra",
                    urlSlika: "https://via.placeholder.com/150",
                    vrstaUlaznice: "Placeholder vrsta",
                    urlInfo: "#",
                }}
                statusPonude={ulaznica.status}
                naUredi={() => console.log(`Uredi oglas ${ulaznica.idUlaznice}`)}
                naObrisi={() => console.log(`Obriši oglas ${ulaznica.idUlaznice}`)}
                naPogledaj={() => console.log(`Pogledaj oglas ${ulaznica.idUlaznice}`)}
            />
        ))}
    </div>
)}

        </div>
    );
}
