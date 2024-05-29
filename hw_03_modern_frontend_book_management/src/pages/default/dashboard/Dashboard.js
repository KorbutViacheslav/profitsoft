import { useEffect, useState } from "react";
import { Col, Container, Row, Alert, ListGroup } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { fetchBooks, deleteBook } from "./requests";
import ConfirmModal from "../modal/ConfirmModal";
import PaginationComponent from "../pagination/PaginationComponent";
import FilterModal from "../filter/FilterModal";
import "./Dashboard.css";
import Button from "react-bootstrap/Button";

const Dashboard = () => {
    const [books, setBooks] = useState([]);
    const [currentPage, setCurrentPage] = useState(() => {
        const savedPage = localStorage.getItem("currentPage");
        return savedPage ? JSON.parse(savedPage) : 1;
    });
    const [error, setError] = useState(null);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [bookToDelete, setBookToDelete] = useState(null);
    const [showFilterModal, setShowFilterModal] = useState(false);
    const [filter, setFilter] = useState(null);

    const booksPerPage = 3;
    const navigate = useNavigate();

    useEffect(() => {
        const getBooks = async () => {
            try {
                const data = await fetchBooks(filter);
                setBooks(data);
            } catch (error) {
                setError("Failed to fetch books. Please try again later.");
            }
        };
        getBooks();
    }, [filter]);

    useEffect(() => {
        const openFilterModalHandler = () => {
            setShowFilterModal(true);
        };

        document.addEventListener('openFilterModal', openFilterModalHandler);

        return () => {
            document.removeEventListener('openFilterModal', openFilterModalHandler);
        };
    }, []);

    const handleDelete = async () => {
        await deleteBook(bookToDelete.id);
        setBooks((prevBooks) => prevBooks.filter((book) => book.id !== bookToDelete.id));
    };

    const handleShowDeleteModal = (book) => {
        setBookToDelete(book);
        setShowDeleteModal(true);
    };

    const handleFilterApply = (filterData) => {
        setFilter(filterData);
        setShowFilterModal(false);
    };

    const indexOfLastBook = currentPage * booksPerPage;
    const indexOfFirstBook = indexOfLastBook - booksPerPage;
    const currentBooks = books.slice(indexOfFirstBook, indexOfLastBook);

    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
        localStorage.setItem("currentPage", JSON.stringify(pageNumber));
    };

    const handleNavigateToDetail = (bookId) => {
        navigate(`/book/${bookId}`);
    };

    return (
        <>
            <Container className="mt-5">
                <Row>
                    <Col>
                        <h1 className="text-center">Books</h1>
                        {error && <Alert variant="danger">{error}</Alert>}
                        <ListGroup>
                            {currentBooks.length > 0 ? (
                                currentBooks.map((book, index) => (
                                    <ListGroup.Item key={book.id} className="book-item" onClick={() => handleNavigateToDetail(book.id)}>
                                        <Row className="align-items-center">
                                            <Col>
                                                {index + 1 + (currentPage - 1) * booksPerPage}.{" "}
                                                <span className="book-title">
                                                    Title: {book.title}
                                                    <div>Publish year: {book.yearPublished}</div>
                                                </span>
                                            </Col>
                                            <Col className="text-right">
                                                <Button
                                                    variant="outline-danger"
                                                    className="delete-button mx-1"
                                                    onClick={(e) => {
                                                        e.stopPropagation();
                                                        handleShowDeleteModal(book);
                                                    }}
                                                >
                                                    Delete
                                                </Button>
                                            </Col>
                                        </Row>
                                    </ListGroup.Item>
                                ))
                            ) : (
                                <ListGroup.Item className="text-center">
                                    No books found.
                                </ListGroup.Item>
                            )}
                        </ListGroup>
                        <PaginationComponent
                            currentPage={currentPage}
                            totalItems={books.length}
                            itemsPerPage={booksPerPage}
                            paginate={paginate}
                        />
                    </Col>
                </Row>
            </Container>

            <ConfirmModal
                show={showDeleteModal}
                onClose={() => setShowDeleteModal(false)}
                onConfirm={handleDelete}
                book={bookToDelete}
            />

            <FilterModal
                show={showFilterModal}
                onHide={() => setShowFilterModal(false)}
                onApply={handleFilterApply}
            />
        </>
    );
};

export default Dashboard;
