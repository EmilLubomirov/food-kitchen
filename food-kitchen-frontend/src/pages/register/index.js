import React, {useCallback, useRef, useState} from "react";
import {useHistory} from "react-router-dom";
import PageLayout from "../../components/page-layout";
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";
import styled from "styled-components";
import axios from "axios";
import {Password} from "primereact/password";
import {beginUpload} from "../../utils/cloudinaryService";
import {CloudinaryContext} from "cloudinary-react";
import {Toast} from "primereact/toast";
import {MESSAGE_TYPES, MESSAGES} from "../../utils/constants";
import FormWrapper from "../../components/form-wrapper";

const Wrapper = styled.div`
    width: 400px;
    height: 400px;
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    align-items: center;
`;

const RegisterPage = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [avatarImageUrl, setAvatarImageUrl] = useState(null);

    const toast = useRef(null);
    const history = useHistory();

    const handleSubmit = () => {

        if (username.trim().length === 0 ||
            password.trim().length === 0 ||
            confirmPassword.trim().length === 0){
            showMessage(MESSAGE_TYPES.error, MESSAGES.emptyFields);
            return;
        }

        if (password !== confirmPassword){
            showMessage(MESSAGE_TYPES.error, MESSAGES.passwordAndConfPasswordMismatch);
            return;
        }

        const url = 'http://localhost:8080/api/user/register';
        const body = {
            username,
            password,
            confirmPassword,
            avatarImageUrl
        };

        const headers = {'Content-Type': 'application/json'};

        axios.post(url, body, { headers })
            .then(res => {

                if (res.status === 200 || res.status === 201){
                    history.push('/login');
                }
        }).catch(() => {
            showMessage(MESSAGE_TYPES.error, MESSAGES.invalidFieldData);
        });
    };

    const showMessage = useCallback((type, value) => {

        if (toast.current){

            toast.current.show({
                severity: type,
                summary: value,
            })
        }
    },[]);

    return (
        <PageLayout>
            <CloudinaryContext cloudName={process.env.REACT_APP_CLOUD_NAME}>
                <FormWrapper>
                    <Wrapper className="card">
                        <h1>Register</h1>
                        <span className="p-float-label">
                    <InputText id="username" value={username} onChange={(e) => setUsername(e.target.value)} />
                    <label htmlFor="username">Username</label>
                </span>

                        <span className="p-float-label">
                    <Password value={password} onChange={(e) => setPassword(e.target.value)} toggleMask />
                    <label htmlFor="password">Password</label>
                </span>

                        <span className="p-float-label">
                    <Password value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} toggleMask />
                    <label htmlFor="confirmPassword">Confirm Password</label>
                </span>

                        <Button onClick={() => beginUpload(setAvatarImageUrl)} label={"Add profile picture"}/>
                        <Button onClick={handleSubmit} label="Register"/>

                        <Toast ref={toast} position="bottom-right"/>
                    </Wrapper>
                </FormWrapper>
            </CloudinaryContext>
        </PageLayout>
    )
};

export default RegisterPage;