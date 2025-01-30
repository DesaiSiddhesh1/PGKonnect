import { NavLink } from "react-router-dom";
import {
  FaBars,
  FaHome,
  FaWallet,
  FaSignOutAlt,
  FaUser,
  FaSignInAlt,
  FaUserPlus,
  FaPhoneAlt,
  FaInfoCircle,
  FaCheckCircle,
  FaHourglassHalf,
  FaTimesCircle,
  FaCalendarCheck,
  FaCalendarAlt,
  FaClock,
  FaCalendarTimes,
  FaMapMarkerAlt,
  FaMap,
} from "react-icons/fa";
import {
  MdApartment,
  MdHome,
  MdVisibility,
  MdAddLocation,
} from "react-icons/md";
import { BiCog } from "react-icons/bi";
import { useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import SidebarMenu from "./SidebarMenu";
const routes = [
  {
    path: "/",
    name: "Home",
    icon: <FaHome />,
  },
  {
    path: "/about-us",
    name: "About Us",
    icon: <FaInfoCircle />,
  },
  {
    path: "/contact-us",
    name: "Contact Us",
    icon: <FaPhoneAlt />,
  },
];

const SideBar = ({ children }) => {
  const guest = JSON.parse(sessionStorage.getItem("active-guest"));
  const admin = JSON.parse(sessionStorage.getItem("active-admin"));
  const owner = JSON.parse(sessionStorage.getItem("active-owner"));

  const userRoutes = [...routes];
  if (guest) {
    userRoutes.push(
      {
        path: "/guest/property/",
        name: "Bookings",
        icon: <FaCalendarAlt />,
        exact: true,
        subRoutes: [
          {
            path: "/guest/property/pending/booking",
            name: "Pending",
            icon: <FaClock />,
          },
          {
            path: "/guest/property/rejected/booking",
            name: "Rejected",
            icon: <FaCalendarTimes />,
          },

          {
            path: "/guest/property/approved/booking",
            name: "Approved",
            icon: <FaCalendarCheck />,
          },
          {
            path: "/guest/property/cancelled/booking",
            name: "Cancelled",
            icon: <FaCalendarTimes />,
          },
        ],
      },
      {
        path: "/settings",
        name: "Profile",
        icon: <BiCog />,
        exact: true,
        subRoutes: [
          {
            path: `/user/guest/${guest.id}/profile/detail`,
            name: "My Profile",
            icon: <FaUser />,
          },
          {
            path: "/user/guest/wallet",
            name: "Wallet",
            icon: <FaWallet />,
          },
        ],
      },
      {
        path: "/user/logout",
        name: "Logout",
        icon: <FaSignOutAlt />,
      }
    );
  } else if (owner) {
    userRoutes.push(
      {
        path: "/settings",
        name: "Profile",
        icon: <BiCog />,
        exact: true,
        subRoutes: [
          {
            path: `/user/owner/${owner.id}/profile/detail`,
            name: "My Profile",
            icon: <FaUser />,
          },
        ],
      },
      {
        path: "/property",
        name: "Property",
        icon: <MdApartment />,
        exact: true,
        subRoutes: [
          {
            path: "/owner/property/add",
            name: "Add Property",
            icon: <FaHome />,
          },
          {
            path: "/owner/approved/property",
            name: "Approved Property",
            icon: <FaCheckCircle />,
          },
          {
            path: "/owner/pending/property",
            name: "Pending Property",
            icon: <FaHourglassHalf />,
          },
          {
            path: "/owner/rejected/property",
            name: "Rejected Property",
            icon: <FaTimesCircle />,
          },
        ],
      },
      {
        path: "/owner/property/",
        name: "Bookings",
        icon: <FaCalendarAlt />,
        exact: true,
        subRoutes: [
          {
            path: "/owner/property/pending/booking",
            name: "Pending",
            icon: <FaClock />,
          },
          {
            path: "/owner/property/rejected/booking",
            name: "Rejected",
            icon: <FaCalendarTimes />,
          },
          {
            path: "/owner/property/approved/booking",
            name: "Approved",
            icon: <FaCalendarCheck />,
          },
        ],
      },
      {
        path: "/user/logout",
        name: "Logout",
        icon: <FaSignOutAlt />,
      }
    );
  } else if (admin) {
    userRoutes.push(
      {
        path: "/admin/location",
        name: "Location",
        icon: <FaMapMarkerAlt />,
        exact: true,
        subRoutes: [
          {
            path: "/admin/location/add",
            name: "Add Location",
            icon: <MdAddLocation />,
          },
          {
            path: "/admin/location/view",
            name: "View Location",
            icon: <FaMap />,
          },
        ],
      },
      {
        path: "/property",
        name: "Property",
        icon: <MdApartment />,
        exact: true,
        subRoutes: [
          {
            path: "/admin/property/pending/approval",
            name: "Pending Property",
            icon: <MdHome />,
          },
          {
            path: "/admin/property/rejected/approval",
            name: "Rejected Property",
            icon: <MdHome />,
          },
          {
            path: "/admin/property/approved/approval",
            name: "Approved Property",
            icon: <MdHome />,
          },
        ],
      },
      {
        path: "/admin/property/",
        name: "Bookings",
        icon: <FaCalendarAlt />,
        exact: true,
        subRoutes: [
          {
            path: "/admin/property/pending/booking",
            name: "Pending",
            icon: <FaClock />,
          },
          {
            path: "/admin/property/rejected/booking",
            name: "Rejected",
            icon: <FaCalendarTimes />,
          },
          {
            path: "/admin/property/approved/booking",
            name: "Approved",
            icon: <FaCalendarCheck />,
          },
        ],
      },
      {
        path: "/user/logout",
        name: "Logout",
        icon: <FaSignOutAlt />,
      }
    );
  } else {
    userRoutes.push({
      path: "/settings",
      name: "Users",
      icon: <BiCog />,
      exact: true,
      subRoutes: [
        {
          path: "/user/owner/register",
          name: "Register Owner",
          icon: <FaUserPlus />,
        },
        {
          path: "/user/guest/register",
          name: "Register Guest",
          icon: <FaUserPlus />,
        },
        {
          path: "/user/login",
          name: "Login",
          icon: <FaSignInAlt />,
        },
      ],
    });
  }

  const [isOpen, setIsOpen] = useState(false);
  const toggle = () => setIsOpen(!isOpen);
  const inputAnimation = {
    hidden: {
      width: 0,
      padding: 0,
      transition: {
        duration: 0.2,
      },
    },
    show: {
      width: "140px",
      padding: "5px 15px",
      transition: {
        duration: 0.2,
      },
    },
  };

  const showAnimation = {
    hidden: {
      width: 0,
      opacity: 0,
      transition: {
        duration: 0.5,
      },
    },
    show: {
      opacity: 1,
      width: "auto",
      transition: {
        duration: 0.5,
      },
    },
  };

  return (
    <>
      <div className="main-container">
        <motion.div
          animate={{
            width: isOpen ? "200px" : "45px",

            transition: {
              duration: 0.5,
              type: "spring",
              damping: 10,
            },
          }}
          className={`sidebar `}
        >
          <div className="top_section">
            <AnimatePresence>
              {isOpen && (
                <motion.h1
                  variants={showAnimation}
                  initial="hidden"
                  animate="show"
                  exit="hidden"
                  className="logo"
                >
                  <b> PG Haven</b>
                </motion.h1>
              )}
            </AnimatePresence>

            <div className="bars">
              <FaBars onClick={toggle} />
            </div>
          </div>

          <section className="routes">
            {userRoutes.map((route, index) => {
              if (route.subRoutes) {
                return (
                  <SidebarMenu
                    setIsOpen={setIsOpen}
                    route={route}
                    showAnimation={showAnimation}
                    isOpen={isOpen}
                  />
                );
              }

              return (
                <NavLink
                  to={route.path}
                  key={index}
                  className="link"
                  activeClassName="active"
                >
                  <div className="icon text-color">{route.icon}</div>
                  <AnimatePresence>
                    {isOpen && (
                      <motion.div
                        variants={showAnimation}
                        initial="hidden"
                        animate="show"
                        exit="hidden"
                        className="link_text text-color"
                      >
                        <b>{route.name}</b>
                      </motion.div>
                    )}
                  </AnimatePresence>
                </NavLink>
              );
            })}
          </section>
        </motion.div>

        <main>{children}</main>
      </div>
    </>
  );
};

export default SideBar;
