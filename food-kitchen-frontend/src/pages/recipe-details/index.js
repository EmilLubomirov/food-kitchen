import React, {useCallback, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import PageLayout from "../../components/page-layout";
import axios from "axios";
import RecipeDetailsCard from "../../components/recipe-details-card";

const RecipeDetailsPage = () => {

    const [recipe, setRecipe] = useState(null);
    const [isLoading, setLoading] = useState(true);

    const params = useParams();

    const getRecipe = useCallback(() => {

        const recipeId = params.recipeId;

        axios.get(`http://localhost:8080/api/recipe/${recipeId}`)
            .then(res => {

            if (res.status === 200){
                setRecipe(res.data);
            }
        })

    }, [params.recipeId]);

    useEffect(() => {

        if (recipe){
            setLoading(false);
        }
    }, [recipe]);

    useEffect(() => {
        getRecipe();
    }, [getRecipe]);

    return (
      <PageLayout>
          <div>
              {isLoading ? <div style={{height: "450px"}}/> :
                  (<RecipeDetailsCard recipe={recipe}/>)}
          </div>
      </PageLayout>
    );
};
export default RecipeDetailsPage;