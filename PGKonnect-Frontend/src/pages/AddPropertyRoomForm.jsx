import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";

const AddPropertyRoomForm = () => {
  const { propertyId } = useParams();
  const owner_jwtToken = sessionStorage.getItem("owner-jwtToken");

  const navigate = useNavigate();

  const [roomDetails, setRoomDetails] = useState({
    roomNo: "",
    description: "",
    roomFor: "",
    type: "",
    privateRoomPrice: "",
    sharedRoomOneBedPrice: "",
    totalBeds: "",
    sharedBedsNos: "",
    propertyId: propertyId,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    setRoomDetails((prevDetails) => {
      const updatedDetails = { ...prevDetails, [name]: value };

      if (name === "type") {
        if (value === "Private") {
          updatedDetails.sharedRoomOneBedPrice = "0.0";
          updatedDetails.totalBeds = "0";
          updatedDetails.sharedBedsNos = "";
        } else if (value === "Shared") {
          updatedDetails.privateRoomPrice = "0.0";
        }
      }

      return updatedDetails;
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    fetch("http://localhost:8080/api/property/room/add", {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: "Bearer " + owner_jwtToken,
      },
      body: JSON.stringify(roomDetails),
    })
      .then((result) => {
        result.json().then((res) => {
          if (res.success) {
            toast.success(res.responseMessage, {
              position: "top-center",
              autoClose: 1000,
            });
            setTimeout(() => {
              navigate("/home");
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
        toast.error("It seems server is down", {
          position: "top-center",
          autoClose: 1000,
        });
      });
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 mb-5">
      <div className="card shadow-lg p-4" style={{ width: "30rem" }}>
        <h3 className="text-center mb-4">Add Room Details</h3>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="roomNo" className="form-label">
              Room Number
            </label>
            <input
              type="text"
              id="roomNo"
              name="roomNo"
              className="form-control"
              value={roomDetails.roomNo}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="description" className="form-label">
              Description
            </label>
            <textarea
              id="description"
              name="description"
              className="form-control"
              value={roomDetails.description}
              onChange={handleChange}
              required
            ></textarea>
          </div>

          <div className="mb-3">
            <label htmlFor="roomFor" className="form-label">
              Room For
            </label>
            <select
              id="roomFor"
              name="roomFor"
              className="form-control"
              value={roomDetails.roomFor}
              onChange={handleChange}
              required
            >
              <option value="">Select</option>
              <option value="Boys">Boys</option>
              <option value="Girls">Girls</option>
              <option value="Family">Family</option>
            </select>
          </div>

          <div className="mb-3">
            <label htmlFor="type" className="form-label">
              Type
            </label>
            <select
              id="type"
              name="type"
              className="form-control"
              value={roomDetails.type}
              onChange={handleChange}
              required
            >
              <option value="">Select Room Type</option>
              <option value="Shared">Shared</option>
              <option value="Private">Private</option>
            </select>
          </div>

          {roomDetails.type === "Private" && (
            <div className="mb-3">
              <label htmlFor="privateRoomPrice" className="form-label">
                Private Room Price
              </label>
              <input
                type="number"
                id="privateRoomPrice"
                name="privateRoomPrice"
                className="form-control"
                value={roomDetails.privateRoomPrice}
                onChange={handleChange}
                required
              />
            </div>
          )}

          {roomDetails.type === "Shared" && (
            <>
              <div className="mb-3">
                <label htmlFor="sharedRoomOneBedPrice" className="form-label">
                  Shared Room One Bed Price
                </label>
                <input
                  type="number"
                  id="sharedRoomOneBedPrice"
                  name="sharedRoomOneBedPrice"
                  className="form-control"
                  value={roomDetails.sharedRoomOneBedPrice}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="totalBeds" className="form-label">
                  Total Beds
                </label>
                <input
                  type="number"
                  id="totalBeds"
                  name="totalBeds"
                  className="form-control"
                  value={roomDetails.totalBeds}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="sharedBedsNos" className="form-label">
                  Shared Beds Numbers
                </label>
                <input
                  type="text"
                  id="sharedBedsNos"
                  name="sharedBedsNos"
                  className="form-control"
                  value={roomDetails.sharedBedsNos}
                  onChange={handleChange}
                  required
                />
                <small className="text-success">
                  e.g: roomNo-BedNo enter like 102-1,102-2,102-3,102-4,102-5
                </small>
              </div>
            </>
          )}

          <button type="submit" className="btn bg-color custom-bg-text w-100">
            Add Room
          </button>
        </form>
      </div>
      <ToastContainer />
    </div>
  );
};

export default AddPropertyRoomForm;
