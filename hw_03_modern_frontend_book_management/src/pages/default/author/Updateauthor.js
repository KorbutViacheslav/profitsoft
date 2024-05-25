import { useEffect, useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Updateauthor.css";
import { useParams, useNavigate } from "react-router-dom";
const Updateauthor = () => {
    const { id } = useParams();
    const navigate = useNavigate();

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
    useEffect(() => {
        const fetchAuthors = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/author/${id}`);
                const data = await response.json();
                setFormData(data);
            } catch (error) {
                console.error("Error fetching author", error.message);
            }
        };
        fetchAuthors();
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/author/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            const data = await response.json();
            console.log("Author updated: ", data);

            navigate("/");
        } catch (error) {
            console.error("Error updating author", error.message);
        }
    };

    return (
        <>
            <div className="center-form">
                <h1>Edit Author</h1>
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
                        Edit Author
                    </Button>
                </Form>
            </div>
        </>
    );
};
export default Updateauthor;
