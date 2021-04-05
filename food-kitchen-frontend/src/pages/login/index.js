import React, {useContext, useState} from "react";
import AuthContext from "../../AuthContext";
import {useHistory} from "react-router-dom";
import PageLayout from "../../components/page-layout";
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";
import styled from "styled-components";
import {Password} from "primereact/password";
import {authenticate} from "../../utils/auth";

const Wrapper = styled.div`
    height: 350px;
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    align-items: center;
`;

const LoginPage = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const context = useContext(AuthContext);
    const history = useHistory();

    const handleSubmit = () => {

        if (username === '' || password === ''){
            return;
        }

        const url = 'http://localhost:8080/api/user/login';
        const body = {
            username,
            password
        };

        const headers = {'Content-Type': 'application/json'};

        authenticate(url, body, headers, async (response) =>{

            const {id, username, authorities} = response.data;
            const isAdmin = authorities.some(auth => auth['authority'] === 'ADMIN');

            context.login({
                id,
                username,
                isAdmin
            });

            history.push('/recipe');
        }, (e) => {
            console.error(e);
        })
    };

    return (
        <PageLayout>
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
            </Wrapper>
        </PageLayout>
    )
};

export default LoginPage;