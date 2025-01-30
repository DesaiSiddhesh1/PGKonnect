import pg_list from "./../images/list_pg.png";
import Carousel from "./Carousel";
import hotel_book from "./../images/hotel_book_customer.png";
import secure_payment from "./../images/secure_payment.png";
import { Link } from "react-router-dom";

const HomePage = () => {
    return (
        <div>
            {/* Hero Section */}

            <Carousel />

            <div className="container text-center mt-5">
                <h1 className="display-4">Find Your Perfect PG Accommodation</h1>
                <p className="lead">
                    A hassle-free platform to browse, book, and manage properties in just
                    a few clicks.
                </p>
                <Link
                    to="/property/explore"
                    className="btn btn-lg bg-color custom-bg-text mt-4"
                >
                    Explore Now
                </Link>
            </div>

            {/* Features Section */}
            <section className="features-section py-5">
                <div className="container">
                    <div className="row text-center">
                        <div className="col-md-4">
                            <img
                                src={pg_list}
                                alt="Feature 1"
                                className="img-fluid mb-3"
                                style={{
                                    maxHeight: "150px",
                                }}
                            />
                            <h3>Easy Property Listing</h3>
                            <p>
                                Property owners can effortlessly list their properties and
                                manage bookings with ease.
                            </p>
                        </div>
                        <div className="col-md-4">
                            <img
                                src={hotel_book}
                                alt="Feature 2"
                                className="img-fluid mb-3"
                                style={{
                                    maxHeight: "150px",
                                }}
                            />
                            <h3>Convenient Room Booking</h3>
                            <p>
                                Guests can browse and book rooms that suit their preferences
                                without any hassle.
                            </p>
                        </div>
                        <div className="col-md-4">
                            <img
                                src={secure_payment}
                                alt="Feature 3"
                                className="img-fluid mb-3"
                                style={{
                                    maxHeight: "150px",
                                }}
                            />
                            <h3>Secure Payment</h3>
                            <p>
                                Experience secure transactions with our integrated payment
                                gateway for seamless bookings.
                            </p>
                        </div>
                    </div>
                </div>
            </section>

            <section
                className="cta-section text-white text-center py-5"
                style={{
                    backgroundImage: "url('/path-to-cta-background.jpg')",
                    backgroundSize: "cover",
                }}
            >
                <div className="container text-color">
                    <h2>Ready to Find Your Next PG?</h2>
                    <p className="lead">
                        Join hundreds of happy customers who have found their perfect
                        accommodation with us.
                    </p>
                    <Link
                        to="/user/guest/register"
                        className="btn btn-lg bg-color custom-bg-text"
                    >
                        Get Started
                    </Link>
                </div>
            </section>
        </div>
    );
};

export default HomePage;
