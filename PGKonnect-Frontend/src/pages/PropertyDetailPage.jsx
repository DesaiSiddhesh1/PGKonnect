import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import TourCarousel from "./PropertyCarousel";
import { Link } from "react-router-dom";

const PropertyDetailPage = () => {
  const { propertyId } = useParams();

  const guest = JSON.parse(sessionStorage.getItem("active-guest"));
  const guest_jwtToken = sessionStorage.getItem("guest-jwtToken");

  const owner = JSON.parse(sessionStorage.getItem("active-owner"));
  const owner_jwtToken = sessionStorage.getItem("owner-jwtToken");

  const navigate = useNavigate();

  const [property, setProperty] = useState({
    id: "",
    name: "",
    description: "",
    address: "",
    image: "",
    facilities: [
      {
        id: "",
        name: "",
        description: "",
      },
    ],
    owner: {
      id: "",
      firstName: "",
      lastName: "",
      emailId: "",
      phoneNo: "",
    },
    location: {
      id: "",
      name: "",
    },
    rooms: [
      {
        id: "",
        roomNo: "",
        description: "",
        roomFor: "",
        type: "",
        totalBeds: "",
        privateRoomPrice: "",
        sharedRoomOneBedPrice: "",
        sharedRoomBeds: [
          {
            id: "",
            bedNo: "",
          },
        ],
      },
    ],
  });

  useEffect(() => {
    const getProperty = async () => {
      const fetchPropertyResponse = await retrieveProperty();
      if (fetchPropertyResponse) {
        setProperty(fetchPropertyResponse.properties[0]);
      }
    };
    getProperty();
  }, []);

  const retrieveProperty = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/property/fetch/id-wise?propertyId=" +
        propertyId
    );
    console.log(response.data);
    return response.data;
  };

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const bookPropertyPage = (e) => {
    e.preventDefault();
    if (guest === null) {
      alert("Please login as guest to book an property!!!");
    } else {
      navigate("/property/booking/page", { state: property });
    }
  };

  return (
    <div className="mb-3">
      <div className="col ml-5 mt-3 ms-5 me-5">
        {/* Company and Employer Details Card */}
        <div className="card rounded-card h-100 shadow-lg ">
          <h2 className="card-title text-center text-color ms-4">
            Property Detail
          </h2>

          <div className="row g-0">
            {/* Left side - Company Details Card */}
            <div className="col-md-6">
              <div className="card-body">
                {/* Left side - Company Logo */}
                <div className="d-flex align-items-center justify-content-center">
                  <img
                    src={"http://localhost:8080/api/property/" + property.image}
                    className="d-block w-100"
                    alt="..."
                    style={{
                      height: "auto",
                      width: "100%",
                      borderRadius: "15px",
                      objectFit: "cover",
                    }}
                  />
                </div>
              </div>
            </div>

            {/* Right side - Employer Details Card */}
            <div className="col-md-6 text-dark">
              <div className="card-body text-color">
                <h3 className="card-title d-flex justify-content-between text-color-second">
                  <div>
                    <b>{property.name}</b>
                  </div>
                </h3>
                <p className="card-text text-dark">{property.description}</p>

                <b className="card-text">
                  <div className="col-md-4 d-flex justify-content-between">
                    <div>
                      <span className="text-dark">Location:</span>
                      <span className="text-color ms-2">
                        {property.location.name + " "}
                      </span>
                    </div>
                  </div>
                </b>
                <div className="row mt-3">
                  <div className="col-md-12">
                    <p className="mb-2">
                      <b>Property Address:</b>

                      <span className="text-color"> {property.address}</span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div className="row g-0">
            <div className="col-md-6 text-dark">
              <div className="card-body">
                <div className="text-left text-color">
                  <h4>Owner Detail</h4>
                </div>
                <div className="row mt-3">
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Owner Name:</b>

                      <span className="text-color">
                        {" "}
                        {property.owner.firstName}
                      </span>
                    </p>
                  </div>
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Last Name:</b>
                      <span className="text-color">
                        {" "}
                        {property.owner.lastName}
                      </span>
                    </p>
                  </div>
                </div>

                <div className="row mt-3">
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Contact No.:</b>

                      <span className="text-color">
                        {" "}
                        {property.owner.phoneNo}
                      </span>
                    </p>
                  </div>
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Email Id:</b>
                      <span className="text-color">
                        {" "}
                        {property.owner.emailId}
                      </span>
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <div className="col-md-6 text-dark">
              <div className="card-body">
                <div className="text-left text-color">
                  <h4>Facilities</h4>
                </div>

                {property.facilities && property.facilities.length > 0 ? (
                  property.facilities.map((facility, index) => (
                    <div className="row mt-3" key={index}>
                      <div className="col-md-6">
                        <p className="mb-2">
                          <b>Name:</b>
                          <span className="text-color"> {facility.name}</span>
                        </p>
                      </div>
                      <div className="col-md-6">
                        <p className="mb-2">
                          <b>Description:</b>
                          <span className="text-color">
                            {" "}
                            {facility.description}
                          </span>
                        </p>
                      </div>
                    </div>
                  ))
                ) : (
                  <p className="text-dark">No Facilities added</p>
                )}
              </div>
            </div>
          </div>

          <div className="row g-0">
            <div className="col-md-12 text-dark">
              <div className="card-body">
                <div className="text-center text-color">
                  <h4>Room Details</h4>
                </div>

                {/* Check if rooms exist */}
                {property.rooms && property.rooms.length > 0 ? (
                  <div className="row">
                    {property.rooms.map((room, index) => (
                      <div className="col-md-6 mb-4" key={index}>
                        {/* Wrap card inside Link */}
                        <Link
                          to={{
                            pathname: `/property/${property.id}/room/${room.roomNo}`,
                            state: { property }, // Pass the entire property object as state
                          }}
                          style={{ textDecoration: "none", color: "inherit" }}
                        >
                          <div className="card shadow-sm h-100 border-color shadow-lg">
                            <div className="card-body">
                              <h4 className="card-title text-center">
                                <b className="text-muted">Room No:</b>{" "}
                                {room.roomNo}
                              </h4>

                              <div className="row mt-3">
                                <div className="col-md-6">
                                  <p className="card-text">
                                    <b>Room For:</b> {room.roomFor}
                                  </p>
                                </div>
                                <div className="col-md-6">
                                  <p className="card-text">
                                    <b>Type:</b> {room.type}
                                  </p>
                                </div>
                              </div>

                              <div className="row">
                                <div className="col-md-6">
                                  <p className="card-text">
                                    <b>Room Price:</b> ₹
                                    {room.type === "Private"
                                      ? room.privateRoomPrice
                                      : room.sharedRoomOneBedPrice}
                                  </p>
                                </div>

                                {room.type === "Shared" && (
                                  <div className="col-md-6">
                                    <p className="card-text">
                                      <b>Total Beds:</b> {room.totalBeds}
                                    </p>
                                  </div>
                                )}
                              </div>

                              {/* Shared Bed Numbers */}
                              {room.type === "Shared" &&
                                room.sharedRoomBeds?.length > 0 && (
                                  <div className="row">
                                    <div className="col-md-12">
                                      <h6 className="mt-3">Shared Bed Nos.:</h6>
                                      <p className="card-text">
                                        {room.sharedRoomBeds
                                          .map((bed, bedIndex) => bed.bedNo)
                                          .join(", ")}
                                      </p>
                                    </div>
                                  </div>
                                )}
                            </div>
                          </div>
                        </Link>
                      </div>
                    ))}
                  </div>
                ) : (
                  <p className="text-dark">No Rooms available</p>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PropertyDetailPage;
