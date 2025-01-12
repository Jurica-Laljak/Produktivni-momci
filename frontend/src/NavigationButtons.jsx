import React from "react";
import { Link, useLocation } from "react-router-dom";
import "./NavigationButtons.css"; 
import { FaTicketAlt } from "react-icons/fa";  
import { FaUser } from "react-icons/fa";       
import { FaArchive } from "react-icons/fa";

export default function  NavigationButtons (){
    
    const location = useLocation();

  return (
    <div className="navigation-buttons">
      <Link to="/userUlaznice"  className={`btn btn-ticket ${location.pathname === '/userUlaznice' ? 'active' : ''}`}>
        <FaTicketAlt /> 
        <div>Centar za ulaznice</div>
      </Link>
      <Link to="/user" className={`btn btn-user ${location.pathname === '/user' ? 'active' : ''}`}>
        <FaUser />
        <div>Centar za korisničke podatke</div>
      </Link>
      <Link to="/userOglasi" className={`btn btn-ads ${location.pathname === '/userOglasi' ? 'active' : ''}`}>
         <FaArchive/> 
         <div>Centar za transakcije</div>
      </Link>
    </div>
  );
};

