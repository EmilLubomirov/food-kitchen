import React, {useContext} from "react"
import {Route, Switch, Redirect} from "react-router-dom";
import HomePage from "./pages/home";
import RecipePage from "./pages/recipe";
import AddRecipePage from "./pages/recipe-add";
import RegisterPage from "./pages/register";
import LoginPage from "./pages/login";
import AuthContext from "./AuthContext";
import RecipeDetailsPage from "./pages/recipe-details";
import ProfilePage from "./pages/profile";
import ForumPage from "./pages/forum";
import ForumQuestionPage from "./pages/forum-topic";
import BookPage from "./pages/book";
import ArticlePage from "./pages/article";
import ArticleDetailsPage from "./pages/article-details";

const Navigation = () => {

    const context = useContext(AuthContext);
    const isLoggedIn = !!context.user;

    return (
        <Switch>
            <Route path='/' exact component={HomePage}/>

            <Route path='/recipe' exact component={RecipePage}/>

            <Route path='/recipe/add'>
                {isLoggedIn ? (<AddRecipePage/>) : (<Redirect to="/login"/>)}
            </Route>

            <Route path='/recipe/:recipeId' component={RecipeDetailsPage}/>

            <Route path='/register'>
                {!isLoggedIn ? (<RegisterPage/>) : (<Redirect to="/"/>)}
            </Route>

            <Route path='/login'>
                {!isLoggedIn ? (<LoginPage/>) : (<Redirect to="/"/>)}
            </Route>

            <Route path='/profile'>
                {isLoggedIn ? (<ProfilePage/>) : (<Redirect to="/"/>)}
            </Route>

            <Route path='/forum' exact>
                {isLoggedIn ? (<ForumPage/>) : (<Redirect to="/"/>)}
            </Route>

            <Route path='/forum/:categoryName/:topicId'>
                {isLoggedIn ? (<ForumQuestionPage/>) : (<Redirect to="/"/>)}
            </Route>

            <Route path='/book' component={BookPage}/>

            <Route path='/article' exact component={ArticlePage}/>

            <Route path='/article/:articleId' component={ArticleDetailsPage}/>
        </Switch>
    )
};

export default Navigation;