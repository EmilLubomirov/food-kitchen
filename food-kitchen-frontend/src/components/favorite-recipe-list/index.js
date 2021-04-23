import React from "react";
import {DataScroller} from "primereact/datascroller";
import {Rating} from "primereact/rating";
import {Button} from "primereact/button";

const FavoriteRecipeList = ({favRecipes, handleRecipeShow, handleRecipeRemoveFav}) => {

    const itemTemplate = ({id, imageUrl, title, rating}) => {
        return (
            <div className="product-item">
                <img src={imageUrl}
                     style={{width: "20em"}}
                     onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'}
                     alt={title} />
                <div className="product-detail">
                    <div className="product-name">{title}</div>
                    <Rating value={Math.ceil(rating)} readOnly cancel={false}>Rating</Rating>
                </div>
                <div className="product-action">
                    <Button icon="pi pi-search-plus"
                            label="Show"
                            onClick={() => handleRecipeShow(id)}/>
                    <Button icon="pi pi-minus"
                            label="Remove"
                            onClick={() => handleRecipeRemoveFav(id)}/>
                </div>
            </div>
        );
    };

    return (

        <div className="datascroller-demo">
            <div className="card">
                <DataScroller value={favRecipes}
                              itemTemplate={itemTemplate}
                              rows={5} inline
                              scrollHeight="430px"/>
            </div>
        </div>
    )
};

export default FavoriteRecipeList;