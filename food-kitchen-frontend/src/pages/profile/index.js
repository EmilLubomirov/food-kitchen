import React, {useContext, useState} from "react";
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

const Wrapper = styled.div`
    height: 450px;
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    align-items: center;
`;

const PasswordsWrapper = styled.div`
    display: flex;
`;

const ProfilePage = () => {

    const context = useContext(AuthContext);
    const history = useHistory();

    const [username, setUsername] = useState(context.user.username);
    const [oldPassword, setOldPassword] = useState('');
    const [updatePassword, setUpdatePassword] = useState('');
    const [updateConfirmPassword, setUpdateConfirmPassword] = useState('');
    const [updateAvatarImageUrl, setUpdateAvatarImageUrl] = useState('');
    const [isDisabled, setDisabled] = useState(true);
    const [btnLabel, setBtnLabel] = useState('Edit');

    const updateRequest = (pathUpdateVariable, queryParam, queryParamValue) => {

        const headers =  { 'Content-Type': 'application/json',
            'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME) };

        axios.patch(`http://localhost:8080/api/user/edit/${pathUpdateVariable}?${queryParam}=${queryParamValue}`,
            {},
            { headers })
            .then(res => {
                if (res.status === 200){

                    context.logout();
                    document.cookie = "auth-token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
                    history.push('/login');
                }
            })
    };

    const handleUsernameSubmit = () => {
        updateRequest('username', 'updateUsername', username);
    };

    const handlePasswordSubmit = () => {
        updateRequest('password', 'updatePassword', updatePassword);
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

    return (
        <PageLayout>
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

                <div>
                    <Button onClick={() => beginUpload(setUpdateAvatarImageUrl)}
                            label="Change profile picture"
                            disabled={isDisabled}/>
                    <Button
                        onClick={handleAvatarImageSubmit}
                        label="Save"
                        disabled={isDisabled}
                        style={{marginLeft: '10px'}}/>
                </div>

                <Button onClick={handleSubmit} label={btnLabel}/>
            </Wrapper>
        </PageLayout>
    )
};

export default ProfilePage;