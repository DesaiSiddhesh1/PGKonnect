import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import { Link } from "react-router-dom";

const ViewRejectedProperty = () => {
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
      "http://localhost:8080/api/property/fetch/all?status=Rejected",
      {
        headers: {
          Authorization: "Bearer " + admin_jwtToken, // Replace with your actual JWT token
        },
      }
    );
    console.log(response.data);
    return response.data;
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
          <h2>Rejected Properties</h2>
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

export default ViewRejectedProperty;
