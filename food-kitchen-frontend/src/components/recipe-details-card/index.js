import React from "react";
import {Card} from "primereact/card";
import styled from "styled-components";

const Wrapper = styled.div`
    display: flex;
    justify-content: center;
    margin: 30px;
`;

const RecipeDetailsCard = ({recipe}) => {

    const {title, imageUrl, description, publisher} = recipe;

    const header = (
        <img src={imageUrl}
             alt={title}
             onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} />
    );

    return (
        <Wrapper>
            <Card title={title} subTitle="Subtitle" style={{ width: '25em' }} header={header}>
                <p className="p-m-0" style={{lineHeight: '1.5'}}>{description}</p>
                <p className="p-m-0" style={{lineHeight: '1.5'}}>Published by: {publisher}</p>
            </Card>
        </Wrapper>
    )

};

export default RecipeDetailsCard;