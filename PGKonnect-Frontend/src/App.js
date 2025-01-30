import "./App.css";
import SideBar from "./components/Sidebar/SideBar";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import Dashboard from "./pages/ErrorPage";
import Users from "./pages/Users";
import FileManager from "./pages/ContactUsPage";
import AboutUsPage from "./pages/AboutUsPage";
import Order from "./pages/Order";
import Saved from "./pages/Saved";
import Setting from "./pages/Setting";
import UserLoginForm from "./users/UserLoginForm";
import UserRegister from "./users/UserRegister";
import ContactUsPage from "./pages/ContactUsPage";
import HomePage from "./pages/HomePage";
import ErrorPage from "./pages/ErrorPage";
import PropertyExporePage from "./pages/PropertyExporePage";
import PropertyDetailPage from "./pages/PropertyDetailPage";
import PropertyRoomBookingPage from "./pages/PropertyRoomBookingPage";
import UserLogout from "./pages/UserLogout";
import UserProfilePage from "./users/UserProfilePage";
import MyWallet from "./users/MyWallet";
import AddPropertyForm from "./pages/AddPropertyForm";
import ViewPendingProperty from "./pages/ViewPendingProperty";
import ViewRejectedProperty from "./pages/ViewRejectedProperty";
import ViewApprovedProperty from "./pages/ViewApprovedProperty";
import ViewOwnerApprovedProperty from "./pages/ViewOwnerApprovedProperty";
import ViewOwnerPendingProperty from "./pages/ViewOwnerPendingProperty";
import ViewOwnerRejectedProperty from "./pages/ViewOwnerRejectedProperty";
import ViewOwnerPendingPropertyBookings from "./pages/ViewOwnerPendingPropertyBookings";
import ViewOwnerApprovedPropertyBookings from "./pages/ViewOwnerApprovedPropertyBookings";
import ViewOwnerRejectedPropertyBookings from "./pages/ViewOwnerRejectedPropertyBookings";

import ViewAdminPendingPropertyBookings from "./pages/ViewAdminPendingPropertyBookings";
import ViewAdminApprovedPropertyBookings from "./pages/ViewAdminApprovedPropertyBookings";
import ViewAdminRejectedPropertyBookings from "./pages/ViewAdminRejectedPropertyBookings";

import ViewGuestPendingPropertyBookings from "./pages/ViewGuestPendingPropertyBookings";
import ViewGuestApprovedPropertyBookings from "./pages/ViewGuestApprovedPropertyBookings";
import ViewGuestRejectedPropertyBookings from "./pages/ViewGuestRejectedPropertyBookings";
import ViewGuestCancelledPropertyBookings from "./pages/ViewGuestCancelledPropertyBookings";
import AddPropertyRoomForm from "./pages/AddPropertyRoomForm";
import AddPropertyFacilityForm from "./pages/AddPropertyFacilityForm";
import AddLocationForm from "./locations/AddLocationForm";
import ViewAllLocations from "./locations/ViewAllLocations";
import UpdateLocationForm from "./locations/UpdateLocationForm";

function App() {
  return (
    <Router>
      <SideBar>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/home" element={<HomePage />} />
          <Route path="/users" element={<Users />} />
          <Route path="/about-us" element={<AboutUsPage />} />
          <Route path="/contact-us" element={<ContactUsPage />} />
          <Route path="/file-manager" element={<FileManager />} />
          <Route path="/order" element={<Order />} />
          <Route path="/saved" element={<Saved />} />
          <Route path="/settings" element={<Setting />} />
          <Route path="/user/login" element={<UserLoginForm />} />
          <Route path="/user/guest/register" element={<UserRegister />} />
          <Route path="/user/owner/register" element={<UserRegister />} />
          <Route path="/property/explore" element={<PropertyExporePage />} />
          <Route
            path="/property/:propertyId/detail"
            element={<PropertyDetailPage />}
          />
          <Route
            path="/property/:propertyId/room/:roomNo"
            element={<PropertyRoomBookingPage />}
          />
          <Route path="/user/logout" element={<UserLogout />} />
          <Route
            path="/user/guest/:userId/profile/detail"
            element={<UserProfilePage />}
          />
          <Route
            path="/user/owner/:userId/profile/detail"
            element={<UserProfilePage />}
          />
          <Route path="/user/guest/wallet" element={<MyWallet />} />
          <Route path="/owner/property/add" element={<AddPropertyForm />} />
          <Route
            path="/owner/property/:propertyId/room/add"
            element={<AddPropertyRoomForm />}
          />
          <Route
            path="/owner/property/:propertyId/facility/add"
            element={<AddPropertyFacilityForm />}
          />
          <Route
            path="/admin/property/pending/approval"
            element={<ViewPendingProperty />}
          />
          <Route
            path="/admin/property/rejected/approval"
            element={<ViewRejectedProperty />}
          />
          <Route
            path="/admin/property/approved/approval"
            element={<ViewApprovedProperty />}
          />
          <Route
            path="/owner/approved/property"
            element={<ViewOwnerApprovedProperty />}
          />
          <Route
            path="/owner/rejected/property"
            element={<ViewOwnerRejectedProperty />}
          />
          <Route
            path="/owner/pending/property"
            element={<ViewOwnerPendingProperty />}
          />
          <Route
            path="/owner/property/pending/booking"
            element={<ViewOwnerPendingPropertyBookings />}
          />
          <Route
            path="/owner/property/rejected/booking"
            element={<ViewOwnerRejectedPropertyBookings />}
          />
          <Route
            path="/owner/property/approved/booking"
            element={<ViewOwnerApprovedPropertyBookings />}
          />

          <Route
            path="/admin/property/pending/booking"
            element={<ViewAdminPendingPropertyBookings />}
          />
          <Route
            path="/admin/property/rejected/booking"
            element={<ViewAdminRejectedPropertyBookings />}
          />
          <Route
            path="/admin/property/approved/booking"
            element={<ViewAdminApprovedPropertyBookings />}
          />

          <Route
            path="/guest/property/pending/booking"
            element={<ViewGuestPendingPropertyBookings />}
          />
          <Route
            path="/guest/property/rejected/booking"
            element={<ViewGuestRejectedPropertyBookings />}
          />
          <Route
            path="/guest/property/approved/booking"
            element={<ViewGuestApprovedPropertyBookings />}
          />
          <Route
            path="/guest/property/cancelled/booking"
            element={<ViewGuestCancelledPropertyBookings />}
          />

          <Route path="/admin/location/add" element={<AddLocationForm />} />

          <Route path="/admin/location/view" element={<ViewAllLocations />} />
          <Route
            path="/admin/location/update"
            element={<UpdateLocationForm />}
          />

          <Route path="*" element={<ErrorPage />} />
        </Routes>
      </SideBar>
    </Router>
  );
}

export default App;
