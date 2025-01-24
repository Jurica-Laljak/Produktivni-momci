import React, { useContext, useEffect, useState } from "react";
import { Context } from "./App";
import "./AddOglasModal.css";
import axiosPrivate from "./api/axiosPrivate";
import Button from "./common/Button";
import ScaleLoader from "react-spinners/ScaleLoader";

function ExchangeModal() {
  const { closeRazmijeniModal, razmijeniModalData } = useContext(Context); // Dohvati podatke proslijeđene iz modala
  const [ulaznice, setUlaznice] = useState([]);
  const [selectedUlaznica, setSelectedUlaznica] = useState(null);
  const [loading, setLoading] = useState(false)

  // Dohvati ulaznice na mountu
  useEffect(() => {
    const fetchUlaznice = async () => {
      try {
        setLoading(true)
        const response = await axiosPrivate.get("preference/korisnici/ulaznice/without-oglas");
        setUlaznice(response.data);
        console.log("Ulaznice korisnika:", response.data);
        if (response.data.length > 0) {
          setSelectedUlaznica(response.data[0])
        }
        setLoading(false)
      } catch (error) {
        setLoading(false)
        console.error("Greška prilikom dohvaćanja ulaznica:", error);
      }
    }
    fetchUlaznice()
  }, []);

  // Funkcija za podnošenje transakcije
  const handleSubmit = async () => {
    if (!selectedUlaznica) {
      alert("Molimo odaberite ulaznicu!");
      return;
    }

    const id = Number(localStorage.getItem("ID"));

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
      {
        loading ?
          <div className="modal-content">
            <ScaleLoader id="loader"
              height={100}
              radius={15}
              width={10}
              margin={4}
              color='black'
            />
          </div>
          :
          <div className="modal-content">
            {/* <h2>Zamijeni ulaznicu</h2>
          <form>
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
            <button
              type="button"
              onClick={handleSubmit}
              className="btn btn-create-ad"
            >
              Ponudi ulaznicu
            </button>

            <button type="button" className="modal-close-btn" onClick={closeRazmijeniModal}>
              Zatvori
            </button>
          </form> */}
            <div>
              <h1>Zamjeni ulaznicu</h1>
              <div className="form-wrapper">
                <div className="option-wrapper"><span>Moja ponuda:</span></div>
                <div className="select-wrapper">
                  <select
                    id="ulaznica"
                    value={selectedUlaznica ? JSON.stringify(selectedUlaznica) : ""}
                    onChange={(e) => setSelectedUlaznica(JSON.parse(e.target.value))}>
                    {ulaznice.map((ulaznica) => (
                      <option key={ulaznica.sifraUlaznice} value={JSON.stringify(ulaznica)}>
                        {ulaznica.sifraUlaznice}
                      </option>
                    ))}
                  </select>
                </div>
                {/* <div className="option-wrapper"><span>Tip oglasa:</span></div>
            <div className="type-wrapper">
              <span className={!jeProdaja ? "zamjena option" : "option"}>Zamjena</span>
              <label className="switch">
                <input type="checkbox" value={jeProdaja} onChange={(e) => (handleSetProdaja())}></input>
                <span className="slider"></span>
              </label>
              <span className={jeProdaja ? "prodaja option" : "option"}>Prodaja</span>
            </div> */}
                <div className="button-wrapper">
                  <Button onClick={closeRazmijeniModal}
                    style={["red", null, "3px solid red"]} hover={["white", "red"]}>
                    Odustani
                  </Button>
                  <Button onClick={handleSubmit}
                    style={["#425dff", null, "3px solid #425dff"]} hover={["white", "#425dff"]}>
                    Ponudi
                  </Button>
                </div>
              </div>
            </div>
          </div>
      }

    </div>
  );
}

export default ExchangeModal;
