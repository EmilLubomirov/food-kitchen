import React, {useContext, useEffect, useState} from "react"
import {useHistory} from "react-router-dom";
import getNavigation from "../../utils/navigation";
import LinkComponent, {StyledLink} from "../../components/link";
import AuthContext from "../../AuthContext";
import styled from 'styled-components';
import AvatarComponent from "../avatar";
import {MESSAGE_TYPES, MESSAGES} from "../../utils/constants";
import {getCookie} from "../../utils/cookie";

const Wrapper = styled.div`
    height: 70px;
    background-color: #deb887e3;
    display: flex;
    justify-content: space-around;
    align-items: center;
    
    & ${StyledLink} {
        font-weight: bolder;
    }
    
    @media screen and (max-width: 500px) {
        flex-flow: column;
        height: 300px;
    }
`;

const Header = () => {

    const [isLoading, setLoading] = useState(true);

    const context = useContext(AuthContext);
    const navigation = getNavigation(context.user);
    const history = useHistory();

    const handleLogout = async () => {

        await context.logout();

        document.cookie = "auth-token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        sessionStorage.removeItem("chosenCategories");
        sessionStorage.removeItem("recipeLimit");

        history.push('/login', {
            message: MESSAGES.logoutSuccess,
            type: MESSAGE_TYPES.success
        });
    };

    useEffect(() => {

        if (context.user ||
            context.user === null ||
            !getCookie("auth-token")){

            setLoading(false);
        }

    },[context.user]);

    return (
        <Wrapper>

            {
                isLoading ? null :

                    navigation.map((link, index) => {
                        return <LinkComponent key={index}
                                              path={link.path}
                                              title={link.title}/>
                    })
            }

            {
                isLoading ? null :

                context.user ? (
                <>
                    <AvatarComponent image={
                        context.user.avatarImageUrl ? context.user.avatarImageUrl :
                        "https://icon-library.com/images/default-user-icon/default-user-icon-13.jpg"}
                        handleLogout={handleLogout}/>
                        </>
            ) : null}
        </Wrapper>
    )
};

export default Header;