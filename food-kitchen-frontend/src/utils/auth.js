import axios from "axios";
import {getCookie} from "./cookie";

const authCookieName = "auth-token";

export const authenticate = (url, body, headers, onSuccess, onFailure) =>{

    axios.post(url, body, { headers })
        .then(response => {

        if (response.status !== 200) {
            onFailure(response.statusText);
            return;
        }

        const authToken = response.data.token;
        document.cookie = `${authCookieName}=${authToken}`;

        onSuccess(response);

    }).catch(e => onFailure(e));
};

export const getLoggedInUser = async () =>{

    const authToken = getCookie(authCookieName) || "";

    const url = 'http://localhost:8080/api/user/verifyLogin';
    const headers =  { 'Content-Type': 'application/json', "authorization": authToken};

    const promise = await axios.post(url, {}, { headers });

    return promise.data;
};