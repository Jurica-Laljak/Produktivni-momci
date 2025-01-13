import React, { useState,useEffect, useContext } from 'react';
import {Context} from "./App" 
import axiosPrivate from "./api/axiosPrivate";
import { Navigate } from 'react-router-dom';
import { FaSearch, FaUsers, FaUser } from 'react-icons/fa';
import "./Admin.css"
import UserReport from './UserReport';

export default function Admin( { userData } ) {

    const[sentRequest, setSentRequest] = useState(false);
    const[korisnici, setKorisnici] = useState([]);
    const[transakcije, setTransakcije] = useState([]);
    const[adminRole, setAdminRole] = useState();
    const[selectedValue, setSelectedValue] = useState("all-users");
    const[korisnikInput, setKorisnikInput] = useState("");

    function handleOnClick() {
        setSentRequest(true);
        if(selectedValue === "all-users") {
            axiosPrivate.get('korisnici')
            .then(async (response) => {
                var korisnici = response.data;
                console.log(korisnici);
                setKorisnici(korisnici);
            });
        }
        else if(selectedValue === "special-user") {
            axiosPrivate.get(`korisnici/g/${korisnikInput}`)
            .then(async (response) => {
                var korisnici = Array(Object(response.data));
                console.log(korisnici);
                setKorisnici(korisnici);
            });
        }
        axiosPrivate.get("transakcije")
        .then(async (response) => {
            var transakcije = response.data;
            console.log("Transakcije: ", transakcije);
            setTransakcije(transakcije);
        });
        axiosPrivate.get("role")
        .then(async (response) => {
            var roles = response.data;
            var adminRole = roles.find((role) => role.role == "ROLE_ADMIN");
            console.log("Role: ", adminRole);
            setAdminRole(adminRole);
        });
    };

    const handleOptionChange = changeEvent => {
        setSelectedValue(changeEvent.target.value);
    };

    const handleInputChange = changeEvent => {
        setKorisnikInput(changeEvent.target.value);
    };

    return (
        <div className='admin-content'>
            {!sentRequest &&
            <div className='admin-wrapper'>
                <div className='radio-el'>
                    <label>
                    <input type="radio" id="all-users" value="all-users" 
                    checked={selectedValue === "all-users"}
                    onChange={handleOptionChange}
                    />
                    Kreiraj izvještaj na temelju svih korisnika
                    <FaUsers/>
                    </label>
                </div>
                <div className='radio-el'>
                    <div>
                        <label>
                        <input type="radio" id="special-user" value="special-user" 
                        checked={selectedValue === "special-user"} 
                        onChange={handleOptionChange}
                        />
                        Kreiraj izvještaj na temelju odabranog korisnika
                        <FaUser/>
                        </label>
                    </div>
                    <div className='input-wrapper-id'>
                        <FaSearch id="search-icon" />
                        <input placeholder='GID korisnika'
                            value={korisnikInput}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>
                <div className='btn-wrapper'>
                    <button className='btn-submit-admin' label='Obavi pretragu'
                    onClick={handleOnClick}
                    >Obavi pretragu</button>
                </div>
            </div>}

            {sentRequest &&
            <div className='korisnici'>
                {korisnici.map((korisnik) => 
                <UserReport
                korisnik={korisnik}
                transakcije={transakcije}
                userData={userData}
                adminRole={adminRole}
                />
                )}
            </div>}

        </div>

    )
}