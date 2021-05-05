import React, {useCallback, useContext, useEffect, useRef, useState} from "react";
import axios from "axios";
import PageLayout from "../../components/page-layout";
import AuthContext from "../../AuthContext";
import {Button} from "primereact/button";
import DialogWindow from "../../components/dialog";
import AddBookForm from "../../components/add-book-form";
import {getCookie} from "../../utils/cookie";
import {Accordion, AccordionTab} from "primereact/accordion";
import styled from "styled-components";
import {Toast} from "primereact/toast";
import {MESSAGE_TYPES, MESSAGES} from "../../utils/constants";

const StyledImage = styled.img`
    max-width: 5%;
    margin: 0 70px;
`;

const Wrapper = styled.div`
    
    display: flex;
    align-items: center;
    
     @media screen and (max-width: 600px) {
        flex-flow: column;
        justify-content: space-between;
        align-items: flex-start;
        height: 170px;
        
        ${StyledImage}{
            max-width: 15%;
        }
    }
`;

const StyledHeading = styled.h4`
    margin: 0 20px;
`;

const BookPage = () =>{

    const [books, setBooks] = useState([]);
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [year, setYear] = useState('');
    const [description, setDescription] = useState('');
    const [bookImageUrl, setBookImageUrl] = useState('');
    const [visible, setVisible] = useState(false);

    const context = useContext(AuthContext);

    const toast = useRef(null);
    const formToast = useRef(null);

    const getBooks = () => {

        axios.get('http://localhost:8080/api/book')
            .then(res => {

                if (res.status === 200){
                    setBooks(res.data._embedded.cookBookServiceModelList);
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

        if (title.trim().length === 0 ||
            author.trim().length === 0 ||
            year.trim().length === 0 ||
            description.trim().length === 0){

            showMessage(formToast, MESSAGE_TYPES.error, MESSAGES.emptyFields);
            return;
        }

        const url = `http://localhost:8080/api/book`;

        const body = {
            title,
            author,
            year,
            description,
            imageUrl: bookImageUrl
        };

        const headers = {"Content-Type": "application/json",
                        "Authorization": getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME)};

        axios.post(url, body, { headers })
            .then(res => {

                if (res.status === 201){
                    getBooks();
                    setVisible(false);
                    showMessage(toast, MESSAGE_TYPES.success, MESSAGES.addedBook);
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

    const renderHeader = (book) => {

        return (

            <Wrapper>
                <StyledImage src={book.imageUrl} alt={book.title}/>

                <StyledHeading>Title: {book.title}</StyledHeading>
                <StyledHeading>Author: {book.author}</StyledHeading>
                <StyledHeading>Year: {book.year}</StyledHeading>
            </Wrapper>

        )
    };

    useEffect(() => {
        getBooks();
    }, []);

    return (
        <PageLayout>
            <DialogWindow visible={visible}
                          header="Add Book"
                          footer={renderFooter}
                          draggable={false}
                          onHide={onHide}
                          style={{display: 'flex', flexFlow: 'column', alignItems: 'center'}}>

                <AddBookForm title={title} setTitle={setTitle}
                             author={author} setAuthor={setAuthor}
                             year={year} setYear={setYear}
                             description={description} setDescription={setDescription}
                             setBookImageUrl={setBookImageUrl}/>

                <Toast ref={formToast} position="bottom-right"/>

            </DialogWindow>

            <h1>Cook Books you should read</h1>
            {
                (books.length === 0 ?
                    (<div>
                        <h3>No cookbooks found</h3>
                    </div>)

                    :
                    (
                        <Accordion activeIndex={[0]}>

                            {
                                books.map(b => {

                                    return(  <AccordionTab key={b.id}
                                                           header={renderHeader(b)}>
                                        <p>{b.description}</p>
                                    </AccordionTab>
                                   )
                                })
                            }
                        </Accordion>
                    ))
            }

            {
                context.user ?
                    context.user.isAdmin ? (<Button label="Add book"
                                                    onClick={handleSubmit}/>) : null
                    : null
            }

            <Toast ref={toast} position="bottom-right"/>

        </PageLayout>
    )
};

export default BookPage;