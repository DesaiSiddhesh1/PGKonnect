import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import { Link } from "react-router-dom";

const ViewPendingProperty = () => {
  const [properties, setProperties] = useState([]);

  const admin_jwtToken = sessionStorage.getItem("admin-jwtToken");

  let navigate = useNavigate();

  useEffect(() => {
    const getProperties = async () => {
      const res = await retrieveProperties();
      if (res) {
        setProperties(res.properties);
      }
    };

    getProperties();
  }, []);

  const retrieveProperties = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/property/fetch/all?status=Pending",
      {
        headers: {
          Authorization: "Bearer " + admin_jwtToken, // Replace with your actual JWT token
        },
      }
    );
    console.log(response.data);
    return response.data;
  };

  const updateProperty = (propertyId, status, e) => {
    fetch(
      "http://localhost:8080/api/property/update/status?propertyId=" +
        propertyId +
        "&status=" +
        status,
      {
        method: "PUT",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: "Bearer " + admin_jwtToken,
        },
      }
    )
      .then((result) => {
        result.json().then((res) => {
          if (res.success) {
            toast.success(res.responseMessage, {
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
          } else if (!res.success) {
            toast.error(res.responseMessage, {
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
            }, 1000); // Redirect after 3 seconds
          }
        });
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
        setTimeout(() => {
          window.location.reload(true);
        }, 1000); // Redirect after 3 seconds
      });
  };

  return (
    <div className="mt-3">
      <div
        className="card form-card ms-2 me-2 mb-5 shadow-lg"
        style={{
          height: "45rem",
        }}
      >
        <div
          className="card-header custom-bg-text text-center bg-color"
          style={{
            borderRadius: "1em",
            height: "50px",
          }}
        >
          <h2>Pending Properties</h2>
        </div>
        <div
          className="card-body"
          style={{
            overflowY: "auto",
          }}
        >
          <div className="table-responsive">
            <table className="table table-hover text-color text-center">
              <thead className="table-bordered border-color bg-color custom-bg-text">
                <tr>
                  <th scope="col">Property</th>
                  <th scope="col">Name</th>
                  <th scope="col">Description</th>
                  <th scope="col">Location</th>
                  <th scope="col">Address</th>
                  <th scope="col">Owner</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {properties.map((property) => {
                  return (
                    <tr>
                      <td>
                        <img
                          src={
                            "http://localhost:8080/api/property/" +
                            property.image
                          }
                          class="img-fluid"
                          alt="food_pic"
                          style={{
                            maxWidth: "90px",
                          }}
                        />
                      </td>
                      <td>
                        <b>{property.name}</b>
                      </td>
                      <td>
                        <b>{property.description}</b>
                      </td>
                      <td>
                        <b>{property.location.name}</b>
                      </td>
                      <td>
                        <b>{property.address}</b>
                      </td>
                      <td>
                        <Link
                          to={`/user/owner/${property.owner.id}/profile/detail`}
                        >
                          <b>
                            {property.owner.firstName +
                              " " +
                              property.owner.lastName}
                          </b>
                        </Link>
                      </td>

                      <td>
                        <button
                          onClick={() =>
                            updateProperty(property.id, "Rejected")
                          }
                          className="btn btn-sm bg-danger text-white ms-2"
                        >
                          Reject
                        </button>

                        <button
                          onClick={() => updateProperty(property.id, "Active")}
                          className="btn btn-sm bg-success text-white ms-2"
                        >
                          Approve
                        </button>

                        <ToastContainer />
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewPendingProperty;
