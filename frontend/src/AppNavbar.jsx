import React, { useEffect, useState } from 'react'
import { Navbar, Nav, Container } from 'react-bootstrap';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import './AppNavbar.css'
import SearchBar from './SearchBar'
import { FcGoogle } from 'react-icons/fc';

export default function AppNavbar({setResults}){
return(
  <Navbar className="custom-navbar" expand="lg">
  <Container>
    <Navbar.Brand as={Link} to="/" >Ticket4Ticket</Navbar.Brand>
    <Navbar.Toggle aria-controls="basic-navbar-nav" />
    
    <Navbar.Collapse id="basic-navbar-nav">
      <SearchBar setResults={setResults}/>
      <Nav className="ms-auto" >
        <Nav.Link as={Link} to="/login">
        Log in with Google 
        <FcGoogle style={{ marginLeft: '5px' }} />
        </Nav.Link>
      </Nav>
    </Navbar.Collapse>
   
  </Container>
</Navbar>
)

}