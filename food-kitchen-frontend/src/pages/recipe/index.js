import React, {useEffect, useState} from "react";
import PageLayout from "../../components/page-layout";
import axios from "axios";
import RecipeCards from "../../components/recipe-cards";

const RecipePage = () => {

    const [recipes, setRecipes] = useState([]);

    const getRecipes = () => {

        axios.get('http://localhost:8080/api/recipe')
            .then(res => setRecipes(res.data));
    };

    useEffect(() => {
        getRecipes();
    }, []);

    return (
        <PageLayout>
            <h1>Recipe page</h1>
            <RecipeCards recipes={recipes}/>
        </PageLayout>
    )
};

export default RecipePage;