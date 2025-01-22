import { Link } from 'react-router-dom'
import './LinkWrapper.css'

export default function LinkWrapper({ children, to, onClick }) {
    return (
        <Link to={to} onClick={onClick} className="link">
            <div className="link-flex">
                {children}
            </div>
        </Link>
    )
}   