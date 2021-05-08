import React, {useCallback, useEffect, useRef, useState} from "react";
import {useHistory} from "react-router-dom";
import {beginUpload} from "../../utils/cloudinaryService";
import {CloudinaryContext} from "cloudinary-react"
import PageLayout from "../../components/page-layout";
import {InputText} from "primereact/inputtext";
import {InputTextarea} from "primereact/inputtextarea";
import {Button} from "primereact/button";
import axios from "axios";
import styled from "styled-components";
import {getCookie} from "../../utils/cookie";
import {Dropdown} from "primereact/dropdown";
import {MESSAGE_TYPES, MESSAGES} from "../../utils/constants";
import {Toast} from "primereact/toast";
import FormWrapper from "../../components/form-wrapper";

const Wrapper = styled.div`
    height: fit-content;
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    align-items: center;
`;

const StyledBtn = styled(Button)`
    width: 30%;
    height: 45px;
`;

const AddRecipePage = () => {

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [imageUrl, setImageUrl] = useState(null);
    const [foodCategories, setFoodCategories] = useState([]);
    const [selectedFoodCategory, setSelectedFoodCategory] = useState(null);

    const toast = useRef(null);
    const history = useHistory();

    const handleSubmit = () => {

        if (title.trim().length === 0 ||
            description.trim().length === 0 ||
            !selectedFoodCategory){
            showMessage(MESSAGE_TYPES.error, MESSAGES.emptyFields);
            return;
        }

        const url = 'http://localhost:8080/api/recipe';
        const headers =  { 'Content-Type': 'application/json',
                            'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME) };

        const body = {
            title,
            description,
            categories: [selectedFoodCategory],
            imageUrl,
        };

        axios.post(url, body, { headers })
            .then(res => {

                if (res.status === 201) {
                history.push(`/recipe/${res.data.id}`, {
                    message: MESSAGES.addedRecipe,
                    type: MESSAGE_TYPES.success
                });
            }
        }).catch(() => {
            showMessage(MESSAGE_TYPES.error, MESSAGES.invalidFieldData);
        })
    };

    const showMessage = useCallback((type, value) => {

        if (toast.current){

            toast.current.show({
                severity: type,
                summary: value,
            })
        }
    },[]);

    const getFoodCategories = () => {

        axios.get('http://localhost:8080/api/recipe/category')
            .then(res => {

                if (res.status === 200){
                    setFoodCategories(res.data._embedded.foodCategoryServiceModelList);
                }
            })
    };

    useEffect(() => {
        getFoodCategories();
    }, []);

    return(
        <PageLayout>
            <CloudinaryContext cloudName={process.env.REACT_APP_CLOUD_NAME}>
                <FormWrapper>
                    <Wrapper className="card">
                        <h1>Add recipe</h1>

                        <h4>Title</h4>
                        <span className="p-float-label">
                    <InputText id="title" value={title} onChange={(e) => setTitle(e.target.value)} />
                    <label htmlFor="title">Title</label>
                    </span>

                        <h5>Category</h5>
                        <Dropdown value={selectedFoodCategory}
                                  options={foodCategories}
                                  onChange={(e) => setSelectedFoodCategory(e.value)}
                                  optionLabel="name" placeholder="Select a Category" />

                        <h4>Description</h4>
                        <InputTextarea value={description} onChange={(e) => setDescription(e.target.value)}
                                       rows={5} cols={50} autoResize />

                        <Button style={{margin: "15px 0"}} onClick={() => beginUpload(setImageUrl)} icon="pi pi-plus" label="Add photo"/>
                        <StyledBtn onClick={handleSubmit} label="Save"/>

                        <Toast ref={toast} position="bottom-right"/>
                    </Wrapper>
                </FormWrapper>
            </CloudinaryContext>
        </PageLayout>
    )
};

export default AddRecipePage;