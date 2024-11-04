/*import React, { useEffect, useState } from 'react'
import './Login.css'


export default function Login(){
    const [formData, setFormData] = useState({
        userName:"",
        password:""

    })

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

        // dodat tu slanje podataka na backend
    }

return(
<div className='loginDiv'> 
<h1>Log in with</h1>

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
            type="password"
            placeholder="password"
            onChange={handleChange}
            name="password"
            value={formData.password}
        />

<button>Submit</button>

</form>

</div>
)
} */



import React, { useEffect, useState } from 'react';
import { Button, Form, Container, Row, Col } from 'react-bootstrap';
import "@fortawesome/fontawesome-free/css/all.min.css"
import './Login.css';

export default function Login() {
  const [formData, setFormData] = useState({
    userName: "",
    password: ""
  });

  function handleChange(event) {
    const { name, value } = event.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: value
    }));
  }

  function handleSubmit(event) {
    event.preventDefault();
    //console.log(formData);
    // tu dodat slanje podataka na backend
  }

  return (
    <Container className='loginDiv'>
      <h1>Log in with</h1>

      <a href="http://localhost:8080/oauth2/authorization/google">
        <Button variant="outline-primary" className="mb-3">
          <i className="fab fa-google"></i> Google
        </Button>
      </a>

      <p className="separator"><span>or</span></p>

      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="formUserName">
          <Form.Control
            type="text"
            placeholder="UserName"
            onChange={handleChange}
            name="userName"
            value={formData.userName}
            className='inputi'
          />
        </Form.Group>

        <Form.Group controlId="formPassword">
          <Form.Control
            type="password"
            placeholder="Password"
            onChange={handleChange}
            name="password"
            value={formData.password}
            className='inputi'
          />
        </Form.Group>

        <Button variant="primary" type="submit" className="mt-3">
          Log in
        </Button>
      </Form>
    </Container>
  );
} 
