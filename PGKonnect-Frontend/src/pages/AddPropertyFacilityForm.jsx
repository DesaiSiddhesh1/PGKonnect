import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";

const AddPropertyFacilityForm = () => {
    const { propertyId } = useParams();
    const owner_jwtToken = sessionStorage.getItem("owner-jwtToken");

    const navigate = useNavigate();

    const [facilityDetail, setFacilityDetail] = useState({
        name: "",
        description: "",
        propertyId: propertyId,
    });

    const handleChange = (e) => {
        setFacilityDetail({ ...facilityDetail, [e.target.name]: e.target.value });
    };

    const validateForm = () => {
        let isValid = true;

        if (!facilityDetail.name) {
            isValid = false;
            toast.error("Facility Name is required", {
                position: "top-center",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
            });
        }

        if (!facilityDetail.description) {
            isValid = false;
            toast.error("Description is required", {
                position: "top-center",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
            });
        }

        return isValid;
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        fetch("http://localhost:8080/api/property/facility/add", {
            method: "PUT",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
                Authorization: "Bearer " + owner_jwtToken,
            },
            body: JSON.stringify(facilityDetail),
        })
            .then((result) => {
                result.json().then((res) => {
                    if (res.success) {
                        toast.success(res.responseMessage, {
                            position: "top-center",
                            autoClose: 1000,
                        });
                        setTimeout(() => {
                        }, 1000);
                    } else {
                        toast.error(res.responseMessage, {
                            position: "top-center",
                            autoClose: 1000,
                        });
                    }
                });
            })
            .catch(() => {
                toast.error("Server is Down", {
                    position: "top-center",
                    autoClose: 1000,
                });
            });
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100 mb-5">
            <div className="card shadow-lg p-4" style={{ width: "30rem" }}>
                <h3 className="text-center mb-4">Add Facility Details</h3>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">Facility Name</label>
                        <input type="text"
                            id="name"
                            name="name"
                            className={`form-control`}
                            value={facilityDetail.name}
                            onChange={handleChange}

                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="description" className="form-label">Description</label>
                        <textarea
                            id="description"
                            name="description"
                            className={`form-control`}
                            value={facilityDetail.description}
                            onChange={handleChange}

                        />
                    </div>
                    <button type="submit" className="btn bg-color custom-bg-text w-100">
                        Add Facility
                    </button>
                </form>
            </div>
            <ToastContainer />
        </div>
    );
};

export default AddPropertyFacilityForm;
