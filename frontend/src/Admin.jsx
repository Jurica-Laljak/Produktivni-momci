import React, { useState,useEffect, useContext } from 'react';
import {Context} from "./App" 
import axiosPrivate from "./api/axiosPrivate";
import { Navigate } from 'react-router-dom';
import { FaSearch, FaUsers, FaUser } from 'react-icons/fa';
import "./Admin.css"
import UserReport from './UserReport';
import Autosuggest from 'react-autosuggest';
import ScaleLoader from "react-spinners/ScaleLoader";

export default function Admin( { userData } ) {

    const[sentRequest, setSentRequest] = useState(false);
    const[korisnici, setKorisnici] = useState([]);
    const[transakcije, setTransakcije] = useState([]);
    const[adminRole, setAdminRole] = useState();
    const[selectedValue, setSelectedValue] = useState("all-users");
    const[korisnikInput, setKorisnikInput] = useState('');
    const[suggestions, setSuggestions] = useState([]);
    const[korisnik, setKorisnik] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await axiosPrivate.get("korisnici");
            const response2 = await axiosPrivate.get("role");
            const response3 = await axiosPrivate.get("transakcije");

            setKorisnici(response.data);

            var roles = response2.data;
            var adminRole = roles.find((role) => role.role == "ROLE_ADMIN");
            setAdminRole(adminRole);

            setTransakcije(response3.data);
            setLoading(false);
          } catch (error) {
            console.error("Greška:", error);
          }
        };
        fetchData();
    }, []);

    const getSuggestions = inputValue => {
        const regex = new RegExp(inputValue.replace("(", "\\\(").replace(")", "\\\)").trim(), 'i');
        const inputLength = inputValue.length;

        return inputLength < 2 ? [] : korisnici.filter((korisnik) => regex.test(korisnik.imeKorisnika) || regex.test(korisnik.prezimeKorisnika)
        || regex.test(korisnik.idKorisnika) || regex.test(`${korisnik.imeKorisnika} ${korisnik.prezimeKorisnika} (ID: ${korisnik.idKorisnika})`));
    };

    const getSuggestionValue = korisnik => (`${korisnik.imeKorisnika} ${korisnik.prezimeKorisnika} (ID: ${korisnik.idKorisnika})`);

    const renderSuggestion = korisnik => (
        <div>
            {korisnik.imeKorisnika} {korisnik.prezimeKorisnika} (ID: {korisnik.idKorisnika})
        </div>
    );

    const onSuggestionsFetchRequested = ({ value }) => {
        setSuggestions(getSuggestions(value));
    };

    const onSuggestionsClearRequested = () => {
        setSuggestions([]);
    };

    const onSuggestionsChange = (event, { newValue }) => {
        setKorisnikInput(newValue);
    };

    const onSuggestionSelected = (event, { suggestion, suggestionValue, suggestionIndex, sectionIndex, method }) => {
        setKorisnik(Array(Object(suggestion)));
    }

    const inputProps = {
        placeholder: 'Korisnik...',
        value: korisnikInput,
        onChange: onSuggestionsChange
    };

    function handleOnClick() {
        console.log(korisnik);
        if(selectedValue === "special-user" && korisnik.length == 0) {
            alert("Niste odabrali korisnika!");
            return;
        }
        else if(selectedValue === "special-user") {
            setKorisnici(korisnik);
        }
        setSentRequest(true);
    };

    const handleOptionChange = changeEvent => {
        setSelectedValue(changeEvent.target.value);
    };

    return (
        <div className='admin-content'>
            {!sentRequest ?
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
                        <Autosuggest
                            suggestions={suggestions}
                            onSuggestionsFetchRequested={onSuggestionsFetchRequested}
                            onSuggestionsClearRequested={onSuggestionsClearRequested}
                            getSuggestionValue={getSuggestionValue}
                            renderSuggestion={renderSuggestion}
                            inputProps={inputProps}
                            onSuggestionSelected={onSuggestionSelected}
                        />
                    </div>
                </div>
                <div className='btn-wrapper'>
                    <button className='btn-submit-admin' label='Obavi pretragu'
                    onClick={handleOnClick}
                    >Obavi pretragu</button>
                </div>
            </div>
            :
            <div style={{display: loading && "flex", marginTop: loading && "6rem", justifyContent: "center"}}>
            {loading ?
              <ScaleLoader
              height={100}
              radius={15}
              width={10}
              margin={4}
              color='#FFB700'
              />
            : 
            <div className='korisnici'>
                {korisnici.sort((a, b) => a.idKorisnika > b.idKorisnika).map((korisnik) => 
                <UserReport
                korisnik={korisnik}
                transakcije={transakcije}
                userData={userData}
                adminRole={adminRole}
                loading={loading}
                />
                )}
            </div>}
            </div>}
        </div>

    )
}