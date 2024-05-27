// Book.js
import React from "react";
import {Card, Button, Row, Col} from "react-bootstrap";

const Book = ({book, onClick, onShowDeleteModal}) => {
    const {id, title, authorCreateDTO, yearPublished, genres} = book;

    return (
        <Card className="mb-3">
            <Card.Body>
                <Row className="align-items-center">
                    <Col>
                        <Card.Title>{title}</Card.Title>
                        <Card.Text>
                            <strong>Author:</strong> {authorCreateDTO.firstName} {authorCreateDTO.lastName}<br />
                            <strong>Year Published:</strong> {yearPublished}<br />
                            <strong>Genres:</strong> {genres.join(", ")}
                        </Card.Text>
                    </Col>
                    <Col className="text-right">
                        <Button
                            variant="outline-secondary"
                            onClick={() => onClick(id)}
                            className="mx-2"
                        >
                            Update
                        </Button>
                        <Button
                            variant="outline-danger"
                            className="delete-button mx-1"
                            onClick={() => onShowDeleteModal(book)}
                        >
                            Delete
                        </Button>
                    </Col>
                </Row>
            </Card.Body>
        </Card>
    );
};

export default Book;
