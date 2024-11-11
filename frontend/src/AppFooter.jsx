import React from 'react';
import { Link } from 'react-router-dom';
import './AppFooter.css';

export default function AppFooter() {
  return (
    <footer className="custom-footer">
      <div className="container py-3">
        <div className="row">
          <div className="col-md-4">
            <p><img src='src\assets\t4tedit.png' alt='t4t' style={{ height: '20px' }}></img></p>
            <p>Razmjena i prodaja ulaznica za koncerte.</p>
          </div>
          <div className="col-md-4">
            <h5>Brze poveznice</h5>
            <ul className="list-unstyled">
              <li>Početna</li>
              <li>O nama</li>
              <li>Kontakt</li>
            </ul>
          </div>
          <div className="col-md-4 text-md-end">
            <p>&copy; {new Date().getFullYear()} Ticket4Ticket</p>
            <p>Sva prava pridržana.</p>
          </div>
        </div>
      </div>
    </footer>
  );
}
