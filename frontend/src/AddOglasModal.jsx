import React, { useContext, useEffect, useState } from "react";
import { Context } from "./App";
import "./AddOglasModal.css";
import axiosPrivate from "./api/axiosPrivate";
import LinkWrapper from "./common/LinkWrapper";
import { FaHome, FaTicketAlt } from "react-icons/fa";
import Button from "./common/Button";
import ScaleLoader from "react-spinners/ScaleLoader";

function AddOglasModal() {
  const { closeModal } = useContext(Context);
  const [loading, setLoading] = useState(false)
  const [ulaznice, setUlaznice] = useState([]);
  const [selectedUlaznica, setSelectedUlaznica] = useState(null);

  // Dohvati ulaznice na mountu
  useEffect(() => {
    const fetchUlaznice = async () => {
      try {
        setLoading(true)
        const response = await axiosPrivate.get("preference/korisnici/ulaznice/without-oglas");
        setUlaznice(response.data);
        console.log("Ulaznice korisnika:", response.data);
        setLoading(false)
      } catch (error) {
        console.error("Greška prilikom dohvaćanja ulaznica:", error);
      }
    };

    fetchUlaznice();
  }, []);

  // Funkcija za podnošenje oglasa
  const handleSubmit = async () => {
    if (!selectedUlaznica) {
      alert("Molimo odaberite ulaznicu!");
      return;
    }

    const id = Number(localStorage.getItem("ID"));
    const reqBody = {
      "ulaznicaId": selectedUlaznica.idUlaznice,
    };

    console.log("Request Body:", reqBody);

    try {
      const response = await axiosPrivate.post("preference/oglasi/kreiraj", reqBody);
      // console.log("Oglas uspješno kreiran:", response.data);
      alert(`Oglas je uspješno kreiran za ulaznicu: ${selectedUlaznica.sifraUlaznice}`);
      closeModal(); // Zatvaranje modala nakon kreiranja oglasa
    } catch (error) {
      console.error("Greška prilikom kreiranja oglasa:", error);
      alert("Došlo je do greške prilikom kreiranja oglasa.");
    }
  };

  return (
    <div className="modal">
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
            {
              ulaznice == 0 ?
                <div>
                  <h2>Nemate dostupnih ulaznica</h2>
                  <LinkWrapper to={"/userUlaznice"} onClick={closeModal} state={{ sentFrom: "addListing" }}>
                    <Button icon={<FaTicketAlt />}
                      style={["#4b66fc", null]} hover={["white", "#4b66fc"]}>
                      Dodaj ulaznicu
                    </Button>
                  </LinkWrapper>
                  <LinkWrapper to={"/"} onClick={closeModal}>
                    <Button icon={<FaHome></FaHome>}
                      style={["black", null]} hover={["white", "black"]}>
                      Početna stranica
                    </Button>
                  </LinkWrapper>
                </div>
                :
                <>
                  <h2>Kreiraj oglas</h2>
                  <form>
                    {/* Dropdown za odabir ulaznice */}
                    <label htmlFor="ulaznica">Odaberite ulaznicu:</label>
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

                    {/* Gumb za kreiranje oglasa */}
                    <button
                      type="button"
                      onClick={handleSubmit}
                      className="btn btn-create-ad"
                    >
                      Kreiraj oglas
                    </button>

                    {/* Gumb za zatvaranje modala */}
                    <button type="button" className="modal-close-btn" onClick={closeModal}>
                      Zatvori
                    </button>
                  </form>
                </>
            }

          </div>
      }

    </div>
  );
}

export default AddOglasModal;

