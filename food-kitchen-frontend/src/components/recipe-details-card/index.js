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

const RecipeDetailsCard = ({recipe}) => {

    const [rating, setRating] = useState(null);
    const [isRatingReadOnly, setRatingReadOnly] = useState(false);

    const {id, title, imageUrl, description, publisher, voters } = recipe;
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
            <Rating value={rating} cancel={false} readOnly={isRatingReadOnly} onChange={(e) => handleRatingChange(e.value)}  />
        </div> : null

    );

    useEffect(() => {

        if (rating){
            setRatingReadOnly(true);
        }

    }, [rating]);

    return (
        <Wrapper>
            <Card title={title} subTitle="Subtitle"
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