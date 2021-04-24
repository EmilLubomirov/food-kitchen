import React, {useContext, useEffect, useState} from "react"
import PageLayout from "../../components/page-layout";
import axios from "axios";
import AuthContext from "../../AuthContext";
import {Button} from "primereact/button";
import DialogWindow from "../../components/dialog";
import {getCookie} from "../../utils/cookie";
import AddArticleForm from "../../components/add-article-form";
import styled from "styled-components";
import ArticlePreviewCard from "../../components/article-preview-card";

const Wrapper = styled.div`
    display: flex;
    flex-flow: column;
    align-items: center;
`;

const StyledAddBtn = styled(Button)`
    width: 20%;
    height: 50px;
    margin: 20px;
    
     @media screen and (max-width: 400px) {
        width: auto;
    }
`;

const ArticlePage = () => {

    const [articles, setArticles] = useState([]);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [articleImageUrl, setArticleImageUrl] = useState('');
    const [visible, setVisible] = useState(false);
    const context = useContext(AuthContext);

    const getArticles = () => {

        axios.get('http://localhost:8080/api/article')
            .then(res => {

                if (res.status === 200){
                    setArticles(res.data);
                }
            })
    };

    const onHide = () => {
        setVisible(false);
    };

    const renderFooter = () => {
        return (
            <Button label="Save" onClick={handleSave}/>
        )
    };


    const handleSubmit = () => {
        setVisible(true);
    };

    const handleSave = () => {

        const url = `http://localhost:8080/api/article`;

        const body = {
            title,
            description,
            imageUrl: articleImageUrl
        };

        const headers = {"Content-Type": "application/json",
                        "Authorization": getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME)};

        axios.post(url, body, { headers })
            .then(res => {

                if (res.status === 201){
                    getArticles();
                    setVisible(false);
                }
            })
    };

    useEffect(() => {
        getArticles();
    }, []);

    return (
        <PageLayout>
            <h1>Articles</h1>
            <Wrapper>
                <DialogWindow visible={visible}
                              header="Add Article"
                              footer={renderFooter}
                              draggable={false}
                              onHide={onHide}
                              style={{display: 'flex', flexFlow: 'column', alignItems: 'center'}}>

                    <AddArticleForm title={title} setTitle={setTitle}
                                    description={description} setDescription={setDescription}
                                    setArticleImageUrl={setArticleImageUrl}/>

                </DialogWindow>

                {
                    articles.map(a => {

                        const { id, title, description, imageUrl } = a;

                        return (
                            <ArticlePreviewCard key={id}
                                                id={id}
                                                title={title}
                                                description={description}
                                                imageUrl={imageUrl}/>
                        )
                    })
                }
                {

                    context.user ?
                        context.user.isAdmin ? (
                            <StyledAddBtn label="Add Article" onClick={handleSubmit}/>
                        ) : null : null
                }
            </Wrapper>

        </PageLayout>
    )
};

export default ArticlePage;