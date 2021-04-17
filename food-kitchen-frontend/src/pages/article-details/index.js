import React, {useCallback, useEffect, useState} from "react";
import {useParams} from "react-router-dom"
import PageLayout from "../../components/page-layout";
import ArticleDetailsCard from "../../components/article-details-card";
import axios from "axios";

const ArticleDetailsPage = () => {

    const [article, setArticle] = useState(null);

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
        getArticle();
    }, [getArticle]);

    return (
        <PageLayout>
            {
                article ?
                    <ArticleDetailsCard article={article}/> : null
            }
        </PageLayout>
    )
};

export default ArticleDetailsPage;