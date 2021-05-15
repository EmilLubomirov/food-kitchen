import React, {useCallback, useEffect, useRef, useState} from "react";
import  {useLocation, useHistory} from "react-router-dom";
import PageLayout from "../../components/page-layout";
import axios from "axios";
import RecipeCards from "../../components/recipe-cards";
import ScrollTopComponent from "../../components/scroll-top";
import {Checkbox} from "primereact/checkbox";
import styled from "styled-components";
import {Toast} from "primereact/toast";
import {Button} from "primereact/button";

const FilterWrapper = styled.div`
    display: flex;
    justify-content: flex-end;
    margin-bottom: 40px;
    
    @media screen and (max-width: 400px) {
        flex-flow: column;
    }
`;

const CheckboxWrapper = styled.div`
    margin-right: 25px;
    font-weight: bolder;
    font-size: 17px;
    
    @media screen and (max-width: 400px) {
        margin-bottom: 15px;
    }
`;

const StyledLoadBtn = styled(Button)`
    display: block;
    margin: 30px auto;
    width: 15%;
`;

const RecipePage = () => {

    const DEFAULT_RECIPE_LIMIT = 9;

    const [limit, setLimit] = useState(
        parseInt(sessionStorage.getItem("recipeLimit")) || DEFAULT_RECIPE_LIMIT);

    const [recipes, setRecipes] = useState([]);
    const [categories, setCategories] = useState([]);
    const [chosenCategories, setChosenCategories] = useState(
        JSON.parse(sessionStorage.getItem("chosenCategories")) || []);

    const [isLoading, setLoading] = useState(true);

    const toast = useRef(null);

    const history = useHistory();
    const location = useLocation();
    const { state } = location;

    const [message, ] = useState({
        isOpen: state ? !!state.message : false,
        value: state ? state.message || "" : "",
        type: state ? state.type || "" : ""
    });

    const getRecipes = useCallback(() => {

        axios.post(`http://localhost:8080/api/recipe/filter`, {
            categories: chosenCategories,
            limit,
        })
            .then(res => {
                if (res.status === 200){

                    try {
                        setRecipes(res.data._embedded.recipeServiceModelList);
                    }

                    catch(e){
                        setRecipes([])
                    }
                }
            })
    }, [limit, chosenCategories]);

    const getFoodCategories = useCallback(() => {

        axios.get('http://localhost:8080/api/recipe/category')
            .then(res => {
                if (res.status === 200) {
                    setCategories(res.data._embedded.foodCategoryServiceModelList);
                }
            });
    }, []);

    const handleScrollPosition = () => {

        const scrollPosition = sessionStorage.getItem("scrollPosition");

        if (scrollPosition) {
            window.scrollTo(0, parseInt(scrollPosition));
            sessionStorage.removeItem("scrollPosition");
        }
    };

    const handleChange = (e) => {

        const { value, checked } = e;

        let storageCategories = JSON.parse(sessionStorage.getItem("chosenCategories")) || [];

        if (checked){

            storageCategories.push(value);

            setChosenCategories((prevState) => {
                return [...prevState, value];
            })
        }

        else {
            storageCategories = storageCategories.filter(c => c !== value);
            setChosenCategories(chosenCategories.filter(c => c !== value));
        }

        setLimit(DEFAULT_RECIPE_LIMIT);
        sessionStorage.setItem("chosenCategories", JSON.stringify(storageCategories));
    };

    const handleClick = () => {
        setLimit((prevLimit) => {
            return prevLimit + DEFAULT_RECIPE_LIMIT;
        })
    };

    const showMessage = useCallback(() => {

        if (toast.current){
            toast.current.show({
                severity: message.type,
                summary: message.value,
                })
        }
    },[message.type, message.value]);

    useEffect(() => {
        getRecipes();
        getFoodCategories();
    }, [getRecipes, getFoodCategories]);

    useEffect(() => {

        if (!isLoading){
            handleScrollPosition();
        }
    });

    useEffect(() => {

        if (recipes.length > 0){
            setLoading(false);
        }

    }, [recipes]);

    const updateLocationState = useCallback(() => {

        let { state } = location;
        delete state.message;
        delete state.type;

        history.replace({ ...history.location, state });

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    useEffect(() => {

        if (message.isOpen){
            showMessage();
            updateLocationState();
        }

    }, [message, showMessage, updateLocationState]);

    return (
        <PageLayout>
            <h1>Recipes</h1>

            <FilterWrapper>
                {
                    categories.length > 0 ? categories.map(c => {

                        return  (
                            <CheckboxWrapper key={c.name} className="p-col-12">
                            <Checkbox inputId="cb1"
                                      value={c.name}
                                      onChange={handleChange}
                                      style={{marginRight: "5px"}}
                                      checked={chosenCategories.includes(c.name)}>c
                            </Checkbox>
                            <label htmlFor="cb1" className="p-checkbox-label">{c.name}</label>
                        </CheckboxWrapper>)
                    }) : null
                }
            </FilterWrapper>

            {
                isLoading ? <div style={{height: "500px"}}/> :
                    (
                        <>
                            <RecipeCards recipes={recipes}/>
                            <StyledLoadBtn label="Load more" onClick={handleClick}/>
                        </>
                    )
            }

            <ScrollTopComponent threshold={700}/>

            <Toast ref={toast} position="bottom-right"/>

        </PageLayout>
    )
};

export default RecipePage;