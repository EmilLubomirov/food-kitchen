import React from "react";
import {Link} from "react-router-dom";
import styled from 'styled-components';

export const StyledLink = styled(Link)`
    text-decoration: none;
`;

const LinkComponent = ({onClick, path, title, children}) =>{

    return (
        <div data-test-id={`link-${title}`}>
            <StyledLink onClick={onClick} to={path}>
                {children}
                {title}
            </StyledLink>
        </div>
    )
};

export default LinkComponent;