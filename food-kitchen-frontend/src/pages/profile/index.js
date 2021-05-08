import React, {useCallback, useContext, useRef, useState} from "react";
import {useHistory} from "react-router-dom";
import PageLayout from "../../components/page-layout";
import {InputText} from "primereact/inputtext";
import AuthContext from "../../AuthContext";
import styled from "styled-components";
import {Button} from "primereact/button";
import axios from "axios";
import {getCookie} from "../../utils/cookie";
import {Password} from "primereact/password";
import {beginUpload} from "../../utils/cloudinaryService";
import  { MESSAGES, MESSAGE_TYPES } from "../../utils/constants";
import {Toast} from "primereact/toast";
import FormWrapper from "../../components/form-wrapper";

const Wrapper = styled.div`
    height: 650px;
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    align-items: center;
`;

const PasswordsWrapper = styled.div`
    display: flex;
`;

const ProfilePictureWrapper = styled.div`
    display: flex;
    flex-flow: column;
    height: 280px;
    justify-content: space-around;
    align-items: center;
`;

const StyledImage = styled.img`
    width: 15%;
    border-radius: 50%;
`;

const ProfilePage = () => {

    const context = useContext(AuthContext);
    const history = useHistory();

    const toast = useRef(null);

    const [username, setUsername] = useState(context.user.username);
    const [oldPassword, setOldPassword] = useState('');
    const [updatePassword, setUpdatePassword] = useState('');
    const [updateConfirmPassword, setUpdateConfirmPassword] = useState('');
    const [updateAvatarImageUrl, setUpdateAvatarImageUrl] = useState('');
    const [isDisabled, setDisabled] = useState(true);
    const [btnLabel, setBtnLabel] = useState('Edit');

    const updateRequest = (pathUpdateVariable, queryParam, queryParamValue) => {

        if (queryParamValue.trim().length === 0){
            showMessage(MESSAGE_TYPES.error, MESSAGES.emptyFields);
            return;
        }

        const headers =  { 'Content-Type': 'application/json',
            'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME) };

        axios.patch(`http://localhost:8080/api/user/edit/${pathUpdateVariable}?${queryParam}=${queryParamValue}`,
            {},
            { headers })
            .then(res => {
                if (res.status === 200){

                    context.logout();
                    document.cookie = "auth-token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";

                    history.push('/login', {
                        message: MESSAGES.profileChangesSuccess,
                        type: MESSAGE_TYPES.success
                    });
                }
            })
            .catch(() => {
                showMessage(MESSAGE_TYPES.error, MESSAGES.invalidFieldData);
        })
    };

    const handleUsernameSubmit = () => {
        updateRequest('username', 'updateUsername', username);
    };

    const handlePasswordSubmit = () => {

        if (oldPassword.trim().length === 0 ||
            updatePassword.trim().length === 0 ||
            updateConfirmPassword.trim().length === 0){

            showMessage(MESSAGE_TYPES.error, MESSAGES.emptyFields);
            return;
        }

        if (updatePassword !== updateConfirmPassword){
            showMessage(MESSAGE_TYPES.error, MESSAGES.passwordAndConfPasswordMismatch);
            return;
        }

        const headers =  { 'Content-Type': 'application/json',
            'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME) };

        const url = `http://localhost:8080/api/user/edit/password?oldPassword=${oldPassword}&updatePassword=${updatePassword}`;

        axios.patch(url,
            {},
            { headers })
            .then(res => {
                if (res.status === 200){

                    context.logout();
                    document.cookie = "auth-token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";

                    history.push('/login', {
                        message: MESSAGES.profileChangesSuccess,
                        type: MESSAGE_TYPES.success
                    });
                }
            })
            .catch(() => {
                showMessage(MESSAGE_TYPES.error, MESSAGES.invalidFieldData);
            })
    };

    const handleAvatarImageSubmit = () => {
        updateRequest('profilePicture','updateAvatarImageUrl', updateAvatarImageUrl);
    };


    const handleSubmit = () => {

        if (btnLabel === 'Discard'){
            history.push('/recipe');
        }

        else {
            setDisabled(false);
            setBtnLabel('Discard');
        }
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
            <FormWrapper>
                <Wrapper>
                    <h1>Profile</h1>

                    <div>
                    <span className="p-float-label">
                    <InputText id="username"
                               value={username}
                               onChange={(e) => setUsername(e.target.value)}
                               disabled={isDisabled}/>
                    <label htmlFor="username">Username</label>

                    <Button
                        onClick={handleUsernameSubmit}
                        label="Save"
                        disabled={isDisabled}/>
                </span>
                    </div>

                    <PasswordsWrapper>

                    <span className="p-float-label">
                    <Password id="password" value={oldPassword}
                              onChange={(e) => setOldPassword(e.target.value)} toggleMask
                              feedback={false}
                              disabled={isDisabled}/>
                               <label htmlFor="password">Old Password</label>

                    </span>

                        <span className="p-float-label">
                    <Password id="updatePassword" value={updatePassword}
                              onChange={(e) => setUpdatePassword(e.target.value)} toggleMask
                              disabled={isDisabled}/>
                              <label htmlFor="updatePassword">New Password</label>
                    </span>

                        <span className="p-float-label">
                    <Password id="updateConfirmPassword" value={updateConfirmPassword}
                              onChange={(e) => setUpdateConfirmPassword(e.target.value)} toggleMask
                              disabled={isDisabled}/>
                              <label htmlFor="updateConfirmPassword">Confirm New Password</label>
                    </span>

                        <Button label="Save"
                                onClick={handlePasswordSubmit}
                                disabled={isDisabled}/>
                    </PasswordsWrapper>

                    <ProfilePictureWrapper>

                        <StyledImage src={context.user.avatarImageUrl ||
                        "https://icon-library.com/images/default-user-icon/default-user-icon-13.jpg"}
                             alt="Profile"/>

                             <div>
                                 <Button onClick={() => beginUpload(setUpdateAvatarImageUrl)}
                                         label="Change profile picture"
                                         disabled={isDisabled}
                                         style={{marginLeft: '10px'}}/>
                                 <Button
                                     onClick={handleAvatarImageSubmit}
                                     label="Save"
                                     disabled={isDisabled}
                                     style={{marginLeft: '10px'}}/>
                             </div>

                    </ProfilePictureWrapper>

                    <Button style={{width: "12%", height: "40px"}} onClick={handleSubmit} label={btnLabel}/>

                    <Toast ref={toast} position="bottom-right"/>
                </Wrapper>
            </FormWrapper>
        </PageLayout>
    )
};

export default ProfilePage;