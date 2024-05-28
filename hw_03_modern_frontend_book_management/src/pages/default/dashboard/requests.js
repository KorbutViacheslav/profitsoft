const API_BASE_URL = "http://localhost:8080/api";

export const fetchBooks = async () => {
    const response = await fetch(`${API_BASE_URL}/books`);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
};

export const fetchBook = async (bookId) => {
    const response = await fetch(`${API_BASE_URL}/book/${bookId}`);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
};

export const createBook = async (book) => {
    const response = await fetch(`${API_BASE_URL}/book`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(book),
    });
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
};

export const updateBook = async (bookId, book) => {
    const response = await fetch(`${API_BASE_URL}/book/${bookId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(book),
    });
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
};

export const deleteBook = async (bookId) => {
    const response = await fetch(`${API_BASE_URL}/book/${bookId}`, {
        method: "DELETE",
    });
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response;
};
