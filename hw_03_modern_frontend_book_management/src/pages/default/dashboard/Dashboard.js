import { useEffect, useState } from "react";
import {
    Button,
    Col,
    Container,
    Row,
    Pagination,
    Alert,
    ListGroup
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { fetchAuthors, deleteAuthor } from "./requests.js";
import "./Dashboard.css";

const Dashboard = () => {
    const [authors, setAuthors] = useState([]);
    const [currentPage, setCurrentPage] = useState(() => {
        const savedPage = localStorage.getItem("currentPage");
        return savedPage ? JSON.parse(savedPage) : 1;
    });
    const [error, setError] = useState(null);
    const authorsPerPage = 3;
    const navigate = useNavigate();

    useEffect(() => {
        const getAuthors = async () => {
            try {
                const data = await fetchAuthors();
                console.log('Fetched authors:', data);
                setAuthors(data);
            } catch (error) {
                setError("Failed to fetch authors. Please try again later.");
                console.error("Error fetching authors:", error.message);
            }
        };
        getAuthors();
    }, []);

    const handleDelete = async (authorId) => {
        try {
            await deleteAuthor(authorId);
            setAuthors((prevAuthors) =>
                prevAuthors.filter((author) => author.id !== authorId)
            );
            console.log(`Author with id ${authorId} deleted successfully`);
        } catch (error) {
            setError("Failed to delete author. Please try again later.");
            console.error("Error deleting author:", error.message);
        }
    };

    const handleUpdate = (authorId) => {
        navigate(`/author/${authorId}`);
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
                                                {index + 1 + (currentPage - 1) * authorsPerPage}. <span className="author-name">{author.firstName} {author.lastName}</span>
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
                                                    onClick={() => handleDelete(author.id)}
                                                    className="mx-1"
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
                        <div className="d-flex justify-content-center mt-3">
                            <Pagination className="pagination-custom">
                                {[
                                    ...Array(Math.ceil(authors.length / authorsPerPage)).keys(),
                                ].map((number) => (
                                    <Pagination.Item
                                        key={number + 1}
                                        onClick={() => paginate(number + 1)}
                                        active={number + 1 === currentPage}
                                    >
                                        {number + 1}
                                    </Pagination.Item>
                                ))}
                            </Pagination>
                        </div>
                    </Col>
                </Row>
            </Container>
        </>
    );
};

export default Dashboard;
