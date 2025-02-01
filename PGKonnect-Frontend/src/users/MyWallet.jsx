import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import axios from "axios";
//import Razorpay from "razorpay";

const MyWallet = () => {
  let navigate = useNavigate();
  const user = JSON.parse(sessionStorage.getItem("active-guest"));
  const customer_jwtToken = sessionStorage.getItem("guest-jwtToken");
  const [walletAmount, setWalletAmount] = useState(user.walletAmount);

  const [walletRequest, setWalletRequest] = useState({
    id: user.id,
    walletAmount: "",
  });

  const [fetchUserWallet, setFetchUserWallet] = useState({});

  walletRequest.userId = user.id;

  const handleInput = (e) => {
    setWalletRequest({ ...walletRequest, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    const getMyWallet = async () => {
      const userResponse = await retrieveMyWallet();
      if (userResponse) {
        setFetchUserWallet(userResponse.users[0]);
        setWalletAmount(userResponse.users[0].walletAmount);
      }
    };

    getMyWallet();
  }, []);

  const retrieveMyWallet = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/user/fetch/user-id?userId=" + user.id
    );

    return response.data;
  };

  useEffect(() => {
    const script = document.createElement("script");
    script.src = "https://checkout.razorpay.com/v1/checkout.js";
    document.body.appendChild(script);

    return () => {
      // Cleanup: remove the script from the DOM when the component is unmounted
      document.body.removeChild(script);
    };
  }, []);

  const addMoneyInWallet = (e) => {
    fetch("http://localhost:8080/api/user/update/wallet", {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(walletRequest),
    })
      .then((result) => {
        result.json().then((res) => {
          if (res.success) {
            console.log("Success Response");
            var options = res.razorPayRequest;
            console.log(options);

            // Add the handler function to the responseData object
            options.handler = function (response) {
              console.log(response.razorpay_payment_id);
              console.log(response.razorpay_order_id);
              console.log(response.razorpay_signature);
              response.razorpay_order_id = options.orderId;

              fetch("http://localhost:8080/api/user/razorpPay/response", {
                method: "PUT",
                headers: {
                  Accept: "application/json",
                  "Content-Type": "application/json",
                },
                body: JSON.stringify(response),
              })
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
                      }, 1000);
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
            console.log("final json after adding handler function");
            console.log(options);

            // Check if Razorpay is available in the window object
            if (window.Razorpay) {
              console.log("Rzaorpay is defined");
              const rzp1 = new window.Razorpay(options);
              rzp1.on("payment.failed", function (response) {
                console.log(response.error.code);
                console.log(response.error.description);
                console.log(response.error.source);
                console.log(response.error.step);
                console.log(response.error.reason);
                console.log(response.error.metadata.order_id);
                console.log(response.error.metadata.payment_id);

                response.razorpay_order_id = options.orderId;

                fetch("http://localhost:8080/api/user/razorpPay/response", {
                  method: "PUT",
                  headers: {
                    Accept: "application/json",
                    "Content-Type": "application/json",
                  },
                  body: JSON.stringify(response),
                })
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
                        }, 1000);
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
              });
              rzp1.open();
            } else {
              toast.error("Payment Gateway Internal Server Issue", {
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
    e.preventDefault();
  };

  return (
    <div>
      <div className="mt-2 mb-4 d-flex aligns-items-center justify-content-center">
        <div className="card form-card custom-bg" style={{ width: "25rem" }}>
          <div
            className="card-header bg-color text-center custom-bg-text mb-3"
            style={{
              borderRadius: "1em",
              height: "50px",
            }}
          >
            <h3>My Wallet</h3>
          </div>
          <h4 className="ms-3 text-color text-center">
            Wallet Balance: &#8377; {walletAmount}
          </h4>

          <hr />

          <div
            className="card-header bg-color text-center custom-bg-text"
            style={{
              borderRadius: "1em",
              height: "50px",
            }}
          >
            <h4 className="card-title">Add Money In Wallet</h4>
          </div>
          <div className="card-body">
            <form>
              <div className="mb-3 text-color">
                <label for="emailId" class="form-label">
                  <b>Amount</b>
                </label>
                <input
                  type="text"
                  className="form-control"
                  name="walletAmount"
                  onChange={handleInput}
                  value={walletRequest.walletAmount}
                  required
                />
              </div>
              <div className="d-flex aligns-items-center justify-content-center mb-2">
                <button
                  type="submit"
                  className="btn bg-color custom-bg-text"
                  onClick={addMoneyInWallet}
                >
                  Update Wallet
                </button>
                <ToastContainer />
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyWallet;
