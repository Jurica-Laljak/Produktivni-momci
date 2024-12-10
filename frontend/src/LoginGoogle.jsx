import React, { useEffect, useState } from 'react';
import { FcGoogle } from 'react-icons/fc';
import './LoginGoogle.css'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function LoginGoogle(){
   
  const backendUrl = import.meta.env.VITE_BACKEND_URL
  const frontednUrl = import.meta.env.VITE_FRONTEND_URL

      //redirect na stranicu za prijavu s gmailom 
        function ContinueGoogle(){
        // window.location.href = `${backendUrl}/oauth2/authorization/google`
        window.location.href = `${backendUrl}/oauth2/authorization/google?redirect_uri=${frontednUrl}`
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