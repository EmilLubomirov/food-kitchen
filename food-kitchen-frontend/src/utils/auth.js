import axios from "axios";
import {getCookie} from "./cookie";

export const authenticate = (url, body, headers, onSuccess, onFailure) =>{

    axios.post(url, body, { headers })
        .then(response => {

        if (response.status !== 200) {
            onFailure(response.statusText);
            return;
        }

        const authToken = response.data.token;

            const now = new Date();
            const time = now.getTime();

            //expire after a week
            const expireTime = time + (7*24*60*60*1000);
            now.setTime(expireTime);

            document.cookie = `${process.env.REACT_APP_AUTH_COOKIE_NAME}=${authToken};expires=${now.toUTCString()};path=/`;

        onSuccess(response);

    }).catch(e => onFailure(e));
};

export const getLoggedInUser = async () =>{

    const authToken = getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME) || "";

    const url = 'http://localhost:8080/api/user/verifyLogin';
    const headers =  { 'Content-Type': 'application/json',
                        'Authorization': authToken
    };

    const promise = await axios.post(url, {}, { headers });

    return promise.data;
};