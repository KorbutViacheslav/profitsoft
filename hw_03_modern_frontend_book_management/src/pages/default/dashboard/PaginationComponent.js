import React from "react";
import { Pagination } from "react-bootstrap";
import "./PaginationComponent.css";

const PaginationComponent = ({ currentPage, totalItems, itemsPerPage, paginate }) => {
    const pageNumbers = [...Array(Math.ceil(totalItems / itemsPerPage)).keys()];

    return (
        <div className="d-flex justify-content-center mt-3">
            <Pagination className="pagination-custom">
                {pageNumbers.map((number) => (
                    <Pagination.Item
                        key={number + 1}
                        onClick={() => paginate(number + 1)}
                        active={number + 1 === currentPage}
                    >
                        {number + 1}
                    </Pagination.Item>
                ))}
            </Pagination>
        </div>
    );
};

export default PaginationComponent;
