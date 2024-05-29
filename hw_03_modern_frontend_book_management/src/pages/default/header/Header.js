import React from 'react';
import { Container, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";
import Nav from "react-bootstrap/Nav";
import "./Header.css";

const Header = () => {
    return (
        <Navbar className="custom-navbar" variant="dark">
            <Container className="custom-container">
                <Navbar.Brand className="custom-brand">
                    <strong>Books Management System</strong>
                </Navbar.Brand>
                <Nav className="custom-nav">
                    <Nav.Link as={Link} to="/" className="custom-link">
                        Books
                    </Nav.Link>
                    <Nav.Link as={Link} to="/book" className="custom-link">
                        Post Book
                    </Nav.Link>
                    <Nav.Link as={Link} to="#" className="custom-link" onClick={() => { document.dispatchEvent(new CustomEvent('openFilterModal')) }}>
                        Filter
                    </Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    );
};

export default Header;
