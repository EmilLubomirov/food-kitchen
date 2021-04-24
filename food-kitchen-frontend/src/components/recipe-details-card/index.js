import React, {useContext, useEffect, useState} from "react";
import {Card} from "primereact/card";
import styled from "styled-components";
import {Rating} from "primereact/rating";
import axios from "axios";
import {getCookie} from "../../utils/cookie";
import AuthContext from "../../AuthContext";

const Wrapper = styled.div`
    display: flex;
    justify-content: center;
    margin: 30px;
`;

const StyledFavIcon = styled.i`

    font-size: 2rem;
    transition: transform 0.4s;
    
    &:hover{
        cursor: pointer;
        transform: scale(1.15);
    }
`;

const RecipeDetailsCard = ({recipe}) => {

    const [rating, setRating] = useState(null);
    const [isRatingReadOnly, setRatingReadOnly] = useState(false);
    const [isFavVisible, setFavVisible] = useState(undefined);

    const { id, title, imageUrl, description, publisher, voters, fans } = recipe;
    const context = useContext(AuthContext);

    const handleRatingChange = (e) => {

        const url = `http://localhost:8080/api/recipe`;

        const body = {
            id,
            rating: e
        };

        const headers = {'Content-Type': 'application/json',
                         'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME)};

        axios.patch(url, body, { headers })
            .then(res => {

                if (res.status === 200){
                    setRating(e);
                }
            });
    };

    const handleFavClick = () => {

        const url = `http://localhost:8080/api/recipe/addToFavorites`;

        const headers = {'Content-Type': 'application/json',
                         'Authorization': getCookie(process.env.REACT_APP_AUTH_COOKIE_NAME)};

        axios.patch(url, { id }, { headers })
            .then(res => {

                if (res.status === 200){
                    setFavVisible(false);
                }
            });
    };

    const renderTitle = (

        context.user ?
            fans.some(f => f.username === context.user.username) ?
                <span>{title}</span> :
                <div style={{display: "flex", justifyContent: "space-between"}}>
                    <span>{title}</span>

                    <div onClick={handleFavClick}
                         style={
                             isFavVisible === false ? {display: "none"} :
                             {width: "fit-content"}}>
                        <StyledFavIcon className="pi pi-heart p-mr-4 p-text-secondary p-overlay-badge"/>
                    </div>
                </div> : null
    );

    const header = (
        <img src={imageUrl}
             alt={title}
             onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} />
    );

    const footer = (

        context.user ?
            voters.some(v => v.username === context.user.username) ?
                null :
        <div>
            <h5>Did you like this recipe?</h5>
            <Rating value={rating}
                    cancel={false}
                    readOnly={isRatingReadOnly}
                    onChange={(e) => handleRatingChange(e.value)}  />
        </div> : null

    );

    useEffect(() => {

        if (rating){
            setRatingReadOnly(true);
        }

    }, [rating]);

    return (
        <Wrapper>
            <Card title={renderTitle} subTitle="Subtitle"
                  style={{ width: '25em' }}
                  header={header}
                  footer={footer}>
                <p className="p-m-0" style={{lineHeight: '1.5'}}>{description}</p>
                <p className="p-m-0" style={{lineHeight: '1.5'}}>Published by: {publisher}</p>
            </Card>
        </Wrapper>
    )

};

export default RecipeDetailsCard;