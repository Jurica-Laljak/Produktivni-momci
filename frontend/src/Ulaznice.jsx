import React, { useState, useEffect } from "react";
import Ulaznica from "./Ulaznica";
import axios from 'axios';
import axiosPrivate from "./api/axiosPrivate";
import './Ulaznice.css';
import ScaleLoader from "react-spinners/ScaleLoader";
import Button from "./common/Button";

export default function Ulaznice() {

  const [ulaznice, setUlaznice] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [ticketCode, setTicketCode] = useState("");
  const [loading, setLoading] = useState(true);
  const [errorDialogue, setErrorDialogue] = useState("")

  const getUlaznice = async () => {
    try {
      const response = await axiosPrivate.get('preference/korisnici/ulaznice')
      setUlaznice(response.data);
      setLoading(false);
      console.log(response.data);
    }
    catch (err) {
      console.log("problem s dohvatom ulaznica korisnika: ", err);
    }
  }

  const handleAddTicket = async () => {
    try {
      await axiosPrivate.post("preference/ulaznice/preuzmi", { sifraUlaznice: ticketCode });
      setTicketCode("");
      setShowModal(false);
      getUlaznice();
    } catch (err) {
      setTicketCode("")
      setErrorDialogue("Ulaznica nije dostupna")
    }
  };

  function handleKeyDown(event) {
    if (event.key === 'Enter') {
      handleAddTicket();
    }
    if (errorDialogue.length > 0) {
      setErrorDialogue("")
    }
  }

  useEffect(() => {
    getUlaznice();
  }, []);

  return (
    <div style={{ display: loading && "flex", marginTop: loading && "6rem", justifyContent: "center", alignItems: "center" }}>
      {loading ?
        <ScaleLoader
          height={100}
          radius={15}
          width={10}
          margin={4}
          color='#425DFF'
        />
        : <div>
          <div className="dodaj-ulaznicu-wrapper">
            <button className="dodaj-ulaznicu" onClick={() => setShowModal(true)}>+ Dodaj ulaznicu</button>
          </div>
          {ulaznice.map((ulaznica, index) => (
            <Ulaznica key={index} {...ulaznica} />
          ))}
          <div className="add-ticket-container">
            {showModal && (
              <div className="modal">
                <div className="modal-content">
                  <span className="close-btn" onClick={() => setShowModal(false)}>
                    &times;
                  </span>
                  <h2>Dodaj Ulaznicu</h2>
                  <div className="input-wrapper" style={{
                    "display": "grid",
                    "grid-template-columns": "auto auto",
                    "width": "100%",
                    "margin-block": "2vh",
                    "border": errorDialogue.length > 0 ? "2px solid red" : null
                  }}>
                    <input
                      type="text"
                      placeholder="Šifra ulaznice..."
                      value={ticketCode}
                      onChange={(e) => setTicketCode(e.target.value)}
                      onKeyDown={handleKeyDown}
                    />
                    <div className="error-dialogue" style={{ "color": "red" }}>{errorDialogue}</div>
                  </div>
                  <button onClick={handleAddTicket}>+ Dodaj ulaznicu</button>
                </div>
              </div>
            )}
          </div>
        </div>}
    </div>
  );
};


