import mongoose from "mongoose";
import { expect } from "chai";
import request, { Response } from "supertest";
import server from "../src/index";

describe("Book Review API Integration Tests", () => {
  let serverInstance: any;

  before((done) => {
    serverInstance = server.listen(8082, () => {
      console.log("Test server running on http://localhost:8082/");
      done();
    });
  });

  after((done) => {
    serverInstance.close(() => {
      mongoose.connection
        .close()
        .then(() => done())
        .catch(done);
    });
  });

  let createdReviewId: string;

  it("should create a new review", (done) => {
    request(serverInstance)
      .post("/api/review")
      .send({ bookId: 1, message: "Great book!" })
      .expect("Content-Type", /json/)
      .expect(201)
      .end((err: any, res: Response) => {
        if (err) return done(err);
        expect(res.body).to.have.property("_id");
        createdReviewId = res.body._id;
        done();
      });
  });

  it("should get reviews for a book", (done) => {
    request(serverInstance)
      .get("/api/reviews")
      .query({ bookId: 1 })
      .expect("Content-Type", /json/)
      .expect(200)
      .end((err: any, res: Response) => {
        if (err) return done(err);
        expect(res.body).to.be.an("array");
        done();
      });
  });

  it("should get review counts for books", (done) => {
    request(serverInstance)
      .post("/api/review/_counts")
      .send({ bookIds: [1] })
      .expect("Content-Type", /json/)
      .expect(200)
      .end((err: any, res: Response) => {
        if (err) return done(err);
        expect(res.body).to.be.an("array");
        expect(res.body[0]).to.have.property("bookId", 1);
        expect(res.body[0]).to.have.property("bookReviewCount");
        done();
      });
  });

  it("should fail to create a review with invalid data", (done) => {
    request(serverInstance)
      .post("/api/review")
      .send({ bookId: "invalid", message: "" })
      .expect("Content-Type", /json/)
      .expect(400)
      .end((err: any, res: Response) => {
        if (err) return done(err);
        expect(res.body).to.have.property("errors");
        done();
      });
  });
});
