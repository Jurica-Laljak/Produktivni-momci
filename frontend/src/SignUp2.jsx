import React, { useEffect, useState } from 'react'
import './SignUp2.css';
import "@fortawesome/fontawesome-free/css/all.min.css"


export default function SignUp2(){
    
const [formData, setFormData] = useState({
    email:"",
    userName:""
});

function handleChange(event){
    const {name,value} = event.target

    setFormData(prevData => {
        return {
            ...prevData,
            [name]: value
        }
    })

}

function handleSubmit(event){
    event.preventDefault()
    // tu dodat slanje podataka na backend


}

return (

      

    <div className='signup-container'>
        <h2 className='form-title'>Sign up on Ticket4Ticket</h2>
        <div className='social-signup'>
            <button className='social-button'>
            <i className="fab fa-google"></i>Google
            </button>
        </div>

        <p className='separator2'><span>or</span></p>

        <form onSubmit={handleSubmit} className='signup-form'>
            <div className='input-wrapper'>
<input  className='input-field'
            type="text"
            placeholder="UserName"
            onChange={handleChange}
            name="userName"
            value={formData.userName}
        />
        </div>

<div className='input-wrapper'>
<input       className='input-field'
            type="email"
            placeholder="E-mail"
            onChange={handleChange}
            name="email"
            value={formData.email}
        />
        </div>

<button className='signup-button'>Sign up</button>

</form>

    </div>


    
)

}