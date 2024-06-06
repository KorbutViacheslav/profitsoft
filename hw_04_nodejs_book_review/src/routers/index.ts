import express from "express";
import BookReviewRoutes from "./BookReviewRoutes";

const router = express.Router();

export default (): express.Router => {
  BookReviewRoutes(router);
  return router;
};
