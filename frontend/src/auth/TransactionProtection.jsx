import { Navigate, useLocation } from "react-router-dom";

function TransactionProtectedRoute ({ children }) {
    const location = useLocation()
    if (!location || !location.state || location.state.sentFrom !== "listing") {
        return <Navigate to="/"></Navigate>
    }
    return children;
};

export default TransactionProtectedRoute