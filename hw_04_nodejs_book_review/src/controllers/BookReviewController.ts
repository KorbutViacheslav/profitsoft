import { Request, Response } from "express";
import axios from "axios";
import { validationResult } from "express-validator";

import BookReview from "../models/BookReview";

export const createReview = async (req: Request, res: Response) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ errors: errors.array() });
  }

  const { bookId, message } = req.body;

  try {
    const bookServiceUrl = process.env.BOOK_SERVICE_URL;
    const bookResponse = await axios.get(
      `${bookServiceUrl}/api/book/${bookId}`
    );
    if (bookResponse.status !== 200) {
      return res.status(400).json({ error: "Invalid book ID" });
    }

    const review = await BookReview.create({ bookId, message });
    return res.status(201).json(review);
  } catch (error) {
    console.error(error);
    return res.sendStatus(400);
  }
};

export const getReviews = async (req: Request, res: Response) => {
  const { bookId } = req.query;
  const size = Number(req.query.size) || 10;
  const from = Number(req.query.from) || 0;

  if (!bookId) {
    return res.status(400).send({ message: "bookId is required" });
  }

  try {
    const reviews = await BookReview.find({ bookId })
      .sort({ timestamp: -1 })
      .skip(from)
      .limit(size);

    return res.status(200).json(reviews);
  } catch (err) {
    console.error(`Error in retrieving reviews with bookId ${bookId}.`, err);
    return res.status(500).send({ message: "Internal Server Error" });
  }
};

export const getReviewCounts = async (req: Request, res: Response) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ errors: errors.array() });
  }

  const { bookIds } = req.body;

  try {
    const counts = await BookReview.aggregate([
      { $match: { bookId: { $in: bookIds } } },
      { $group: { _id: "$bookId", count: { $sum: 1 } } },
      { $project: { _id: 0, bookId: "$_id", bookReviewCount: "$count" } },
    ]);

    return res.status(200).json(counts);
  } catch (error) {
    console.error(error);
    return res.sendStatus(400);
  }
};
