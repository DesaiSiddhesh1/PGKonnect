import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import { Link } from "react-router-dom";
import { Button, Modal } from "react-bootstrap";

const ViewGuestPendingPropertyBookings = () => {
  const [showModal, setShowModal] = useState(false);

  const handleClose = () => setShowModal(false);
  const handleShow = () => setShowModal(true);

  const [payments, setPayments] = useState([]);

  const [bookings, setBookings] = useState([
    {
      guest: { id: "", firstName: "" },
      property: {
        owner: { id: "", firstName: "" },
        name: "",
        image: "",
        location: { name: "" },
      },
      roomDetail: {
        roomNo: "",
        type: "",
        privateRoomPrice: "",
        sharedRoomOneBedPrice: "",
      },
      bedDetail: { bedNo: "" },
      payments: {
        paymentId: "",
        forMonth: "",
        amountPaid: "",
        paymentType: "",
        paymentTime: "",
      },
    },
  ]);

  const guest = JSON.parse(sessionStorage.getItem("active-guest"));
  const guest_jwtToken = sessionStorage.getItem("guest-jwtToken");

  let navigate = useNavigate();

  useEffect(() => {
    const getBookings = async () => {
      const res = await retrieveBookings();
      if (res) {
        setBookings(res.bookings);
      }
    };

    getBookings();
  }, []);

  const retrieveBookings = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/property/booking/fetch/guest-wise?status=PENDING&guestId=" +
        guest.id,
      {
        headers: {
          Authorization: "Bearer " + guest_jwtToken, // Replace with your actual JWT token
        },
      }
    );
    console.log(response.data);
    return response.data;
  };

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const showPaymentDetailViewModal = (e, payments) => {
    setPayments(payments);
    handleShow();
  };

  const updateBookingStatus = (bookingId, status) => {
    fetch(
      "http://localhost:8080/api/property/booking/update/status?bookingId=" +
        bookingId +
        "&status=" +
        status,
      {
        method: "PUT",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: "Bearer " + guest_jwtToken,
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
            // setTimeout(() => {
            //   window.location.reload(true);
            // }, 1000); // Redirect after 3 seconds
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
        // setTimeout(() => {
        //   window.location.reload(true);
        // }, 1000); // Redirect after 3 seconds
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
          <h2>Pending Bookings</h2>
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
                  <th scope="col">Booking Id</th>
                  <th scope="col">Property</th>
                  <th scope="col">Name</th>
                  <th scope="col">Location</th>
                  <th scope="col">Owner</th>
                  <th scope="col">Customer</th>
                  <th scope="col">Room Type</th>
                  <th scope="col">Room No</th>
                  <th scope="col">Amount To Pay(per Mnth)</th>
                  <th scope="col">Start Date</th>
                  <th scope="col">End Date</th>
                  <th scope="col">Total Stay(in Mnths)</th>
                  <th scope="col">Action</th>
                  {/* <th scope="col">Payments</th> */}
                </tr>
              </thead>
              <tbody>
                {bookings.map((booking) => {
                  return (
                    <tr>
                      <td>
                        <b>{booking.bookingId}</b>
                      </td>
                      <td>
                        <img
                          src={
                            "http://localhost:8080/api/property/" +
                            booking.property.image
                          }
                          class="img-fluid"
                          alt="food_pic"
                          style={{
                            maxWidth: "90px",
                          }}
                        />
                      </td>
                      <td>
                        <b>{booking.property.name}</b>
                      </td>
                      <td>
                        <b>{booking.property.location.name}</b>
                      </td>

                      <td>
                        <Link
                          to={`/user/owner/${booking.property.owner.id}/profile/detail`}
                        >
                          <b>{booking.property.owner.firstName}</b>
                        </Link>
                      </td>
                      <td>
                        <Link
                          to={`/user/owner/${booking.guest.id}/profile/detail`}
                        >
                          <b>{booking.guest.firstName}</b>
                        </Link>
                      </td>
                      <td>
                        <b>{booking.roomType}</b>
                      </td>
                      <td>
                        <b>
                          {booking.roomType === "Private"
                            ? booking.roomDetail.roomNo
                            : booking.roomDetail.roomNo +
                              " - [" +
                              booking.bedDetail.bedNo +
                              "]"}
                        </b>
                      </td>
                      <td>
                        <b>
                          {booking.roomType === "Private"
                            ? booking.roomDetail.privateRoomPrice
                            : booking.roomDetail.sharedRoomOneBedPrice}
                        </b>
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(booking.startDate)}</b>
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(booking.endDate)}</b>
                      </td>
                      <td>
                        <b>{booking.totalStayMonth}</b>
                      </td>

                      <td>
                        <button
                          onClick={(e) =>
                            updateBookingStatus(booking.id, "CANCELLED")
                          }
                          className="btn btn-sm bg-danger text-white ms-2"
                        >
                          Cancel
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

export default ViewGuestPendingPropertyBookings;
