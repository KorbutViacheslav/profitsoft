import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import "./FilterModal.css";

const FilterModal = ({ show, onHide, onApply }) => {
    const [filterData, setFilterData] = useState({
        title: "",
        yearPublish: "",
        authorFirstName: "",
        authorLastName: ""
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFilterData({
            ...filterData,
            [name]: value
        });
    };

    const handleSubmit = () => {
        onApply(filterData);
    };

    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header>
                <Modal.Title>Filter Books</Modal.Title>
                <button className="modal-close-button" onClick={onHide}>&times;</button>
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
                        />
                    </Form.Group>

                    <Form.Group controlId="formYearPublish">
                        <Form.Label>Year Publish</Form.Label>
                        <Form.Control
                            type="number"
                            name="yearPublish"
                            value={filterData.yearPublish}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formAuthorFirstName">
                        <Form.Label>Author First Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="authorFirstName"
                            value={filterData.authorFirstName}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formAuthorLastName">
                        <Form.Label>Author Last Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="authorLastName"
                            value={filterData.authorLastName}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>
                    Cancel
                </Button>
                <Button variant="primary" onClick={handleSubmit}>
                    Apply
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default FilterModal;
