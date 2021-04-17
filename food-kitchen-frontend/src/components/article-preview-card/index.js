import React from "react";
import {useHistory} from "react-router-dom";
import {Card} from "primereact/card";
import {Button} from "primereact/button";
import styled from "styled-components";

const StyledCard = styled(Card)`
    display: flex;
    justify-content: space-between;
    width: 90%;
    margin: 20px auto;
`;

const ArticlePreviewCard = ({ id, title, description, imageUrl }) => {

    const history = useHistory();

    const styleDescription = {
        lineHeight: '1.5',
        overflow: "hidden",
        textOverflow: "ellipsis",
        display: "-webkit-box",
        "-webkitLineClamp": "3",
        "-webkit-box-orient": "vertical"
    };

    const renderHeader = (imageUrl) => {

        return (
            <img alt="Card" src={imageUrl} style={{marginTop: "50%"}}
                 onError={(e) =>
                     e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} />
        )
    };

    const renderFooter = () => {

        return (
            <Button label="Read more" onClick={handleSubmit} style={{float: "right"}}/>
        )
    };

    const handleSubmit = () => {
        history.push(`/article/${id}`)
    };

    return (

        <StyledCard title={title}
                    header={renderHeader(imageUrl)}
                    footer ={renderFooter}>
            <p className="p-m-0" style={styleDescription}>
                {description}
            </p>
        </StyledCard>
    )
};

export default ArticlePreviewCard;