import React, { useState,useEffect, useContext } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import axiosPrivate from "./api/axiosPrivate";
import "./User2.css";
import { FiSettings } from "react-icons/fi";
import {Context} from "./App"


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
      
      const [notificationsOn,setNotificationsOn ] = useState(true); 

      const {userName, setUserName} = useContext(Context);
  
      const navigate = useNavigate()

      // dohvati ime prezime i broj telefona korsinika i podatak da li ima ukljucene obavijesti
      useEffect(() => {
        const token = localStorage.getItem('token');
        const notifications = JSON.parse(localStorage.getItem("obavijesti"))
          if(notifications!== null){
            setNotificationsOn(notifications)
            console.log("razlicito null")
            console.log(notifications)
          }
         
          try {
            const googleID = JSON.parse(atob(token.split('.')[1])); // Decode JWT token 
          
  
            const getUserData = async () => {
              try{
                  const response = await  axiosPrivate.get(`korisnici/g/${googleID.sub}`)
                  const userData = response.data;
                  const ime = userData.imeKorisnika || "";
                  const prezime = userData.prezimeKorisnika || "";
                  const broj = userData.brMobKorisnika  || "";
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
         // Provjera formata broja mobitela
         console.log("Save")
         console.log(formData[field])
         if (field === "brMobKorisnika") {
          const isValidNumber = /^09\d{7}$/.test(formData[field]);
          if (!isValidNumber) {
          alert("Unesite ispravan broj mobitela u formatu '0911234567'.");
          return; // Prekini ako broj nije ispravan
            }
  }
       
        const updatedField = { [field]: formData[field] };
    
        try {
          const response = await axiosPrivate.patch("preference/korisnici/update-info", updatedField);
    
          if (response.status == 200) {
            // Uspješna promjena
            if(field !== "brMobKorisnika"){
            const token = localStorage.getItem('token');
            const googleID = JSON.parse(atob(token.split('.')[1]));
            
            const response2 = await axiosPrivate.get(`korisnici/g/${googleID.sub}`);
            const userData = response2.data;
            const imePrezime = userData.imeKorisnika + ' ' + userData.prezimeKorisnika;
            console.log("ciaoooooooooooooooooooo")
            setUserName(imePrezime);
            } 

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
    
      const handleCheckBoxChange = async (e) => {
          const isChecked = e.target.checked;

          try{
            const response = await axiosPrivate.patch("preference/korisnici/update-info", 
              {prikazujObavijesti:isChecked});

              if(response.status == 200){
                setNotificationsOn(isChecked);
                localStorage.setItem("obavijesti", JSON.stringify(isChecked))
                
              }

          } catch(err){
            console.log("greska prilikom updatea korisinikovih preferenca za obavijesti ", err)
          }
      }

      const handleDeleteAccount = async () => {
        try {
          const response = await axiosPrivate.delete("preference/korisnici/izbrisi");
          console.log('Delete successful:', response.data);
         navigate("/")
        } catch (error) {
          console.error('Error deleting data:', error);
          
        }
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
                      value={formData[field] || ""}
                      onChange={handleInputChange}
                    />
                  ) : (
                    <input
                      type="text"
                      id={field}
                      className="form-control"
                      value={formData[field] || ""}
                      readOnly
                    />
                  )}
                 <button
                  className={`btn btn-outline-${
                 isEditing[field]
                ? isChanged[field]
               ? "success"
             : "secondary" 
             : "primary"
                 }`}
                        onClick={() =>
                      isEditing[field]
                   ? isChanged[field]
                  ? handleSave(field)
                 : alert("Nema promjena za spremanje.") 
                  : handleEditToggle(field)
               }
             disabled={isEditing[field] && !isChanged[field]} 
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
                <input type="checkbox" id="allow-notifications"  checked={notificationsOn} onChange={handleCheckBoxChange}/>
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


