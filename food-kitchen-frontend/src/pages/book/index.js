import React, {useContext, useEffect, useState} from "react";
import axios from "axios";
import PageLayout from "../../components/page-layout";
import AuthContext from "../../AuthContext";
import {Button} from "primereact/button";
import DialogWindow from "../../components/dialog";
import AddBookForm from "../../components/add-book-form";
import {getCookie} from "../../utils/cookie";
import {Accordion, AccordionTab} from "primereact/accordion";
import styled from "styled-components";

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

    const getBooks = () => {

        axios.get('http://localhost:8080/api/book')
            .then(res => {

                if (res.status === 200){
                    setBooks(res.data);
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
                }
        })
    };

    const renderHeader = (book) => {

        return (

            <div style={{display: "flex", alignItems: "center"}}>

                <img style={{maxWidth: "5%", margin: "0 70px"}}
                     src={book.imageUrl} alt={book.title}/>

                <StyledHeading>Title: {book.title}</StyledHeading>
                <StyledHeading>Author: {book.author}</StyledHeading>
                <StyledHeading>Year: {book.year}</StyledHeading>
            </div>

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
                                           {/*<div>*/}
                                           {/*    <iframe title="coordinates"*/}
                                           {/*            src={`https://www.google.com/maps/search/?api=1&query=58.698017,-152.522067`}*/}
                                           {/*            width="600" height="450" style={{maxWidth: "100%"}} frameBorder="0"*/}
                                           {/*            allowFullScreen=""*/}
                                           {/*            aria-hidden="false"*/}
                                           {/*            tabIndex="0">*/}
                                           {/*    </iframe>*/}
                                           {/*</div>*/}
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
        </PageLayout>
    )
};

export default BookPage;