import React, {useCallback, useContext, useEffect, useRef, useState} from "react"
import PageLayout from "../../components/page-layout";
import axios from "axios";
import AuthContext from "../../AuthContext";
import {Button} from "primereact/button";
import DialogWindow from "../../components/dialog";
import {getCookie} from "../../utils/cookie";
import AddArticleForm from "../../components/add-article-form";
import styled from "styled-components";
import ArticlePreviewCard from "../../components/article-preview-card";
import {Toast} from "primereact/toast";
import {MESSAGE_TYPES, MESSAGES} from "../../utils/constants";

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
    const [isLoading, setLoading] = useState(true);

    const context = useContext(AuthContext);

    const toast = useRef(null);
    const formToast = useRef(null);

    const getArticles = () => {

        axios.get('http://localhost:8080/api/article')
            .then(res => {

                if (res.status === 200){
                    setArticles(res.data._embedded.articleServiceModelList);
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

        if (title.trim().length === 0 || description.trim().length === 0){
            showMessage(formToast, MESSAGE_TYPES.error, MESSAGES.emptyFields);
            return;
        }

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
                    showMessage(toast, MESSAGE_TYPES.success, MESSAGES.addedArticle)
                }
            }).catch(() => {
            showMessage(formToast, MESSAGE_TYPES.error, MESSAGES.invalidFieldData)
        });
    };

    const showMessage = useCallback((toast, type, value) => {

        if (toast.current){

            toast.current.show({
                severity: type,
                summary: value,
            })
        }
    },[]);

    useEffect(() => {

        if (articles.length > 0){
            setLoading(false);
        }
    }, [articles]);

    useEffect(() => {
        getArticles();
    }, []);

    return (
        <PageLayout>
            <h1>Articles</h1>

            {
                isLoading ? <div style={{height: "500px"}}/> :

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

                            <Toast ref={formToast} position="bottom-right"/>
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
                                    <StyledAddBtn label="Add Article" icon="pi pi-plus" onClick={handleSubmit}/>
                                ) : null : null
                        }

                        <Toast ref={toast} position="bottom-right"/>
                    </Wrapper>
            }

        </PageLayout>
    )
};

export default ArticlePage;