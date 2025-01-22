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
  const [jeProdaja, setJeProdaja] = useState(false)
  const [buttonStyle, setButtonStyle] = useState(["#ffb700", null, "3px solid #ffb700"])
  const [buttonHover, setButtonHover] = useState(["white", "##ffb700"])

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

  function handleSetProdaja() {
    setJeProdaja(!jeProdaja)
    if (!jeProdaja) {
      setButtonStyle(["#425dff", null, "3px solid #425dff"])
      setButtonHover(["white", "##425dff"])
    } else {
      setButtonStyle(["#ffb700", null, "3px solid #ffb700"])
      setButtonHover(["white", "#ffb700"])
    }
  }
  
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
                  <h1>Nemate dostupnih ulaznica</h1>
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
                  <div>
                    <h1>Kreirajte oglas</h1>
                    <div className="form-wrapper">
                      <div className="option-wrapper"><span>Ulaznica:</span></div>
                      <div className="select-wrapper">
                        <select
                          id="ulaznica"
                          value={selectedUlaznica ? JSON.stringify(selectedUlaznica) : ""}
                          onChange={(e) => setSelectedUlaznica(JSON.parse(e.target.value))}
                        >
                          {/* <option value="">-- Odaberite ulaznicu --</option> */}
                          {ulaznice.map((ulaznica) => (
                            <option key={ulaznica.sifraUlaznice} value={JSON.stringify(ulaznica)}>
                              {ulaznica.sifraUlaznice}
                            </option>
                          ))}
                        </select>
                      </div>
                      <div className="option-wrapper"><span>Tip oglasa:</span></div>
                      <div className="type-wrapper">
                        <span className={!jeProdaja ? "zamjena option" : "option"}>Zamjena</span>
                        <label className="switch">
                          <input type="checkbox" value={jeProdaja} onChange={(e) => (handleSetProdaja())}></input>
                          <span className="slider"></span>
                        </label>
                        <span className={jeProdaja ? "prodaja option" : "option"}>Prodaja</span>
                      </div>
                      <div className="button-wrapper">
                        <Button onClick={closeModal}
                          style={["red", null, "3px solid red"]} hover={["white", "red"]}>
                          Odustani
                        </Button>
                        <Button style={buttonStyle} hover={buttonHover}>
                          Kreiraj oglas
                        </Button>
                      </div>
                    </div>
                  </div>
                </>
            }

          </div>
      }

    </div>
  );
}

export default AddOglasModal;

