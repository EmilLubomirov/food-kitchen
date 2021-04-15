import React, {useCallback, useEffect, useState, useContext} from "react";
import {useParams} from "react-router-dom";
import PageLayout from "../../components/page-layout";
import axios from "axios";
import {InputTextarea} from "primereact/inputtextarea";
import AvatarComponent from "../../components/avatar";
import {Button} from "primereact/button";
import styled from "styled-components";
import AuthContext from "../../AuthContext";
import {getCookie} from "../../utils/cookie";

const Wrapper = styled.div`
    display: flex;
    flex-flow: column wrap;
    align-content: center;
`;
const ForumTopicPage = () => {

    const [comments, setComments] = useState([]);
    const [topicTitle, setTopicTitle] = useState('');
    const [personalComment, setPersonalComment] = useState('');

    const { categoryName, topicId } = useParams();
    const context = useContext(AuthContext);

    const { avatarImageUrl } = context.user;

    const getTopicComments = useCallback(() => {

        axios.get(`http://localhost:8080/api/forum/${topicId}`)
            .then(res => {

                if (res.status === 200){

                    setTopicTitle(res.data.title);
                    setComments(res.data.comments);
                }
            })
    }, [topicId]);

    const handleSubmit = () => {

        const url = `http://localhost:8080/api/forum/${topicId}/addComment`;

        const headers =  { 'Content-Type': 'application/json',
                            'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME) };

        const body = {
            content: personalComment
        };

        axios.post(url, body, { headers })
            .then(res => {

                if (res.status === 200) {
                    getTopicComments();
                    setPersonalComment('');
                }
            })
    };

    useEffect(() => {
        getTopicComments();
    }, [getTopicComments]);


    return (
        <PageLayout>
            <Wrapper>
                <h1>{topicTitle}</h1>

                {
                   comments.length > 0 ? comments.map((c, index) => {

                        const { avatarImageUrl, username } = c.initiator;

                       return (

                            <div key={index} style={{display: "flex"}}>

                                <div style={{margin: "20px"}}>
                                    <AvatarComponent image={avatarImageUrl}/>
                                    <span>{username === context.user.username ? 'You' : username}</span>
                                </div>

                                <div style={{margin: "20px"}}>
                                    <InputTextarea
                                        rows={3}
                                        cols={70}
                                        disabled
                                        value={c.content}
                                        autoResize />
                                </div>
                            </div>
                        )
                    }) : null
                }

                <div style={{display: "flex"}}>

                    <div style={{margin: "20px"}}>
                        <AvatarComponent image={avatarImageUrl}/>
                        <span>You</span>
                    </div>

                    <InputTextarea
                        rows={3}
                        cols={70}
                        placeholder="Add comment ..."
                        value={personalComment}
                        onChange={(e) => setPersonalComment(e.target.value)}
                        autoResize />
                </div>

                <Button label="Add comment" style={{marginTop: "20px"}} onClick={handleSubmit}/>
            </Wrapper>
        </PageLayout>
    )
};

export default ForumTopicPage;