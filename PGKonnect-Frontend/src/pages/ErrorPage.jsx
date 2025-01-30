import React from "react";

const ErrorPage = () => {
    return (
        <div className="container my-5">
            <div className="row">
                <div className="col-12">
                    <h1 className="text-center text-danger mb-4">404</h1>
                </div>
                <div className="col-12 col-md-10 offset-md-1">
                    <h2 className="text-center mb-4">Oops! Page Not Found</h2>
                    <p className="lead text-center">
                        The page you are looking for might have been removed or is temporarily unavailable
                    </p>

                    <div className="text-center">
                        <a href="/" className="btn btn-lg bg-color custom-bg-text mt-4">
                            Go Back to Home
                        </a>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ErrorPage;