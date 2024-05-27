import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import "./PostBook.css";

const PostBook = () => {
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

    const navigate = useNavigate();
    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(formData);

        try {
            const response = await fetch("http://localhost:8080/api/book", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            const data = await response.json();
            console.log("Book created: ", data);
            navigate("/");
        } catch (error) {
            console.log("Error creating book: ", error.message);
        }
    };

    return (
        <>
            <div className="center-form">
                <h1>Post New Book</h1>
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
                        Post Book
                    </Button>
                </Form>
            </div>
        </>
    );
};

export default PostBook;
