import React, { useEffect, useState } from 'react';
import { FcGoogle } from 'react-icons/fc';
import './LoginGoogle.css'
import axios from 'axios';

export default function LoginGoogle(){

        function ContinueGoogle(){
                axios.get("http://localhost:8080/oauth2/authorization/google")
                // tu mozda dodat sa then da dohvatimo jel korisnik novi ili stari
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