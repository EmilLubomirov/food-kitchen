import React, {useState} from "react";
import {useHistory} from "react-router-dom";
import {openUploadWidget} from "../../utils/cloudinaryService";
import {CloudinaryContext} from "cloudinary-react"
import PageLayout from "../../components/page-layout";
import {InputText} from "primereact/inputtext";
import {InputTextarea} from "primereact/inputtextarea";
import {Button} from "primereact/button";
import axios from "axios";
import styled from "styled-components";
import {getCookie} from "../../utils/cookie";

const Wrapper = styled.div`
    height: 500px;
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    align-items: center;
`;

const AddRecipePage = () => {

    const history = useHistory();

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [imageUrl, setImageUrl] = useState(null);

    const handleSubmit = () => {

        const url = 'http://localhost:8080/api/recipe';
        const headers =  { 'Content-Type': 'application/json',
                            'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME) };

        const body = {
            title,
            description,
            imageUrl,
        };

        axios.post(url, body, { headers })
            .then(res => {

            if (res.status === 201) {
                history.push('/recipe');
            }
        })
    };

    const beginUpload = () => {

        const uploadOptions = {
            cloudName: process.env.REACT_APP_CLOUD_NAME,
            // tags: [tag],
            uploadPreset: process.env.REACT_APP_UPLOAD_PRESET
        };

        openUploadWidget(uploadOptions, (error, photos) => {
            if (!error) {

                if(photos.event === "success"){
                    // setImagesUrl(imagesUrl => [...imagesUrl, photos.info.secure_url]);
                    setImageUrl(photos.info.secure_url);
                }
            } else {
                console.log(error);
            }
        });
    };

    return(
        <PageLayout>
            <CloudinaryContext cloudName={process.env.REACT_APP_CLOUD_NAME}>
                <Wrapper className="card">
                    <h1>Add recipe</h1>
                    <h4>Title</h4>
                    <span className="p-float-label">
                    <InputText id="title" value={title} onChange={(e) => setTitle(e.target.value)} />
                    <label htmlFor="title">Title</label>
                </span>

                    <h4>Description</h4>
                    <InputTextarea value={description} onChange={(e) => setDescription(e.target.value)}
                                   rows={5} cols={50} autoResize />
                    <Button onClick={() => beginUpload()} label={"Add photo"}/>
                    <Button onClick={handleSubmit} label="Submit"/>
                </Wrapper>
            </CloudinaryContext>
        </PageLayout>
    )
};

export default AddRecipePage;