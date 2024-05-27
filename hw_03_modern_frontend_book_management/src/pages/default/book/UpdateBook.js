import { useEffect, useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./UpdateBook.css";

import { useParams, useNavigate } from "react-router-dom";
const UpdateBook = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        title: "",
    });

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };
    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/book/${id}`);
                const data = await response.json();
                setFormData(data);
            } catch (error) {
                console.error("Error fetching book", error.message);
            }
        };
        fetchBooks();
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/book/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            const data = await response.json();
            console.log("Book updated: ", data);

            navigate("/");
        } catch (error) {
            console.error("Error updating author", error.message);
        }
    };

    return (
        <>
            <div className="center-form">
                <h1>Edit Book</h1>
                <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="formBasicTitle">
                        <Form.Control
                            type="text"
                            name="title"
                            placeholder="Enter title book"
                            value={formData.title}
                            onChange={handleInputChange}
                        />
                    </Form.Group>

                    <Button variant="primary" type="submit" className="w-100">
                        Edit Book
                    </Button>
                </Form>
            </div>
        </>
    );
};
export default UpdateBook;
