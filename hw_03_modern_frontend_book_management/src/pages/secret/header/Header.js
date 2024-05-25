
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
                    <strong>Author Management System</strong>
                </Navbar.Brand>
                <Nav className="custom-nav">
                    <Nav.Link as={Link} to="/" className="custom-link">
                        Authors
                    </Nav.Link>
                    <Nav.Link as={Link} to="/author" className="custom-link">
                        Post Author
                    </Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    );
};

export default Header;
