import React from "react";
import styled from "styled-components";
import {Card} from "primereact/card";

const StyledFormWrapper = styled(Card)`
    
    width: fit-content;
    height: fit-content;
    margin: 20px auto;
`;

const FormWrapper = (props) => {

    return (
        <StyledFormWrapper>
                {props.children}
        </StyledFormWrapper>
    )
};

export default FormWrapper;