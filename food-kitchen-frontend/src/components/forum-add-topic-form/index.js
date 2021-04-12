import React from "react";
import {InputText} from "primereact/inputtext";
import {InputTextarea} from "primereact/inputtextarea";
import styled from "styled-components";

const StyledForm = styled.form`
    width: fit-content;
    margin: 20px auto;
`;

const ForumAddTopicForm = ({categoryName, topicName, setTopicName, question, setQuestion}) => {

    return (
        <StyledForm>
            <div>
                <h4>Category</h4>
                <InputText style={{width: '300px'}} value={categoryName} disabled />
            </div>

            <div>
                <h4>Topic name</h4>
                <InputText style={{width: '300px'}}
                           value={topicName}
                           onChange={(e) => setTopicName(e.target.value)}/>
            </div>

            <div>
                <h4>Question</h4>
                <InputTextarea rows={3}
                               cols={50}
                               value={question}
                               onChange={(e) => setQuestion(e.target.value)} autoResize />
            </div>
        </StyledForm>
    )
};

export default ForumAddTopicForm;