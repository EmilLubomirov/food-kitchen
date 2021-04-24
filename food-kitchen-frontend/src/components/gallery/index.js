import React, {useEffect, useState} from "react";
import {Galleria} from "primereact/galleria";
import styled from "styled-components";

const StyledImage = styled.img`
    
    max-width: 100%;
    display: block;
    
     @media screen and (max-width: 1000px) {
        
    }
`;
const Gallery = () => {

    const [images, setImages] = useState([]);

    const getImages = () => {

        //TODO: GET IMAGES FROM SOMEWHERE

        const imgs = [
            {
                src: 'https://images.unsplash.com/photo-1590794057443-c799b2ebdfbf?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1888&q=800'
            },
            {
                src: 'http://www.tikatours.com/library/content/food-wine.jpg'
            },
            {
                src: 'https://www.fiin.co.uk/sites/default/files/styles/banner_homepage/public/carousels/fiin-site-05-opt.jpg?itok=Olg7ZyBE'
            }
        ];

        setImages(imgs);
    };

    const itemTemplate = (item) => {
        return <StyledImage src={item.src} alt="img" />;
    };

    useEffect(() => {
        getImages();
    }, []);

    return (
        <div>
            <div className="card">
                <Galleria value={images}
                          showThumbnails={false}
                          autoPlay circular showItemNavigators
                          item={itemTemplate} />
            </div>
        </div>
    );
};

export default Gallery;