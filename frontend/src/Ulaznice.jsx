import React, {useState, useEffect} from "react";
import Ulaznica from "./Ulaznica";
import axios from 'axios';
import axiosPrivate from "./api/axiosPrivate";
import './Ulaznice.css';

export default function Ulaznice() {
    const [ulaznice, setUlaznice] = useState([]);
    const [showModal, setShowModal] = useState(false); 
    const [ticketCode, setTicketCode] = useState(""); 
  

  const getUlaznice = async () => {
    try{
    const response = await axiosPrivate.get('preference/korisnici/ulaznice')
    setUlaznice(response.data);   
    console.log(response.data); 
    }
    catch(err){
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

    useEffect(() => {
        getUlaznice();
    },[]);

   

  return (
    <div>
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
              />
              <button onClick={handleAddTicket}>Dodaj</button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};


