import { Router } from "express";
import {
  createReview,
  getReviews,
  getReviewCounts,
} from "../controllers/BookReviewController";
import { body, query } from "express-validator";

export default (router: Router): void => {
  router.post(
    "/api/review",
    body("bookId").isInt().withMessage("Book ID must be an integer"),
    body("message").notEmpty().withMessage("Message is required"),
    createReview
  );

  router.get(
    "/api/reviews",
    query("bookId").isInt().withMessage("Book ID must be an integer"),
    query("size").optional().isInt().withMessage("Size must be an integer"),
    query("from").optional().isInt().withMessage("From must be an integer"),
    getReviews
  );

  router.post(
    "/api/review/_counts",
    body("bookIds").isArray().withMessage("bookIds must be an array"),
    body("bookIds.*").isInt().withMessage("Book ID must be an integer"),
    getReviewCounts
  );
};
