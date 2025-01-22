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
      console.log("problem s dodavanjem ulaznice: ", err);
    }
  };

  function handleKeyDown(event) {
    if (event.key === 'Enter') {
      handleAddTicket();
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
          {ulaznice.map((ulaznica, index) => (
            <Ulaznica key={index} {...ulaznica} />
          ))}
          <div className="add-ticket-container">
            <button className="add-ticket-btn" onClick={() => setShowModal(true)}>+ Dodaj ulaznicu</button>
            {showModal && (
              <div className="modal">
                <div className="modal-content">
                  <span className="close-btn" onClick={() => setShowModal(false)}>
                    &times;
                  </span>
                  <h2>Dodaj Ulaznicu</h2>
                  <input
                    type="text"
                    placeholder="Unesi šifru ulaznice"
                    value={ticketCode}
                    onChange={(e) => setTicketCode(e.target.value)}
                    onKeyDown={handleKeyDown}
                  />
                  <button onClick={handleAddTicket}>+ Dodaj ulaznicu</button>
                </div>
              </div>
            )}
          </div>
        </div>}
    </div>
  );
};


