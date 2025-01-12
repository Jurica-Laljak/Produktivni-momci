import { Navigate } from "react-router-dom";

function ProtectedRoute ({ children }) {
    try {
        const { token } = localStorage.getItem("token");
    } catch (e) {
        return <Navigate to="/" />;
    }
    return children;
};

export default ProtectedRoute