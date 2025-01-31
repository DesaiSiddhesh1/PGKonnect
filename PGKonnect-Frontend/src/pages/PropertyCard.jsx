import { Link } from "react-router-dom";
import location from "../images/location.png";

const PropertyCard = (property) => {
    const descriptionToShow = (description, maxLength) => {
        if (description.length <= maxLength) {
            return description;
        } else {
            const truncatedText = description.substring(0, maxLength);
            return truncatedText + "...";
        }
    };

    return (
        <div className="col">
            <Link
                to={`/property/${property.item.id}/detail`}
                class="card propert-card rounded-card h-100 shadow-lg"
                style={{ textDecoration: "none" }}
            >
                <div style={{ position: "relative", maxWidth: "100%" }}>
                    <img
                        src={"http://localhost:8080/api/property/" + property.item.image}
                        className="card-img-top rounded d-block"
                        alt="img"
                        style={{
                            maxHeight: "270px",
                            maxWidth: "100%",
                            width: "auto",
                        }}
                    />
                </div>
                <div class="card-body text-color">
                    <b className="text-color-third">
                        <img
                            src={location}
                            height="30"
                            width="auto"
                            class="d-inline-block align-top me-2"
                            alt=""
                        />
                        {property.item.location.name}
                    </b>
                    <h5 className="card-title text-color mt-2">
                        <div>
                            <b>{property.item.name}</b>
                        </div>
                    </h5>
                    <p className="card-text">
                        {descriptionToShow(property.item.description, 60)}
                    </p>
                </div>
            </Link>
        </div>
    );
};
export default PropertyCard;