import React, {useEffect, useState} from "react";
import {PanelMenu} from "primereact/panelmenu";
import PageLayout from "../../components/page-layout";
import styled from "styled-components";
import axios from "axios";
import {Button} from "primereact/button";

const Wrapper = styled.div`
   display: flex;
   // flex-flow: column wrap;
   justify-content: space-around;
   align-items: center;
   height: 400px;
`;

const ForumPage = () => {

    const [categories, setCategories] = useState([]);

    const getForumCategories = () => {

        axios.get('http://localhost:8080/api/forum')
            .then(res => {
                if (res.status === 200){
                    setCategories(res.data);
                }
            })
    };

    const getItems = () => {
        return categories.map(c => { return {label: c.title, items: getQuestionItems(c.topics)}});
    };

    const handleSubmit = () => {

        //TODO...
    };

    const getQuestionItems = (c) => {

        const arr = c.map(c1 => {return {label: c1.title}});
        arr.push({label: <Button label='Add Topic' onClick={handleSubmit}/>});

        return arr;
    };

    useEffect(() => {
        getForumCategories();
    }, []);

    return(

        <PageLayout>
            <Wrapper className="card">
                <h1>Choose category</h1>
                <PanelMenu model={getItems(categories)} style={{ width: '44rem' }}/>
            </Wrapper>
        </PageLayout>
    )
};

export default ForumPage;