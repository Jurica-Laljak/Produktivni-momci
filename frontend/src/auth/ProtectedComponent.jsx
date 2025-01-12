import { Navigate } from "react-router-dom";

function ProtectedRoute ({ children }) {

    if(localStorage.getItem("token") === null && !window.location.href.includes("token"))
        return <Navigate to="/" />;

    return children;
};

export default ProtectedRoute