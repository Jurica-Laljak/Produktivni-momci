import React, { useContext,useEffect,useState } from "react";
import { Context } from "./App";
import "./AddOglasModal.css"; 
import axiosPrivate from "./api/axiosPrivate";

function ExchangeModal() {
    const { closeRazmijeniModal, razmijeniModalData } = useContext(Context); // Dohvati podatke proslijeđene iz modala
    const [ulaznice, setUlaznice] = useState([]);
    const [selectedUlaznica, setSelectedUlaznica] = useState(null);
  
    // Dohvati ulaznice na mountu
    useEffect(() => {
      setUlaznice(razmijeniModalData.ulaznice);
    }, []);
  
    // Funkcija za podnošenje transakcije
    const handleSubmit = async () => {
      if (!selectedUlaznica) {
        alert("Molimo odaberite ulaznicu!");
        return;
      }
  
      const id = Number(localStorage.getItem("ID"));

      console.log(razmijeniModalData);

      const reqBody = {
        "ulaznicaPonudaId": selectedUlaznica.idUlaznice,
        "ulaznicaOglasId": razmijeniModalData.idUlaznice,
        "oglasId": razmijeniModalData.idOglasa
      };
      
  
      console.log("Request Body:", reqBody);
  
      try {
        const response = await axiosPrivate.post("transakcije/kreiraj", reqBody);
       // console.log("Oglas uspješno kreiran:", response.data);
        alert(`Uspješno ponuđena ulaznica: ${selectedUlaznica.sifraUlaznice}`); //dino: ${selectedUlaznica.sifraUlaznice} // X
        closeRazmijeniModal(); // Zatvaranje modala nakon kreiranja oglasa
      } catch (error) {
        console.error("Greška prilikom kreiranja oglasa:", error);
        alert("Došlo je do greške prilikom kreiranja oglasa.");
      }
    };
  
    return (
      <div className="exchange-modal">
        <div className="modal-content">
          <h2>Kreiraj transakciju</h2>
          <form>
            {/* Dropdown za odabir ulaznice */}
            <label htmlFor="ulaznica">Odaberite ulaznicu koju želite ponuditi:</label>
            <select
              id="ulaznica"
              value={selectedUlaznica ? JSON.stringify(selectedUlaznica) : ""}
              onChange={(e) => setSelectedUlaznica(JSON.parse(e.target.value))}
            >
               <option value="">-- Odaberite ulaznicu --</option>
              {ulaznice.map((ulaznica) => (
                <option key={ulaznica.sifraUlaznice} value={JSON.stringify(ulaznica)}>
                  {ulaznica.sifraUlaznice} 
                </option>
              ))}
            </select>
  
            {/* Gumb za kreiranje transakcije */}
            <button
              type="button"
              onClick={handleSubmit}
              className="btn btn-create-ad"
            >
              Ponudi ulaznicu
            </button>
  
            {/* Gumb za zatvaranje modala */}
            <button type="button" className="modal-close-btn" onClick={closeRazmijeniModal}>
              Zatvori
            </button>
          </form>
        </div>
      </div>
    );
  }
  
  export default ExchangeModal;
