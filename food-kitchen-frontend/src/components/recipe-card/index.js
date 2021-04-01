import React from "react";
import {Card} from "primereact/card";
import LinkComponent from "../link";

const RecipeCard = ({recipe}) => {

    const header = (
        <img alt={recipe.title} src={recipe.imageUrl}
             onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} />
    );

    return (

        <LinkComponent path={`recipe/${recipe.id}`}>
            <Card title={recipe.title} style={{ width: '16em', height: '18em'}} header={header}>
                {/*<p className="p-m-0" style={{lineHeight: '1.5'}}>{recipe.title}</p>*/}
            </Card>
        </LinkComponent>
    )
};

export default RecipeCard;