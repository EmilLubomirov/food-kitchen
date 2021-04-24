import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import {PanelMenu} from "primereact/panelmenu";
import PageLayout from "../../components/page-layout";
import styled from "styled-components";
import axios from "axios";
import {Button} from "primereact/button";
import DialogWindow from "../../components/dialog";
import ForumAddTopicForm from "../../components/forum-add-topic-form";
import {getCookie} from "../../utils/cookie";
import LinkComponent from "../../components/link";

const Wrapper = styled.div`
   display: flex;
   flex-flow: column;
   align-items: center;
`;

const StyledPanelMenu = styled(PanelMenu)`
    width: 44rem;
    
     @media screen and (max-width: 700px) {
        width: 100%;
    }
`;

const ForumPage = () => {

    const [categories, setCategories] = useState([]);
    const [visibleTopicDialog, setVisibleTopicDialog] = useState(false);
    const [categoryName, setCategoryName] = useState('');
    const [topicName, setTopicName] = useState('');
    const [question, setQuestion] = useState('');

    const history = useHistory();

    const getForumCategories = () => {

        axios.get('http://localhost:8080/api/forum')
            .then(res => {
                if (res.status === 200){
                    setCategories(res.data);
                }
            })
    };

    const getItems = () => {
        return categories.map(c => { return {label: c.title, items: getQuestionItems(c.title, c.topics)}});
    };

    const getQuestionItems = (catName, topics) => {

        const arr = topics.map(t => {return {label: <LinkComponent path={`/forum/${catName}/${t.id}`}
                                                                   title={t.title}/>}});
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

        const headers = {'Content-Type': 'application/json',
                         'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME)};

        axios.post(url, body, { headers })
            .then(res => {

                const { id } = res.data;

                if (res.status === 201){
                    history.push(`/forum/${categoryName}/${id}`)
                }
        });
    };

    useEffect(() => {

        if (!visibleTopicDialog){
            getForumCategories();
        }
    }, [visibleTopicDialog]);

    return(

        <PageLayout>
            <Wrapper className="card">
                <h1 style={{margin: "50px"}}>Choose category</h1>
                <StyledPanelMenu model={getItems(categories)}/>

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