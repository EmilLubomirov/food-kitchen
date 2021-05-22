import React, {useCallback, useEffect, useState, useContext} from "react";
import {useParams} from "react-router-dom";
import PageLayout from "../../components/page-layout";
import axios from "axios";
import {InputTextarea} from "primereact/inputtextarea";
import {Button} from "primereact/button";
import styled from "styled-components";
import AuthContext from "../../AuthContext";
import {getCookie} from "../../utils/cookie";
import AvatarComment from "../../components/avatar-comment";

const Wrapper = styled.div`
    display: flex;
    flex-flow: column wrap;
    align-content: center;
    
    @media screen and (max-width: 600px) {
       display: block;
    }
`;

const Heading = styled.h1`
    margin: 30px auto 50px auto;
`;

const StyledComment = styled.div`
    display: flex;
    justify-content: space-between;
`;

const ForumTopicPage = () => {

    const [comments, setComments] = useState([]);
    const [topicTitle, setTopicTitle] = useState('');
    const [personalComment, setPersonalComment] = useState('');
    const [isLoading, setLoading] = useState(true);

    const { topicId } = useParams();
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

        if (personalComment.trim().length === 0){
            return;
        }

        const url = `http://localhost:8080/api/forum/${topicId}/addComment`;

        const headers =  { 'Content-Type': 'application/json',
                            'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME) };

        const body = {
            content: personalComment
        };

        axios.post(url, body, { headers })
            .then(res => {

                if (res.status === 201) {
                    getTopicComments();
                    setPersonalComment('');
                }
            })
    };

    useEffect(() =>{

        if (comments.length > 0){
            setLoading(false);
        }

    }, [comments]);

    useEffect(() => {
        getTopicComments();
    }, [getTopicComments]);


    return (
        <PageLayout>
            <Wrapper>
                <Heading>{topicTitle}</Heading>

                {
                   isLoading ? <div style={{height: "500px"}}/> :

                       comments.map((c, index) => {

                        const { avatarImageUrl, username } = c.initiator;

                       return (

                            <StyledComment key={index}>

                                <div style={{margin: "20px"}}>
                                    <AvatarComment image={avatarImageUrl}/>
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
                            </StyledComment>
                        )
                    })
                }

                <StyledComment>
                    <div style={{margin: "20px"}}>
                        <AvatarComment image={avatarImageUrl}/>
                        <span>You</span>
                    </div>

                    <div style={{margin: "20px"}}>
                        <InputTextarea
                            rows={3}
                            cols={70}
                            placeholder="Add comment ..."
                            value={personalComment}
                            onChange={(e) => setPersonalComment(e.target.value)}
                            autoResize />
                    </div>
                </StyledComment>

                <Button label="Add comment" style={{marginTop: "20px"}}
                        onClick={handleSubmit}/>
            </Wrapper>
        </PageLayout>
    )
};

export default ForumTopicPage;