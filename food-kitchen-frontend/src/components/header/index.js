import React, {useContext} from "react"
import {useHistory} from "react-router-dom";
import getNavigation from "../../utils/navigation";
import LinkComponent, {StyledLink} from "../../components/link";
import AuthContext from "../../AuthContext";
import {Button} from "primereact/button";
import styled from 'styled-components';
import AvatarComponent from "../avatar";

const Wrapper = styled.div`
    height: 70px;
    background-color: lightgray;
    display: flex;
    justify-content: space-around;
    align-items: center;
    
    & ${StyledLink} {
        font-weight: bolder;
    }
`;

const Header = () => {

    const context = useContext(AuthContext);
    const navigation = getNavigation(context.user);
    const history = useHistory();

    const handleLogout = () => {

        context.logout();
        document.cookie = "auth-token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";

        history.push('/login');
    };

    return (
        <Wrapper>
            {  navigation.map((link, index) => {
                return <LinkComponent key={index}
                                      path={link.path}
                                      title={link.title}/>

            })
            }

            {context.user ? (
                <>
                    <Button label="Logout" onClick={handleLogout}/>
                    <AvatarComponent image={
                        context.user.avatarImageUrl ? context.user.avatarImageUrl :
                        "https://png.pngtree.com/element_our/20200610/ourmid/pngtree-character-default-avatar-image_2237203.jpg"}/>
                        </>
            ) : null}
        </Wrapper>
    )
};

export default Header;