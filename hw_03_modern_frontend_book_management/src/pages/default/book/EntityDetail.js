import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { fetchBook, createBook, updateBook } from "../dashboard/requests.js";
import { Button, Alert, Form, Container } from "react-bootstrap";
import "./EntityDetail.css";

const EntityDetail = () => {
    const { bookId } = useParams();
    const navigate = useNavigate();
    const [book, setBook] = useState({
        title: "",
        yearPublished: "",
        author: {
            firstName: "",
            lastName: "",
        },
        genres: [],
    });
    const [initialBook, setInitialBook] = useState(null);
    const [isEditMode, setIsEditMode] = useState(!bookId);
    const [message, setMessage] = useState(null);
    const [error, setError] = useState(null);
    const [validationErrors, setValidationErrors] = useState({});

    useEffect(() => {
        const getBook = async () => {
            if (bookId) {
                try {
                    const data = await fetchBook(bookId);
                    setBook(data);
                    setInitialBook(data);
                } catch (error) {
                    setError("Failed to fetch book details.");
                }
            }
        };
        getBook();
    }, [bookId]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        if (name === "genres") {
            setBook((prevBook) => ({ ...prevBook, [name]: value.split(",").map((genre) => genre.trim()) }));
        } else if (name === "firstName" || name === "lastName") {
            setBook((prevBook) => ({ ...prevBook, author: { ...prevBook.author, [name]: value } }));
        } else {
            setBook((prevBook) => ({ ...prevBook, [name]: value }));
        }
    };

    const validateFields = () => {
        const errors = {};
        if (!book.title) errors.title = "Title is required.";
        if (!book.yearPublished || isNaN(book.yearPublished)) errors.yearPublished = "Valid year is required.";
        if (!book.author.firstName) errors.firstName = "Author's first name is required.";
        if (!book.author.lastName) errors.lastName = "Author's last name is required.";
        if (!book.genres.length) errors.genres = "At least one genre is required.";

        setValidationErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleSave = async () => {
        if (!validateFields()) return;

        try {
            if (bookId) {
                await updateBook(bookId, book);
                setMessage("Book updated successfully");
            } else {
                await createBook(book);
                setMessage("Book created successfully");
            }
            setIsEditMode(false);
            setTimeout(() => setMessage(null), 3000);
        } catch (error) {
            setError("Failed to save book. Please try again.");
        }
    };

    const handleCancel = () => {
        if (bookId) {
            setBook(initialBook);
            setIsEditMode(false);
            setMessage(null);
            setError(null);
        } else {
            navigate("/");
        }
    };

    return (
        <div className="entity-detail">
            <Container className="mt-5">
                {message && <Alert variant="success">{message}</Alert>}
                {error && <Alert variant="danger">{error}</Alert>}
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h1>{bookId ? "Book Details" : "Add Book"}</h1>
                    {bookId && !isEditMode && (
                        <Button variant="outline-primary" onClick={() => setIsEditMode(true)}>
                            Edit
                        </Button>
                    )}
                    <Button variant="outline-secondary" onClick={() => navigate("/")}>
                        Back
                    </Button>
                </div>
                <Form className={`${isEditMode ? "edit-mode" : ""}`}>
                    <Form.Group controlId="formTitle">
                        <Form.Label>Title</Form.Label>
                        {isEditMode ? (
                            <>
                                <Form.Control
                                    type="text"
                                    name="title"
                                    value={book.title}
                                    onChange={handleInputChange}
                                    isInvalid={!!validationErrors.title}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {validationErrors.title}
                                </Form.Control.Feedback>
                            </>
                        ) : (
                            <p className="form-control-plaintext">{book.title}</p>
                        )}
                    </Form.Group>
                    <Form.Group controlId="formYearPublished">
                        <Form.Label>Year Published</Form.Label>
                        {isEditMode ? (
                            <>
                                <Form.Control
                                    type="text"
                                    name="yearPublished"
                                    value={book.yearPublished}
                                    onChange={handleInputChange}
                                    isInvalid={!!validationErrors.yearPublished}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {validationErrors.yearPublished}
                                </Form.Control.Feedback>
                            </>
                        ) : (
                            <p className="form-control-plaintext">{book.yearPublished}</p>
                        )}
                    </Form.Group>
                    <Form.Group controlId="formFirstName">
                        <Form.Label>Author First Name</Form.Label>
                        {isEditMode ? (
                            <>
                                <Form.Control
                                    type="text"
                                    name="firstName"
                                    value={book.author.firstName}
                                    onChange={handleInputChange}
                                    isInvalid={!!validationErrors.firstName}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {validationErrors.firstName}
                                </Form.Control.Feedback>
                            </>
                        ) : (
                            <p className="form-control-plaintext">{book.author.firstName}</p>
                        )}
                    </Form.Group>
                    <Form.Group controlId="formLastName">
                        <Form.Label>Author Last Name</Form.Label>
                        {isEditMode ? (
                            <>
                                <Form.Control
                                    type="text"
                                    name="lastName"
                                    value={book.author.lastName}
                                    onChange={handleInputChange}
                                    isInvalid={!!validationErrors.lastName}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {validationErrors.lastName}
                                </Form.Control.Feedback>
                            </>
                        ) : (
                            <p className="form-control-plaintext">{book.author.lastName}</p>
                        )}
                    </Form.Group>
                    <Form.Group controlId="formGenres">
                        <Form.Label>Genres</Form.Label>
                        {isEditMode ? (
                            <>
                                <Form.Control
                                    type="text"
                                    name="genres"
                                    value={book.genres.join(", ")}
                                    onChange={handleInputChange}
                                    isInvalid={!!validationErrors.genres}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {validationErrors.genres}
                                </Form.Control.Feedback>
                            </>
                        ) : (
                            <p className="form-control-plaintext">{book.genres.join(", ")}</p>
                        )}
                    </Form.Group>
                    {isEditMode && (
                        <div className="d-flex justify-content-end mt-3">
                            <Button variant="secondary" onClick={handleCancel} className="me-2">
                                Cancel
                            </Button>
                            <Button variant="primary" onClick={handleSave}>
                                Save
                            </Button>
                        </div>
                    )}
                </Form>
            </Container>
        </div>
    );
};

export default EntityDetail;
