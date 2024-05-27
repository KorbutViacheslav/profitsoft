// BookDetails.js
import React, {useState, useEffect} from "react";
import {useParams} from "react-router-dom";

const BookDetails = () => {
    const {id} = useParams();
    const [book, setBook] = useState(null);

    useEffect(() => {
        const fetchBookDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/book/${id}`);
                const data = await response.json();
                setBook(data);
            } catch (error) {
                console.error("Failed to fetch book details:", error);
            }
        };

        fetchBookDetails();
    }, [id]);

    if (!book) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>{book.title}</h2>
            <p><strong>Author:</strong> {book.authorCreateDTO.firstName} {book.authorCreateDTO.lastName}</p>
            <p><strong>Year Published:</strong> {book.yearPublished}</p>
            <p><strong>Genres:</strong> {book.genres.join(", ")}</p>
        </div>
    );
};

export default BookDetails;
