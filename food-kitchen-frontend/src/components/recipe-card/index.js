import React, {useState} from "react";
import {Card} from "primereact/card";
import LinkComponent from "../link";
import {Rating} from "primereact/rating";
import styled from "styled-components";

const StyledCard = styled(Card)`
    width: 16em;
    margin-top: 30px;
`;

const StyledImage = styled.img`
    width: 16em;
    height: 11em;
`;

const RecipeCard = ({recipe}) => {

    const { title, imageUrl, id, rating }= recipe;

    const header = (
        <StyledImage src={imageUrl}
                     alt={title}
             onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} />
    );

    const footer = (
        <div>
            <h5>Rating</h5>
            <Rating value={Math.ceil(rating)} readOnly stars={5} cancel={false} />
        </div>
    );

    return (

        <LinkComponent path={`recipe/${id}`}>
            <StyledCard
                  title={title}
                  header={header}
                  footer={footer}>
                {/*<p className="p-m-0" style={{lineHeight: '1.5'}}>{recipe.title}</p>*/}
                {/*{footer}*/}
            </StyledCard>
        </LinkComponent>
    )
};

export default RecipeCard;