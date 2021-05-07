import React, {useCallback, useContext, useEffect, useRef, useState} from "react";
import {useLocation} from "react-router-dom";
import AuthContext from "../../AuthContext";
import {useHistory} from "react-router-dom";
import PageLayout from "../../components/page-layout";
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";
import styled from "styled-components";
import {Password} from "primereact/password";
import {authenticate} from "../../utils/auth";
import  { MESSAGES, MESSAGE_TYPES } from "../../utils/constants";
import {Toast} from "primereact/toast";
import FormWrapper from "../../components/form-wrapper";

const Wrapper = styled.div`
    width: 300px;
    height: 350px;
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    align-items: center;
`;

const LoginPage = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const toast = useRef(null);

    const location = useLocation();
    const { state } = location;

    const [message, ] = useState({
        isOpen: state ? !!state.message : false,
        value: state ? state.message || "" : "",
        type: state ? state.type || "" : ""
    });

    const context = useContext(AuthContext);
    const history = useHistory();

    const handleSubmit = () => {

        if (username === '' || password === ''){
            showMessage(MESSAGE_TYPES.error, MESSAGES.emptyUsernameOrPassword);
            return;
        }

        const url = 'http://localhost:8080/api/user/login';
        const body = {
            username,
            password
        };

        const headers = {'Content-Type': 'application/json'};

        authenticate(url, body, headers, async (response) =>{

            const {id, username, avatarImageUrl, authorities} = response.data;
            const isAdmin = authorities.some(auth => auth['authority'] === 'ADMIN');

            context.login({
                id,
                username,
                isAdmin,
                avatarImageUrl,
            });

            history.push('/recipe', {
                message: MESSAGES.successfulLogin,
                type: MESSAGE_TYPES.success
            });
        }, (e) => {
            showMessage(MESSAGE_TYPES.error, MESSAGES.unsuccessfulLogin);
        })
    };

    const showMessage = useCallback((type, value) => {

        if (toast.current){
            toast.current.show({
                severity: type,
                summary: value,
            })
        }
    },[]);

    useEffect(() => {

        if (message.isOpen){
            showMessage(message.type, message.value);
        }

    }, [message, showMessage]);

    return (
        <PageLayout>
            <FormWrapper>
                <Wrapper className="card">
                    <h1>Login</h1>
                    <span className="p-float-label">
                    <InputText id="username" value={username} onChange={(e) => setUsername(e.target.value)} />
                    <label htmlFor="username">Username</label>
                </span>

                    <span className="p-float-label">
                    <Password value={password} onChange={(e) => setPassword(e.target.value)} toggleMask  feedback={false} />
                    <label htmlFor="password">Password</label>
                </span>

                    <Button onClick={handleSubmit} label="Login"/>

                    <Toast ref={toast} position="bottom-right"/>
                </Wrapper>
            </FormWrapper>
        </PageLayout>
    )
};

export default LoginPage;