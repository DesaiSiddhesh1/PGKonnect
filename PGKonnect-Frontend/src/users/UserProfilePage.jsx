import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

const UserProfilePage = () => {
  const { userId } = useParams();
  const navigate = useNavigate();

  const [user, setUser] = useState({
    id: "",
    firstName: "",
    lastName: "",
    shopName: "",
    emailId: "",
    phoneNo: "",
    role: "",
    address: {
      id: "",
      street: "",
      city: "",
      pincode: "",
    },
    walletAmount: "",
    status: "",
    guest: null,
  });

  useEffect(() => {
    const getUser = async () => {
      const userRes = await retrieveUser();
      if (userRes) {
        setUser(userRes.users[0]);
      }
    };

    getUser();
  }, [userId]);

  const retrieveUser = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/user/fetch/user-id?userId=" + userId
    );
    return response.data;
  };

  return (
    <div className="container-fluid mb-2">
      <div className="container-fluid mb-2">
        <div className="d-flex align-items-center justify-content-center ms-5 mt-1 me-5 mb-3">
          <div
            className="h-100"
            style={{
              width: "900px",
            }}
          >
            <div className="card-body text-color">
              <div className="text-center header-logo-color">
                <h3 className="mt3">My Profile</h3>
              </div>
              <div className="row mt-4">
                <div className="col-md-4">
                  <p className="mb-2">
                    <b>First Name:</b> {user.firstName}
                  </p>
                </div>
                <div className="col-md-4">
                  <p className="mb-2">
                    <b>Last Name:</b> {user.lastName}
                  </p>
                </div>
                <div className="col-md-4">
                  <p className="mb-2">
                    <b>Email Id:</b> {user.emailId}
                  </p>
                </div>
              </div>
              <div className="row mt-4">
                {user.role !== "Admin" && (
                  <div className="col-md-4">
                    <p className="mb-2">
                      <b>Contact:</b> {user.phoneNo}
                    </p>
                  </div>
                )}
                {user.role !== "Admin" && (
                  <div className="col-md-4">
                    <p className="mb-2">
                      <b>Address:</b>{" "}
                      {user.address.street +
                        ", " +
                        user.address.city +
                        ", " +
                        user.address.pincode}
                    </p>
                  </div>
                )}
                <div className="col-md-4">
                  <p className="mb-2">
                    <b>Wallet Amount:</b> &#8377; {user.walletAmount}
                  </p>
                </div>
                <div className="col-md-4">
                  <p className="mb-2">
                    <b>Role:</b> {user.role}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfilePage;
