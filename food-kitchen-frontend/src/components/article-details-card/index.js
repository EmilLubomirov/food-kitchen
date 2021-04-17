import React from "react";
import {Card} from "primereact/card";
import styled from "styled-components";

const Wrapper = styled.div`
    display: flex;
    justify-content: center;
    margin: 30px;
`;

const StyledP = styled.p`
    line-height: 1.5;
    font-style: italic;
    font-size: 18px;
    font-weight: bolder;
`;

const ArticleDetailsCard = ({article}) => {

    const {title, description, imageUrl, publishedOn} = article;

    const header = (
        <img src={imageUrl}
             alt={title}
             onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} />
    );

    return (
        <Wrapper>
            <Card title={title}
                  subTitle={`Published on: ${publishedOn}`}
                  style={{ width: '70%' }}
                  header={header}>
                <p className="p-m-0" style={{lineHeight: '1.5', fontSize: "18px"}}>{description}</p>
                <StyledP className="p-m-0">Published by: Admin</StyledP>
            </Card>
        </Wrapper>
    )

};

export default ArticleDetailsCard;