import React from "react";
import RecipeCard from "../recipe-card";
import styled from "styled-components";

const Wrapper = styled.div`
         display: flex;
         flex-flow: row wrap;
         justify-content: space-around;
`;

const RecipeCards = ({recipes}) => {

    return (
        <Wrapper>
            {
                recipes.map(r => {
                    return <RecipeCard count={recipes.length} recipe={r} key={r.id}/>
                })
            }
        </Wrapper>
    )
};

export default RecipeCards;