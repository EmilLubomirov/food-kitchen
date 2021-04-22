import React from "react";
import {ScrollTop} from "primereact/scrolltop";
import styled from "styled-components";

const StyledScrollTop = styled(ScrollTop)`
    
    &.custom {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    background-color: var(--primary-color) !important;
}
&.custom:hover {
    background-color: var(--primary-color) !important;
}

&.custom .p-scrolltop-icon {
    font-size: 1rem;
    color: var(--primary-color-text);
}
`;

const ScrollTopComponent = ({threshold}) => {

    return (
        <StyledScrollTop threshold={threshold}
                         className='custom'
                         icon="pi pi-arrow-up"/>
    )
};

export default ScrollTopComponent;