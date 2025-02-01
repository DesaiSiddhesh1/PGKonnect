import { useState, useEffect } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import { useNavigate } from "react-router-dom";

const AddPropertyForm = () => {
  const [locations, setLocations] = useState([]);

  let navigate = useNavigate();
  const owner = JSON.parse(sessionStorage.getItem("active-owner"));
  const owner_jwtToken = sessionStorage.getItem("owner-jwtToken");

  const retrieveAllLocation = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/location/fetch/all"
    );
    return response.data;
  };

  useEffect(() => {
    const getAllLocation = async () => {
      const resLocation = await retrieveAllLocation();
      if (resLocation) {
        setLocations(resLocation.locations);
      }
    };

    getAllLocation();
  }, []);

  const [selectedImage, setSelectImage] = useState(null);

  const [propertyRequest, setPropertyRequest] = useState({
    name: "",
    description: "",
    address: "",
    locationId: "",
    ownerId: owner.id,
  });

  const handleInput = (e) => {
    setPropertyRequest({ ...propertyRequest, [e.target.name]: e.target.value });
  };

  const saveProperty = (e) => {
    e.preventDefault();
    if (propertyRequest === null) {
      toast.error("invalid input!!!", {
        position: "top-center",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });

      return;
    }

    const formData = new FormData();
    formData.append("name", propertyRequest.name);
    formData.append("description", propertyRequest.description);
    formData.append("address", propertyRequest.address);
    formData.append("locationId", propertyRequest.locationId);
    formData.append("image", selectedImage);
    formData.append("ownerId", owner.id);

    axios
      .post(
        "http://localhost:8080/api/property/add",
        formData
        // , {
        //   headers: {
        //     Authorization: "Bearer " + employer_jwtToken, // Replace with your actual JWT token
        //   },
        // }
      )
      .then((resp) => {
        let response = resp.data;

        if (response.success) {
          toast.success(response.responseMessage, {
            position: "top-center",
            autoClose: 1000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });

          setTimeout(() => {
            navigate("/home");
          }, 2000); // Redirect after 3 seconds
        } else if (!response.success) {
          toast.error(response.responseMessage, {
            position: "top-center",
            autoClose: 1000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          setTimeout(() => {
            window.location.reload(true);
          }, 2000); // Redirect after 3 seconds
        } else {
          toast.error("It Seems Server is down!!!", {
            position: "top-center",
            autoClose: 1000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          // setTimeout(() => {
          //   window.location.reload(true);
          // }, 2000); // Redirect after 3 seconds
        }
      })
      .catch((error) => {
        console.error(error);
        toast.error("It seems server is down", {
          position: "top-center",
          autoClose: 1000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        // setTimeout(() => {
        //   window.location.reload(true);
        // }, 2000); // Redirect after 3 seconds
      });
  };

  return (
    <div>
      <div class="mt-2 d-flex aligns-items-center justify-content-center mb-4">
        <div class="card form-card custom-bg" style={{ width: "60rem" }}>
          <div className="container-fluid">
            <div
              className="card-header bg-color custom-bg-text mt-2 text-center"
              style={{
                borderRadius: "1em",
                height: "45px",
              }}
            >
              <h5 class="card-title">Add Property</h5>
            </div>
            <div class="card-body text-color">
              <form className="row g-3">
                <div className="col-md-6 mb-3">
                  <label htmlFor="title" className="form-label">
                    <b>Property Name</b>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="name"
                    name="name"
                    onChange={handleInput}
                    value={propertyRequest.name}
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label htmlFor="description" className="form-label">
                    <b>Property Description</b>
                  </label>
                  <textarea
                    type="text"
                    className="form-control"
                    id="description"
                    name="description"
                    onChange={handleInput}
                    value={propertyRequest.description}
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label">
                    <b>Property Location</b>
                  </label>

                  <select
                    name="locationId"
                    onChange={handleInput}
                    className="form-control"
                  >
                    <option value="">Select Location</option>

                    {locations.map((location) => {
                      return (
                        <option value={location.id}> {location.name} </option>
                      );
                    })}
                  </select>
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label">
                    <b>Address</b>
                  </label>
                  <textarea
                    type="text"
                    className="form-control"
                    name="address"
                    onChange={handleInput}
                    value={propertyRequest.address}
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label for="formFile" class="form-label">
                    <b> Select Property Image</b>
                  </label>
                  <input
                    class="form-control"
                    type="file"
                    id="image"
                    name="image"
                    onChange={(e) => setSelectImage(e.target.files[0])}
                    required
                  />
                </div>

                <div className="d-flex aligns-items-center justify-content-center mb-2">
                  <button
                    type="submit"
                    class="btn bg-color custom-bg-text"
                    onClick={saveProperty}
                  >
                    <b> Add Property</b>
                  </button>
                  <ToastContainer />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddPropertyForm;
