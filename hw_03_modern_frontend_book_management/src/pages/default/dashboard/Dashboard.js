import { useEffect, useState } from "react";
import {
    Button,
    Col,
    Container,
    Row,
    Table,
    Pagination,
    Alert,
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
                        <Table striped bordered hover responsive>
                            <thead>
                            <tr>
                                <th>Firstname</th>
                                <th>Lastname</th>
                                <th className="action-column">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            {currentAuthors.length > 0 ? (
                                currentAuthors.map((author) => (
                                    <tr key={author.id}>
                                        <td>{author.firstName}</td>
                                        <td>{author.lastName}</td>
                                        <td>
                                            <Button
                                                variant="outline-secondary"
                                                onClick={() => handleUpdate(author.id)}
                                            >
                                                Update
                                            </Button>{" "}
                                            <Button
                                                variant="outline-danger"
                                                onClick={() => handleDelete(author.id)}
                                            >
                                                Delete
                                            </Button>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="3" className="text-center">
                                        No authors found.
                                    </td>
                                </tr>
                            )}
                            </tbody>
                        </Table>
                        <Pagination>
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
                    </Col>
                </Row>
            </Container>
        </>
    );
};

export default Dashboard;
