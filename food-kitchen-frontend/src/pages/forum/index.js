import React, {useEffect, useState} from "react";
import {PanelMenu} from "primereact/panelmenu";
import PageLayout from "../../components/page-layout";
import styled from "styled-components";
import axios from "axios";
import {Button} from "primereact/button";
import DialogWindow from "../../components/dialog";
import ForumAddTopicForm from "../../components/forum-add-topic-form";

const Wrapper = styled.div`
   display: flex;
   // flex-flow: column wrap;
   justify-content: space-around;
   align-items: center;
   height: 400px;
`;

const ForumPage = () => {

    const [categories, setCategories] = useState([]);
    const [visibleTopicDialog, setVisibleTopicDialog] = useState(false);
    const [categoryName, setCategoryName] = useState('');
    const [topicName, setTopicName] = useState('');
    const [question, setQuestion] = useState('');

    const getForumCategories = () => {

        axios.get('http://localhost:8080/api/forum')
            .then(res => {
                if (res.status === 200){
                    setCategories(res.data);
                }
            })
    };

    const getItems = () => {
        console.log(categories);

        return categories.map(c => { return {label: c.title, items: getQuestionItems(c.title, c.topics)}});
    };

    const getQuestionItems = (catName, topics) => {

        const arr = topics.map(t => {return {label: t.title}});
        arr.push({label: <Button label='Add Topic' onClick={() => handleSubmit(catName)}/>});

        return arr;
    };

    const handleSubmit = (catName) => {

        setVisibleTopicDialog(true);
        setCategoryName(catName);
    };

    const onHide = () => {
      setVisibleTopicDialog(false);
    };

    const renderFooter = () => {
        return (
            <Button label="Save" onClick={handleSave}/>
        )
    };

    const handleSave = () => {

        const url = 'http://localhost:8080/api/forum/addTopic';

        const body = {
          categoryName,
          topicName,
          question
        };

        const headers = {'Content-Type': 'application/json'};

        axios.post(url, body, { headers })
            .then(res => {

                if (res.status === 201){
                    console.log('Saved');
                }
        });

        setVisibleTopicDialog(false);
    };

    useEffect(() => {
        getForumCategories();
    }, []);

    return(

        <PageLayout>
            <Wrapper className="card">
                <h1>Choose category</h1>
                <PanelMenu model={getItems(categories)} style={{ width: '44rem' }}/>

                <DialogWindow visible={visibleTopicDialog}
                              header="Add Topic"
                              footer={renderFooter}
                              draggable={false}
                              onHide={onHide}
                                style={{display: 'flex', flexFlow: 'column', alignItems: 'center'}}>

                    <ForumAddTopicForm categoryName={categoryName}
                                       topicName={topicName}
                                       setTopicName={setTopicName}
                                       question={question}
                                       setQuestion={setQuestion}/>

                </DialogWindow>
            </Wrapper>
        </PageLayout>
    )
};

export default ForumPage;