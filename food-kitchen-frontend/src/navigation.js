import React from "react"
import {Route, Switch} from "react-router-dom";
import HomePage from "./pages/home";
import RecipePage from "./pages/recipe";
import AddRecipePage from "./pages/recipe-add";

const Navigation = () => {

    return (
        <Switch>
            <Route path='/' exact component={HomePage}/>
            <Route path='/recipe' exact component={RecipePage}/>
            <Route path='/recipe/add' component={AddRecipePage}/>
        </Switch>
    )
};

export default Navigation;