import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import "./Postauthor.css";

const Postauthor = () => {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
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
            const response = await fetch("http://localhost:8080/api/author", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            const data = await response.json();
            console.log("Author created: ", data);
            navigate("/");
        } catch (error) {
            console.log("Error creating author: ", error.massage);
        }
    };

    return (
        <>
            <div className="center-form">
                <h1>Post New Author</h1>
                <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="formBasicFirstName">
                        <Form.Control
                            type="text"
                            name="firstName"
                            placeholder="Enter first name"
                            value={formData.firstName}
                            onChange={handleInputChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formBasicLastName">
                        <Form.Control
                            type="text"
                            name="lastName"
                            placeholder="Enter last name"
                            value={formData.lastName}
                            onChange={handleInputChange}
                        />
                    </Form.Group>
                    <Button variant="primary" type="submit" className="w-100">
                        Post Author
                    </Button>
                </Form>
            </div>
        </>
    );
};
export default Postauthor;
