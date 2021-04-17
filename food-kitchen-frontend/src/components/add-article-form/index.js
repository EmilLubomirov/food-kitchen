import React from "react";
import {InputText} from "primereact/inputtext";
import {InputTextarea} from "primereact/inputtextarea";
import styled from "styled-components";
import {CloudinaryContext} from "cloudinary-react";
import {beginUpload} from "../../utils/cloudinaryService";
import {Button} from "primereact/button";

const StyledForm = styled.form`
    width: fit-content;
    margin: 20px auto;
`;

const AddArticleForm = ({title, setTitle,
                         description, setDescription,
                         setArticleImageUrl}) => {

    return (
        <CloudinaryContext cloudName={process.env.REACT_APP_CLOUD_NAME}>
            <StyledForm>
                <div>
                    <h4>Title</h4>
                    <InputText style={{width: '300px'}} value={title}
                               onChange={(e) => setTitle(e.target.value)} />
                </div>

                <div>
                    <h4>Description</h4>
                    <InputTextarea rows={3}
                                   cols={50}
                                   value={description}
                                   onChange={(e) => setDescription(e.target.value)}
                                   autoResize />
                </div>
            </StyledForm>

            <Button onClick={() => beginUpload(setArticleImageUrl)}
                    label="Add article image" style={{margin: "0 35%"}}/>
        </CloudinaryContext>
    )
};

export default AddArticleForm;