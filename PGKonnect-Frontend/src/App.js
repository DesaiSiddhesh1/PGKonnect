import "./App.css";
import SideBar from "./components/Sidebar/SideBar";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";



import AboutUsPage from "./pages/AboutUsPage";


import HomePage from "./pages/HomePage";
import ErrorPage from "./pages/ErrorPage";




function App() {
  return (
    <Router>
      <SideBar>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/home" element={<HomePage />} />

          <Route path="/about-us" element={<AboutUsPage />} />


          <Route path="*" element={<ErrorPage />} />
        </Routes>
      </SideBar>
    </Router>
  );
}

export default App;
