import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const PropertyRoomBookingPage = () => {
  const guest = JSON.parse(sessionStorage.getItem("active-guest"));
  const guest_jwtToken = sessionStorage.getItem("guest-jwtToken");

  const { propertyId, roomNo } = useParams();
  const navigate = useNavigate(); // Hook to navigate back
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

  const currentYear = new Date().getFullYear();
  const years = Array.from(
    { length: 2030 - currentYear + 1 },
    (_, i) => currentYear + i
  );

  // Find the selected room based on roomNo
  const room = property.rooms.find((r) => r.roomNo === roomNo);

  const [bookingDetails, setBookingDetails] = useState({
    startMonth: "",
    startYear: "",
    endMonth: "",
    endYear: "",
    selectedBed: 0,
    propertyId: propertyId,
    roomId: room ? room.id : "",
    roomType: room ? room.type : "",
  });

  const handleBookingChange = (e) => {
    setBookingDetails({ ...bookingDetails, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    const getProperty = async () => {
      const fetchPropertyResponse = await retrieveProperty();
      if (fetchPropertyResponse) {
        setProperty(fetchPropertyResponse.properties[0]);
      }
    };
    getProperty();
  }, [propertyId]);

  const retrieveProperty = async () => {
    const response = await axios.get(
      `http://localhost:8080/api/property/fetch/id-wise?propertyId=${propertyId}`
    );
    console.log(response.data);
    return response.data;
  };

  if (!room) {
    return <p>Room not found</p>;
  }

  const checkRoomAvailabity = (e) => {
    e.preventDefault();

    if (
      bookingDetails.startMonth === "" ||
      bookingDetails.startYear === "" ||
      bookingDetails.endYear === "" ||
      bookingDetails.endMonth === ""
    ) {
      alert("Please select the Time Range to check Room Availabity!!!");
    } else {
      let startDate =
        bookingDetails.startMonth + " " + bookingDetails.startYear;
      let endDate = bookingDetails.endMonth + " " + bookingDetails.endYear;
      let roomId = room.id;
      let roomType = room.type;

      fetch(
        `http://localhost:8080/api/property/booking/availability/check?propertyId=${bookingDetails.propertyId}&roomId=${roomId}&bedId=${bookingDetails.selectedBed}&roomType=${roomType}&startDate=${startDate}&endDate=${endDate}`,
        {
          method: "GET",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            //    Authorization: "Bearer " + jwtToken,
          },
        }
      )
        .then((result) => {
          console.log("result", result);
          result.json().then((res) => {
            if (res.success) {
              toast.success(res.responseMessage, {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });
            } else if (!res.success) {
              toast.error(res.responseMessage, {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });
            } else {
              toast.error("It seems server is down", {
                position: "top-center",
                autoClose: 1000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });
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
        });
    }
  };

  const bookRoom = (e) => {
    e.preventDefault();

    if (!guest || guest == null) {
      alert("Please Login as Guest to book the room!!!");
    } else if (
      bookingDetails.startMonth === "" ||
      bookingDetails.startYear === "" ||
      bookingDetails.endYear === "" ||
      bookingDetails.endMonth === ""
    ) {
      alert("Please select the Time Range to check Room Availabity!!!");
    } else {
      let startDate =
        bookingDetails.startMonth + " " + bookingDetails.startYear;
      let endDate = bookingDetails.endMonth + " " + bookingDetails.endYear;
      let roomId = room.id;
      let roomType = room.type;

      let data = {
        guestId: guest.id,
        propertyId: property.id,
        roomId: roomId,
        type: roomType,
        bedId: bookingDetails.selectedBed,
        startDate: startDate,
        endDate: endDate,
      };

      fetch(`http://localhost:8080/api/property/booking/add`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          //    Authorization: "Bearer " + jwtToken,
        },
        body: JSON.stringify(data),
      })
        .then((result) => {
          console.log("result", result);
          result.json().then((res) => {
            if (res.success) {
              toast.success(res.responseMessage, {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });
            } else if (!res.success) {
              toast.error(res.responseMessage, {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });
            } else {
              toast.error("It seems server is down", {
                position: "top-center",
                autoClose: 1000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });
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
        });
    }
  };

  return (
    <div className="mb-3">
      <div className="col ml-5 mt-3 ms-5 me-5">
        {/* Company and Employer Details Card */}
        <div className="card rounded-card h-100 shadow-lg ">
          <h2 className="card-title text-center text-color ms-4 mb-4">
            Room Booking Page
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
          </div>

          <div className="row g-0 mt-3">
            {/* Left side - Selected Room Details */}
            <div className="col-md-6">
              <div className="card-body text-dark">
                <h4 className="card-title">
                  Room No: {room.roomNo} ({room.type} Room)
                </h4>
                <p>
                  <b>Description:</b> {room.description}
                </p>
                <p>
                  <b>Room For:</b> {room.roomFor}
                </p>
                <p>
                  <b>Price:</b> ₹
                  {room.type === "Private"
                    ? room.privateRoomPrice
                    : room.sharedRoomOneBedPrice}
                </p>

                {/* Shared Beds Info */}
                {room.type === "Shared" && room.sharedRoomBeds.length > 0 && (
                  <div>
                    <p>
                      <b>Shared Beds:</b>
                    </p>
                    <select
                      className="form-select"
                      name="selectedBed"
                      value={bookingDetails.selectedBed}
                      onChange={handleBookingChange}
                    >
                      <option value="">Select Bed</option>
                      {room.sharedRoomBeds.map((bed) => (
                        <option key={bed.id} value={bed.id}>
                          Bed {bed.bedNo}
                        </option>
                      ))}
                    </select>
                  </div>
                )}
              </div>
            </div>
            {/* Right side - Booking Form */}
            <div className="col-md-6">
              <div className="card-body">
                <h4 className="card-title mb-4">Booking Details</h4>

                <div className="row">
                  <div className="col-md-6">
                    <label>Start Month</label>
                    <select
                      className="form-select"
                      name="startMonth"
                      value={bookingDetails.startMonth}
                      onChange={handleBookingChange}
                    >
                      <option value="">Select Month</option>
                      <option value="January">January</option>
                      <option value="February">February</option>
                      <option value="March">March</option>
                      <option value="April">April</option>
                      <option value="May">May</option>
                      <option value="June">June</option>
                      <option value="July">July</option>
                      <option value="August">August</option>
                      <option value="September">September</option>
                      <option value="October">October</option>
                      <option value="November">November</option>
                      <option value="December">December</option>
                    </select>
                  </div>

                  <div className="col-md-6">
                    <label>Start Year</label>
                    <select
                      id="startYear"
                      className="form-select"
                      name="startYear"
                      onChange={handleBookingChange}
                    >
                      <option value="">Select Year</option>
                      <option value="2024">2024</option>
                      <option value="2025">2025</option>
                      <option value="2026">2026</option>
                      <option value="2027">2027</option>
                      <option value="2028">2028</option>
                      <option value="2029">2029</option>
                      <option value="2030">2030</option>
                    </select>
                  </div>
                </div>

                <div className="row mt-3">
                  <div className="col-md-6">
                    <label>End Month</label>
                    <select
                      className="form-select"
                      name="endMonth"
                      onChange={handleBookingChange}
                    >
                      <option value="">Select Month</option>
                      <option value="January">January</option>
                      <option value="February">February</option>
                      <option value="March">March</option>
                      <option value="April">April</option>
                      <option value="May">May</option>
                      <option value="June">June</option>
                      <option value="July">July</option>
                      <option value="August">August</option>
                      <option value="September">September</option>
                      <option value="October">October</option>
                      <option value="November">November</option>
                      <option value="December">December</option>
                    </select>
                  </div>

                  <div className="col-md-6">
                    <label>End Year</label>
                    <select
                      id="endYear"
                      className="form-select"
                      name="endYear"
                      onChange={handleBookingChange}
                    >
                      <option value="">Select Year</option>
                      <option value="2024">2024</option>
                      <option value="2025">2025</option>
                      <option value="2026">2026</option>
                      <option value="2027">2027</option>
                      <option value="2028">2028</option>
                      <option value="2029">2029</option>
                      <option value="2030">2030</option>
                    </select>
                  </div>
                </div>

                {/* Submit Booking Button and Check Room Availability Button */}
                <div className="mt-4 d-flex justify-content-between">
                  <button
                    className="btn bg-color custom-bg-text"
                    onClick={bookRoom}
                  >
                    Book Room
                  </button>
                  <button
                    className="btn btn-outline-dark ms-2"
                    onClick={checkRoomAvailabity}
                  >
                    Check Room Availability
                  </button>
                  <ToastContainer />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PropertyRoomBookingPage;
