import Cookies from 'js-cookie';

const TOKEN_COOKIE_NAME = 'token';

export const getTokenFromCookie = () => {
    return Cookies.get(TOKEN_COOKIE_NAME);
};


export const removeTokenCookie = () => {
    Cookies.remove(TOKEN_COOKIE_NAME);
};
