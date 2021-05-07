import React from "react";
import styled from "styled-components";

const StyledFooter = styled.div`
    width: 100%;
    height: 50px;
    padding-top: 13px;
    text-align: center;
    color: white;
    background-color: dimgray;
`;

const Footer = () => {

    return (
        <StyledFooter>
            Copyright &copy; {new Date().getFullYear()} Food Kitchen, Inc.
            All rights reserved.
        </StyledFooter>
    )
};

export default Footer;