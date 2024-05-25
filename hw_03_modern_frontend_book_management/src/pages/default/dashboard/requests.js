const API_BASE_URL = "http://localhost:8080/api";

export const fetchAuthors = async () => {
    const response = await fetch(`${API_BASE_URL}/authors`);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
};

export const deleteAuthor = async (authorId) => {
    const response = await fetch(`${API_BASE_URL}/author/${authorId}`, {
        method: "DELETE",
    });
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response;
};
