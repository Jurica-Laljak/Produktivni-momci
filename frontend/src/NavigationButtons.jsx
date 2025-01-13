import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import "./NavigationButtons.css"; 
import { FaTicketAlt } from "react-icons/fa";  
import { FaUser } from "react-icons/fa";       
import { FaArchive } from "react-icons/fa";
import { FaLock } from "react-icons/fa";

export default function  NavigationButtons (){
    
  const location = useLocation();

  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const googleID = JSON.parse(atob(token.split('.')[1]));

        if(googleID.roles.includes("ROLE_ADMIN"))
          setIsAdmin(true);
      }
      catch (error) {
        console.error('Error decoding token:', error);
      }
    }
  });

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
      {isAdmin &&
      <Link to="/admin" className={`btn btn-admin ${location.pathname === '/admin' ? 'active' : ''}`}>
         <FaLock/> 
         <div>Centar za administratora</div>
      </Link>
      }
    </div>
  );
};

