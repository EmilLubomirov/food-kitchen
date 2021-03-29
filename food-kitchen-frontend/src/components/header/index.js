import React from "react"
import getNavigation from "../../utils/navigation";
import LinkComponent, {StyledLink} from "../../components/link";
import styled from 'styled-components';

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

    const navigation = getNavigation();

    return (
        <Wrapper>
            {  navigation.map((link, index) => {
                return <LinkComponent key={index}
                                      path={link.path}
                                      title={link.title}/>

            })
            }
        </Wrapper>
    )
};

export default Header;