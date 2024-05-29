import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import "./FilterModal.css";

const FilterModal = ({ show, onHide, onApply }) => {
    const [filterData, setFilterData] = useState({
        title: '',
        yearPublished: '',
        authorFirstName: '',
        authorLastName: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFilterData({ ...filterData, [name]: value });
    };

    const handleApply = () => {
        onApply(filterData);
    };

    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Filter Books</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="formTitle">
                        <Form.Label>Title</Form.Label>
                        <Form.Control
                            type="text"
                            name="title"
                            value={filterData.title}
                            onChange={handleChange}
                            placeholder="Enter title"
                        />
                    </Form.Group>
                    <Form.Group controlId="formYearPublished">
                        <Form.Label>Year Published</Form.Label>
                        <Form.Control
                            type="number"
                            name="yearPublished"
                            value={filterData.yearPublished}
                            onChange={handleChange}
                            placeholder="Enter year"
                        />
                    </Form.Group>
                    <Form.Group controlId="formAuthorFirstName">
                        <Form.Label>Author First Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="authorFirstName"
                            value={filterData.authorFirstName}
                            onChange={handleChange}
                            placeholder="Enter author's first name"
                        />
                    </Form.Group>
                    <Form.Group controlId="formAuthorLastName">
                        <Form.Label>Author Last Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="authorLastName"
                            value={filterData.authorLastName}
                            onChange={handleChange}
                            placeholder="Enter author's last name"
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>
                    Close
                </Button>
                <Button variant="primary" onClick={handleApply}>
                    Apply
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default FilterModal;
