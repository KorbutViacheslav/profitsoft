import React, { useState, useEffect } from "react";
import "./ConfirmModal.css";

const ConfirmModal = ({ show, onClose, onConfirm, book }) => {
    const [status, setStatus] = useState(null);

    const handleConfirm = async () => {
        try {
            await onConfirm();
            setStatus("success");
        } catch (error) {
            setStatus("error");
        }
    };

    useEffect(() => {
        if (status) {
            const timer = setTimeout(() => {
                setStatus(null);
                onClose();
            }, 2000);

            return () => clearTimeout(timer);
        }
    }, [status, onClose]);

    if (!show) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <div className="modal-header">
                    <h3 className="confirm-delete-text">Confirm Delete</h3>
                    <button className="close-button" onClick={onClose}>
                        &times;
                    </button>
                </div>
                <div className="modal-body">
                    {status === "success" ? (
                        <p className="success-message">Book "{book?.title}" deleted successfully</p>
                    ) : status === "error" ? (
                        <p className="error-message">Failed to delete book. Please try again later.</p>
                    ) : (
                        <p>
                            Are you sure you want to delete book "{book?.title}"?
                        </p>
                    )}
                </div>
                <div className="modal-footer">
                    {!status && (
                        <>
                            <button className="btn btn-secondary" onClick={onClose}>
                                Cancel
                            </button>
                            <button className="btn btn-danger" onClick={handleConfirm}>
                                OK
                            </button>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ConfirmModal;
