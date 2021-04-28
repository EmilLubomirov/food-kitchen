import React, {useEffect, useState} from "react";
import PageLayout from "../../components/page-layout";
import axios from "axios";
import RecipeCards from "../../components/recipe-cards";
import ScrollTopComponent from "../../components/scroll-top";
import {Checkbox} from "primereact/checkbox";
import styled from "styled-components";

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

const RecipePage = () => {

    const [recipes, setRecipes] = useState([]);
    const [categories, setCategories] = useState([]);
    const [chosenCategories, setChosenCategories] = useState([]);

    const getRecipes = () => {

        axios.get('http://localhost:8080/api/recipe')
            .then(res => {
                if (res.status === 200) {
                    setRecipes(res.data._embedded.recipeServiceModelList);
                }
            });
    };

    const getFoodCategories = () => {

        axios.get('http://localhost:8080/api/recipe/category')
            .then(res => {
                if (res.status === 200) {
                    setCategories(res.data._embedded.foodCategoryServiceModelList);
                }
            });
    };

    const handleChange = (e) => {

        const { value, checked } = e;

        if (checked){
            setChosenCategories((prevState) => {
                return [...prevState, value];
            })
        }

        else {
            setChosenCategories(chosenCategories.filter(c => c !== value));
        }
    };

    useEffect(() => {

        axios.post(`http://localhost:8080/api/recipe/filter`, {
            categories: chosenCategories
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

    }, [chosenCategories]);

    useEffect(() => {
        getRecipes();
        getFoodCategories();
    }, []);

    return (
        <PageLayout>
            <h1>Recipe page</h1>

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

            <RecipeCards recipes={recipes}/>
            <ScrollTopComponent threshold={700}/>
        </PageLayout>
    )
};

export default RecipePage;