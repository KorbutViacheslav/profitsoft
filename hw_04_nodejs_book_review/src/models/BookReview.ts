import mongoose, { Schema, Document } from "mongoose";

const BookReviewSchema: Schema = new Schema({
  bookId: { type: Number, required: true },
  message: { type: String, required: true },
  timestamp: { type: Date, required: true, default: Date.now },
});

export interface IBookReview extends Document {
  bookId: Number;
  message: String;
  timestamp: Date;
}

const BookReview = mongoose.model<IBookReview>("BookReview", BookReviewSchema);
export default BookReview;
