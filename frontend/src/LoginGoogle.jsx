import React, { useEffect, useState } from 'react';
import { FcGoogle } from 'react-icons/fc';
import './LoginGoogle.css'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function LoginGoogle(){

   
      //redirect na stranicu za prijavu s gmailom 
        function ContinueGoogle(){
        window.location.href =  "http://localhost:8080/oauth2/authorization/google"           
        }

    return (
        <div className="login-page">
          <h2>Sign up or Log in</h2>
          <button className="google-auth-button"
                   onClick={ContinueGoogle} >
            <FcGoogle style={{ marginRight: '8px' }} />
            Continue with Google
          </button>
        </div>
      );
}