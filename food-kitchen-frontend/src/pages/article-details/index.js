import React, {useCallback, useEffect, useState} from "react";
import {useParams} from "react-router-dom"
import PageLayout from "../../components/page-layout";
import ArticleDetailsCard from "../../components/article-details-card";
import axios from "axios";

const ArticleDetailsPage = () => {

    const [article, setArticle] = useState(null);
    const [isLoading, setLoading] = useState(true);

    const params = useParams();

    const getArticle = useCallback(() => {

        const id = params.articleId;

        axios.get(`http://localhost:8080/api/article/${id}`)
            .then(res => {

                if (res.status === 200){
                    setArticle(res.data);
                }
            })
    }, [params.articleId]);

    useEffect(() => {

        if (article){
            setLoading(false);
        }
    }, [article]);

    useEffect(() => {
        getArticle();
    }, [getArticle]);

    return (
        <PageLayout>
            {
                isLoading ? <div style={{height: "500px"}}/> :
                    <ArticleDetailsCard article={article}/>
            }
        </PageLayout>
    )
};

export default ArticleDetailsPage;