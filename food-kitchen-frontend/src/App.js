import React, {useCallback, useEffect, useState} from "react"
import AuthContext from "./AuthContext";
import './App.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import PrimeReact from 'primereact/api';
import {getLoggedInUser} from "./utils/auth";

PrimeReact.ripple = true;

const App = (props) => {

    const [state, setState] = useState({
        user: undefined,
        isLoading: true,
        login: (user) =>{
            setState({
                ...state,
                user: { ...user, isLoggedIn: true},
            });
        },
        logout: () =>{
            setState({
                ...state,
                user: null,
            });
        }
    });

    const checkUserStatus = useCallback(async () =>{
        const user = await getLoggedInUser();

        const {id, username, authorities} = user;
        const isAdmin = authorities.some(auth => auth['authority'] === 'ADMIN');

        setState({
            ...state,
            user: { id, username, isAdmin },
            isLoading: false
        });
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    useEffect(() =>{
        checkUserStatus();
    }, [checkUserStatus]);

    return (
        <AuthContext.Provider value={state}>
            {props.children}
        </AuthContext.Provider>
    )
};


export default App;
