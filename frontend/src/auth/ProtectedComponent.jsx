import { Navigate } from "react-router-dom";

function ProtectedRoute ({ children }) {

    var token = localStorage.getItem("token");

    if(token === null && !window.location.href.includes("token"))
        return <Navigate to="/" />;
    else if (token && !JSON.parse(atob(token.split('.')[1])).roles.includes("ROLE_ADMIN") && window.location.pathname === '/admin')
        return <Navigate to="/user" />;

    return children;
};

export default ProtectedRoute