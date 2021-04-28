import React, {useState} from "react";
import {useHistory, useLocation} from "react-router-dom";
import {SlideMenu} from "primereact/slidemenu";
import FavoriteRecipeList from "../favorite-recipe-list";
import DialogWindow from "../dialog";
import {getCookie} from "../../utils/cookie";
import axios from "axios";

const AvatarDropdownMenu = ({menu, handleLogout}) => {

    const [isFavListVisible, setFavListVisible] = useState(false);
    const [favRecipes, setFavRecipes] = useState([]);

    const history = useHistory();
    const location = useLocation();

    const items = [
        {
            label: 'Favorites',
            icon: 'pi pi-heart',
            command: () => {
                getFavRecipes();
            }
        },
        {
            label: 'Profile',
            icon: 'pi pi-user',
            command: () => {

                if (location.pathname !== '/profile'){
                    history.push('/profile');
                }
            }
        },
        {   label: 'Logout',
            icon: 'pi pi-sign-out',
            command: () => {

                handleLogout();

                if (location.pathname !== '/login'){
                    history.push('/login');
                }
            }
        }
    ];

    const getFavRecipes = () => {

        const url = 'http://localhost:8080/api/recipe/favorite';

        const headers = {"Content-Type": "application/json",
            "Authorization": getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME)};

        axios.get(url, { headers })
            .then(res => {
                if (res.status === 200){

                    try {
                        setFavRecipes(res.data._embedded.recipeServiceModelList);
                    }

                    catch(e){
                        setFavRecipes([]);
                    }

                    setFavListVisible(true);
                }
            })
    };

    const handleRecipeShow = (id) => {

        const recipePath = `/recipe/${id}`;

        if (location.pathname !== recipePath){
            history.push(recipePath);
        }

        setFavListVisible(false);
    };

    const handleRecipeRemoveFav = (id) => {

        const url = `http://localhost:8080/api/recipe/deleteFromFav?id=${id}`;

        const headers = {"Content-Type": "application/json",
            "Authorization": getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME)};

        axios.delete(url, { headers })
            .then(res => {

                if (res.status === 200){
                    setFavListVisible(false);
                    getFavRecipes();
                }
            })
    };

    const onHide = () => {
        setFavListVisible(false);
    };

    return (
        <div>

            <DialogWindow visible={isFavListVisible}
                          header="Favorite recipes"
                          draggable={false}
                          onHide={onHide}>

                {
                    isFavListVisible ?

                        <FavoriteRecipeList favRecipes={favRecipes}
                                            handleRecipeShow={handleRecipeShow}
                                            handleRecipeRemoveFav={handleRecipeRemoveFav}/>
                        : null
                }

            </DialogWindow>

            <SlideMenu ref={menu} model={items} popup>Menu</SlideMenu>
        </div>
    )
};

export default AvatarDropdownMenu;