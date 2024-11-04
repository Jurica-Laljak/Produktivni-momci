import React, { useEffect, useState } from 'react'
import './SignUp.css';
import "@fortawesome/fontawesome-free/css/all.min.css"


export default function SignUp(){
    
const [email, setFormData] = useState({
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
    <div className='signupDiv'>
        <h1>Sign up on Ticket4Ticket</h1>

        <a href="http://localhost:8080/oauth2/authorization/google" >
    <button>
    <i className="fab fa-google"></i> Google </button>
    </a>    


        <p className="separator"><span>or</span></p>

        <form onSubmit={handleSubmit}>
<input  className='inputi'
            type="text"
            placeholder="UserName"
            onChange={handleChange}
            name="userName"
            value={formData.userName}
        />

<input       className='inputi'
            type="email"
            placeholder="E-mail"
            onChange={handleChange}
            name="email"
            value={formData.email}
        />

<button>Submit</button>

</form>




    </div>
)

}