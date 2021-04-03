import React, {useContext, useEffect, useState} from "react";
import PageLayout from "../../components/page-layout";
import axios from "axios";
import RecipeCards from "../../components/recipe-cards";
import AuthContext from "../../AuthContext";

const RecipePage = () => {

    const [recipes, setRecipes] = useState([]);
    const context = useContext(AuthContext);

    const getRecipes = () => {

        axios.get('http://localhost:8080/api/recipe')
            .then(res => setRecipes(res.data));
    };

    useEffect(() => {
        getRecipes();
    }, []);

    return (
        <PageLayout>
            {
                context.user ?
                <div>
                    <h3>{context.user.username}</h3>
                    <h3>{context.user.isAdmin ? "Admin" : "Not admin"}</h3>
                </div> : <div>Loading....</div>
            }
            <h1>Recipe page</h1>
            <RecipeCards recipes={recipes}/>
        </PageLayout>
    )
};

export default RecipePage;