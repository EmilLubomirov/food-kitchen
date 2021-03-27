import React from "react";
import {Link} from "react-router-dom";

const Index = ({path, title, children}) =>{

    return (
        <div data-test-id={`link-${title}`}>
            <Link to={path}>
                {children}
                {title}
            </Link>
        </div>
    )
};

export default Index;