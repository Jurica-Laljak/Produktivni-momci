import React, { useState,useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import axiosPrivate from "./api/axiosPrivate";
import "./User2.css";
import { FiSettings } from "react-icons/fi";

/*
export default function User(){

    const [formData, setFormData] = useState({
        firsName:"",
        lastName:"",
        phoneNumber:""
    })


    const navigate = useNavigate()

    const handleEditPreferences = () => {
        navigate("/ChooseGenres")
    }

     // dohvati ime prezime i broj telefona korsinika
     useEffect(() => {
        const token = localStorage.getItem('token');
        
         
          try {
            const googleID = JSON.parse(atob(token.split('.')[1])); // Decode JWT token 
          
  
            const getUserData = async () => {
              try{
                  const response = await  axiosPrivate.get(`korisnici/g/${googleID.sub}`)
                  const userData = response.data;
                  const ime = userData.imeKorisnika;
                  const prezime = userData.prezimeKorisnika;
                  const broj = userData.brMobKorisnika;
                    const user = {
                        firstName: ime,
                        lastName: prezime,
                        phoneNumber: broj
                    }
                    
                    setFormData(user);

                }
              catch(err){
                  console.log("Doslo je do greske", err);
              }
            } 
             
             getUserData();
  
           
            
          } catch (error) {
            console.error('Error decoding token:', error);
          }
        
      }, []);

    return(
        <div className="user-settings-container">
      <div className="settings-row">
        <div className="section">
          <h3>Osobni podaci</h3>
          <div className="form-group">
            <label htmlFor="first-name">Ime</label>
            <input
              type="text"
              id="first-name"
              className="form-control"
              value={formData.firstName}
              readOnly
            />
            <button className="btn btn-outline-primary">Uredi</button>
          </div>
          <div className="form-group">
            <label htmlFor="last-name">Prezime</label>
            <input
              type="text"
              id="last-name"
              className="form-control"
              value={formData.lastName}
              readOnly
            />
            <button className="btn btn-outline-primary">Uredi</button>
          </div>
          <div className="form-group">
            <label htmlFor="phone-number">Broj telefona</label>
            <input
              type="text"
              id="phone-number"
              className="form-control"
              value={formData.phoneNumber}
              readOnly
            />
            <button className="btn btn-outline-primary">Uredi</button>
          </div>
        </div>

        <div className="section">
          <h3>Preferencije</h3>
          <button
            className="btn btn-outline-primary preference-btn"
            onClick={handleEditPreferences}
          >
            <FiSettings className="icon" /> Uredi preferencije
          </button>
        </div>
      </div>

      <div className="section">
        <h3>Obavijesti</h3>
        <div className="checkbox-group">
          <div>
            <input type="checkbox" id="allow-notifications" checked />
            <label htmlFor="allow-notifications">
              Dozvoli obavijesti unutar aplikacije
            </label>
          </div>
          
        </div>
      </div>

      <button className="btn btn-danger delete-account-btn">
        Obriši korisnički račun
      </button>
    </div>
        
    )
}   */

    export default function User(){

      const [formData, setFormData] = useState({
          imeKorisnika:"",
          prezimeKorisnika:"",
          brMobKorisnika:"091"
      })
  
      const [isEditing, setIsEditing] = useState({
        imeKorisnika: false,
        prezimeKorisnika: false,
        brMobKorisnika: false,
      });

      const [isChanged, setIsChanged] = useState({
        imeKorisnika: false,
        prezimeKorisnika: false,
        brMobKorisnika: false,
      });
      

  
      const navigate = useNavigate()

      // dohvati ime prezime i broj telefona korsinika
      useEffect(() => {
        const token = localStorage.getItem('token');
        
         
          try {
            const googleID = JSON.parse(atob(token.split('.')[1])); // Decode JWT token 
          
  
            const getUserData = async () => {
              try{
                  const response = await  axiosPrivate.get(`korisnici/g/${googleID.sub}`)
                  const userData = response.data;
                  const ime = userData.imeKorisnika;
                  const prezime = userData.prezimeKorisnika;
                  const broj = userData.brMobKorisnika;
                    const user = {
                        imeKorisnika: ime,
                        prezimeKorisnika: prezime,
                        brMobKorisnika: broj
                    }
                    
                    setFormData(user);

                }
              catch(err){
                  console.log("Doslo je do greske", err);
              }
            } 
             
             getUserData();
  
           
            
          } catch (error) {
            console.error('Error decoding token:', error);
          }
        
      }, []);

  
      const handleEditPreferences = () => {
          navigate("/ChooseGenres")
      }


      const handleEditToggle = (field) => {
        setIsEditing((prev) => ({ ...prev, [field]: !prev[field] }));
      };
    
      const handleInputChange = (e) => {
        const { id, value } = e.target;
        setFormData((prev) => ({ ...prev, [id]: value }));

        setIsChanged((prev) => ({
          ...prev,
          [id]: value !== formData[id],
        }));
      };
    
      const handleSave = async (field) => {
       
        const updatedField = { [field]: formData[field] };
    
        try {
          const response = await axiosPrivate.patch("preference/korisnici/update-info", updatedField);
    
          if (response.status == 200) {
            // Uspješna promjena
            alert(`${field} je uspješno izmijenjen.`);
            setIsEditing((prev) => ({ ...prev, [field]: false }));
            setIsChanged((prev) => ({ ...prev, [field]: false }));
          } else {
            // Greška prilikom spremanja
            alert("Došlo je do greške prilikom spremanja. Pokušajte ponovno.");
          }
        } catch (error) {
          console.error("Greška prilikom ažuriranja:", error);
          alert("Došlo je do greške prilikom ažuriranja.");
        }
      };
    
      const handleCheckBoxChange = async () => {
        console.log("a")

      }

      const handleDeleteAccount = async () => {
        console.log("b")
    }
       
      return (
        <div className="user-settings-container">
          <div className="settings-row">
            <div className="section">
              <h3>Osobni podaci</h3>
              {["imeKorisnika", "prezimeKorisnika", "brMobKorisnika"].map((field, idx) => (
                <div key={idx} className="form-group">
                  <label htmlFor={field}>
                    {field === "imeKorisnika"
                      ? "Ime"
                      : field === "prezimeKorisnika"
                      ? "Prezime"
                      : "Broj telefona"}
                  </label>
                  {isEditing[field] ? (
                    <input
                      type="text"
                      id={field}
                      className="form-control"
                      value={formData[field]}
                      onChange={handleInputChange}
                    />
                  ) : (
                    <input
                      type="text"
                      id={field}
                      className="form-control"
                      value={formData[field]}
                      readOnly
                    />
                  )}
                 <button
                  className={`btn btn-outline-${
                 isEditing[field]
                ? isChanged[field]
               ? "success"
             : "secondary" // Onemogućen izgled ako nema promjene
             : "primary"
                 }`}
                        onClick={() =>
                      isEditing[field]
                   ? isChanged[field]
                  ? handleSave(field)
                 : alert("Nema promjena za spremanje.") // Može biti suptilnije, npr. gumb onemogućen
                  : handleEditToggle(field)
               }
             disabled={isEditing[field] && !isChanged[field]} // Onemogući gumb ako nema promjene
>
           {isEditing[field] ? "Spremi" : "Uredi"}
          </button>

                </div>
              ))}
            </div>
    
            <div className="section">
              <h3>Preferencije</h3>
              <button
                className="btn btn-outline-primary preference-btn"
                onClick={() => navigate("/ChooseGenres")}
              >
                <FiSettings className="icon" /> Uredi preferencije
              </button>
            </div>
          </div>
    
          <div className="section">
            <h3>Obavijesti</h3>
            <div className="checkbox-group">
              <div>
                <input type="checkbox" id="allow-notifications"  defaultChecked={true} onChange={handleCheckBoxChange}/>
                <label htmlFor="allow-notifications">
                  Dozvoli obavijesti unutar aplikacije
                </label>
              </div>
              
            </div>
          </div>
    
          <button className="btn btn-danger delete-account-btn" onClick={handleDeleteAccount}>
            Obriši korisnički račun
          </button>
        </div>
      );
    };
