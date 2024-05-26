import { useEffect, useState } from "react";
import { Button, Col, Container, Row, Alert, ListGroup } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { fetchAuthors, deleteAuthor } from "./requests.js";
import ConfirmModal from "./ConfirmModal";
import PaginationComponent from "./PaginationComponent";
import "./Dashboard.css";

const Dashboard = () => {
    const [authors, setAuthors] = useState([]);
    const [currentPage, setCurrentPage] = useState(() => {
        const savedPage = localStorage.getItem("currentPage");
        return savedPage ? JSON.parse(savedPage) : 1;
    });
    const [error, setError] = useState(null);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [authorToDelete, setAuthorToDelete] = useState(null);
    const authorsPerPage = 3;
    const navigate = useNavigate();

    useEffect(() => {
        const getAuthors = async () => {
            try {
                const data = await fetchAuthors();
                setAuthors(data);
            } catch (error) {
                setError("Failed to fetch authors. Please try again later.");
            }
        };
        getAuthors();
    }, []);

    const handleDelete = async () => {
        await deleteAuthor(authorToDelete.id);
        setAuthors((prevAuthors) => prevAuthors.filter((author) => author.id !== authorToDelete.id));
    };

    const handleUpdate = (authorId) => {
        navigate(`/author/${authorId}`);
    };

    const handleShowDeleteModal = (author) => {
        setAuthorToDelete(author);
        setShowDeleteModal(true);
    };

    const indexOfLastAuthor = currentPage * authorsPerPage;
    const indexOfFirstAuthor = indexOfLastAuthor - authorsPerPage;
    const currentAuthors = authors.slice(indexOfFirstAuthor, indexOfLastAuthor);

    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
        localStorage.setItem("currentPage", JSON.stringify(pageNumber));
    };

    return (
        <>
            <Container className="mt-5">
                <Row>
                    <Col>
                        <h1 className="text-center">Authors</h1>
                        {error && <Alert variant="danger">{error}</Alert>}
                        <ListGroup>
                            {currentAuthors.length > 0 ? (
                                currentAuthors.map((author, index) => (
                                    <ListGroup.Item key={author.id} className="author-item">
                                        <Row className="align-items-center">
                                            <Col>
                                                {index + 1 + (currentPage - 1) * authorsPerPage}.{" "}
                                                <span className="author-name">{author.firstName} {author.lastName}</span>
                                            </Col>
                                            <Col className="text-right">
                                                <Button
                                                    variant="outline-secondary"
                                                    onClick={() => handleUpdate(author.id)}
                                                    className="mx-1"
                                                >
                                                    Update
                                                </Button>
                                                <Button
                                                    variant="outline-danger"
                                                    className="delete-button mx-1"
                                                    onClick={() => handleShowDeleteModal(author)}
                                                >
                                                    Delete
                                                </Button>
                                            </Col>
                                        </Row>
                                    </ListGroup.Item>
                                ))
                            ) : (
                                <ListGroup.Item className="text-center">
                                    No authors found.
                                </ListGroup.Item>
                            )}
                        </ListGroup>
                        <PaginationComponent
                            currentPage={currentPage}
                            totalItems={authors.length}
                            itemsPerPage={authorsPerPage}
                            paginate={paginate}
                        />
                    </Col>
                </Row>
            </Container>

            <ConfirmModal
                show={showDeleteModal}
                onClose={() => setShowDeleteModal(false)}
                onConfirm={handleDelete}
                author={authorToDelete}
            />
        </>
    );
};

export default Dashboard;
