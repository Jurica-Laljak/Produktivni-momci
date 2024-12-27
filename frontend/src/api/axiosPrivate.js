import axios from 'axios';
import { getTokenFromCookie } from '../utils/cookieUtils';

// Instanca za autorizirane zahtjeve
const axiosPrivate = axios.create({
    baseURL: '/api', //  osnovni URL za backend
});


axiosPrivate.interceptors.request.use(
    (config) => {
        const token = getTokenFromCookie();
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default axiosPrivate;
