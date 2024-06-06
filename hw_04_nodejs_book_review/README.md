# Node.js REST API Project. Book Review.
* [Task issued](https://docs.google.com/document/d/1qZFp9bT4QGd_vRhRHDyIIIBxFDNO7M_i5BEe3Ad8dCk/edit?hl=ru&tab=t.0#heading=h.arwffw3rrrrx) (Only for users who have access to the internship)
* A short video about how the application works [YouTube](https://www.youtube.com/watch?v=popOtcncZgw)
---
## Application Description
  Book Review is a REST API service built with Node.js, using TypeScript and MongoDB. 
This service allows for the creation and management of book reviews. Reviews have the attributes: `bookId`, `message`, and `timestamp`, which indicates the time the review was created. 
The service interacts with another server to verify the existence of a book by its `bookId`.
---
### Features
- **Create Reviews**: Add a new review for a book.
- **Retrieve Reviews**: Get a list of reviews for a specific book.
- **Count Reviews**: Get the count of reviews for specified books.
- **Validation**: Validate input data for all API endpoints.
- **Integration Tests**: Cover all endpoints with integration tests.
---
### Usage
1. **Install Dependencies**:
    ```bash
    npm install
    ```

2. **Environment Setup**:
   Create a `.env` file in the project root and add the following variables:
    ```env
    MONGO_URL=your_mongo_db_connection_string
    BOOK_SERVICE_URL=http://localhost:8080
    ```

3. **Start the Server**:
    ```bash
    npm start
    ```
   The server will start on `http://localhost:8081/`.
---
### Controllers

#### POST /api/review
Creates a new BookReview record.
- **Request Body**:
    ```json
    {
      "bookId": 1,
      "message": "This is a great book!"
    }
    ```
- **Response**:
    ```json
    {
      "bookId": 1,
      "message": "This is a great book!",
      "timestamp": "2024-06-06T12:34:56.789Z"
    }
    ```

#### GET /api/reviews
Returns a list of BookReview objects for a single Book record, sorted in descending order by timestamp (most recent first).
- **Query Parameters**:
  - `bookId`: required
  - `size`: optional, maximum number of objects to return
  - `from`: optional, the number of objects to skip from the beginning
- **Response**:
    ```json
    [
      {
        "bookId": 1,
        "message": "This is a great book!",
        "timestamp": "2024-06-06T12:34:56.789Z"
      }
    ]
    ```

#### POST /api/review/_counts
Accepts an array of Book entity IDs and returns the total number of BookReview entities related to each Book entity.
- **Request Body**:
    ```json
    {
      "bookIds": [1, 2, 3]
    }
    ```
- **Response**:
    ```json
    [
      {
        "bookId": 1,
        "bookReviewCount": 5
      },
      {
        "bookId": 2,
        "bookReviewCount": 3
      },
      {
        "bookId": 3,
        "bookReviewCount": 0
      }
    ]
    ```

### Summary
This project provides an easy way to manage book reviews through a REST API. 
Using TypeScript ensures strict type control, while MongoDB allows efficient data storage and processing. 
Integration with another service to verify the existence of a book ensures that reviews are only created for valid books. 
The project includes all necessary endpoints for creating, retrieving, and counting reviews, making it a convenient tool for managing book reviews.