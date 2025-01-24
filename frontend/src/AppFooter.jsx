import React from 'react';
import './AppFooter.css';

export default function AppFooter() {
  return (
    <footer className="custom-footer">
      <div className="container py-3">
        <div className="d-flex justify-content-between align-items-center">
          <div>
            <a href="https://github.com/Jurica-Laljak/Produktivni-momci" className="footer-link">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-link" viewBox="0 0 16 16">
                <path d="M6.354 5.5H4a3 3 0 0 0 0 6h3a3 3 0 0 0 2.83-4H9q-.13 0-.25.031A2 2 0 0 1 7 10.5H4a2 2 0 1 1 0-4h1.535c.218-.376.495-.714.82-1z" />
                <path d="M9 5.5a3 3 0 0 0-2.83 4h1.098A2 2 0 0 1 9 6.5h3a2 2 0 1 1 0 4h-1.535a4 4 0 0 1-.82 1H12a3 3 0 1 0 0-6z" />
              </svg>
              <span>GitHub</span>
            </a>
          </div>
          <div>
            <h5><strong>Produktivni momci</strong>, 2025.</h5>
          </div>
          <div>
            <a href="https://github.com/Jurica-Laljak/Produktivni-momci/tree/main/Dokumentacija" className="footer-link">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-link" viewBox="0 0 16 16">
                <path d="M6.354 5.5H4a3 3 0 0 0 0 6h3a3 3 0 0 0 2.83-4H9q-.13 0-.25.031A2 2 0 0 1 7 10.5H4a2 2 0 1 1 0-4h1.535c.218-.376.495-.714.82-1z" />
                <path d="M9 5.5a3 3 0 0 0-2.83 4h1.098A2 2 0 0 1 9 6.5h3a2 2 0 1 1 0 4h-1.535a4 4 0 0 1-.82 1H12a3 3 0 1 0 0-6z" />
              </svg>
              <span>Dokumentacija</span>
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
}