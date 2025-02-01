import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import PropertyCard from "./PropertyCard";
// import CourseCard from "../CourseComponent/CourseCard";

const PropertyExporePage = () => {
  const navigate = useNavigate();
  const [locations, setLocations] = useState([]);

  const [propertyName, setPropertyName] = useState("");
  const [locationId, setLocationId] = useState("");
  const [tempPropertyName, setTempPropertyName] = useState("");
  const [tempLocationId, setTempLocationId] = useState("");
  const [properties, setProperties] = useState([]);

  const retrieveAllLocations = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/location/fetch/all"
    );
    return response.data;
  };

  useEffect(() => {
    const getallProperties = async () => {
      const allProperties = await retrieveAllProperties();
      if (allProperties) {
        setProperties(allProperties.properties);
      }
    };

    const getSearchedProperties = async () => {
      const allProperties = await searchProperties();
      if (allProperties) {
        setProperties(allProperties.properties);
      }
    };

    const getAllLocations = async () => {
      const resLocation = await retrieveAllLocations();
      if (resLocation) {
        setLocations(resLocation.locations);
      }
    };

    if (locationId !== "" || propertyName !== "") {
      getSearchedProperties();
    } else {
      getallProperties();
    }

    getAllLocations();
  }, [locationId, propertyName]);

  const retrieveAllProperties = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/property/fetch/all?status=Active"
    );
    return response.data;
  };

  const searchProperties = async () => {
    if (propertyName !== "") {
      const response = await axios.get(
        "http://localhost:8080/api/property/fetch/name-wise?status=Active&name=" +
          propertyName
      );

      return response.data;
    } else if (locationId !== "" || locationId !== "0") {
      const response = await axios.get(
        "http://localhost:8080/api/property/fetch/location-wise?status=Active&locationId=" +
          locationId
      );
      return response.data;
    }
  };

  const searchPropertyByName = (e) => {
    e.preventDefault();
    setPropertyName(tempPropertyName);

    setTempPropertyName("");
    setLocationId("");
  };

  const searchPropertyByLocation = (e) => {
    e.preventDefault();
    setLocationId(tempLocationId);

    setTempLocationId("");
    setPropertyName("");
  };

  return (
    <div className="container-fluid mb-2">
      <h5 className="text-color-second text-center mt-3">
        Search Properties here..!!
      </h5>

      <div className="d-flex aligns-items-center justify-content-center">
        <div className="row">
          <div className="col-auto">
            <div className="mt-3">
              <form class="row g-3">
                <div class="col-auto">
                  <input
                    type="text"
                    className="form-control"
                    id="city"
                    name="propertyName"
                    onChange={(e) => setTempPropertyName(e.target.value)}
                    value={tempPropertyName}
                    placeholder="Search Properties here..."
                  />
                </div>

                <div class="col-auto">
                  <button
                    type="submit"
                    class="btn bg-color text-color mb-3"
                    onClick={searchPropertyByName}
                  >
                    Search
                  </button>
                </div>
              </form>
            </div>
          </div>
          <div className="col">
            <div className="mt-3">
              <form class="row g-3">
                <div class="col-auto">
                  <select
                    name="tempCourseCategoryId"
                    onChange={(e) => setTempLocationId(e.target.value)}
                    className="form-control"
                    required
                  >
                    <option value="">Select Property Location</option>

                    {locations.map((location) => {
                      return (
                        <option value={location.id}> {location.name} </option>
                      );
                    })}
                  </select>
                </div>

                <div class="col-auto">
                  <button
                    type="submit"
                    class="btn bg-color text-color mb-3"
                    onClick={searchPropertyByLocation}
                  >
                    Search
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>

      <div className="col-md-12 mt-3 mb-5 ">
        <div className="row row-cols-1 row-cols-md-4 g-4">
          {properties.map((property) => {
            return <PropertyCard item={property} key={property.id} />;
          })}
        </div>
      </div>
    </div>
  );
};

export default PropertyExporePage;
