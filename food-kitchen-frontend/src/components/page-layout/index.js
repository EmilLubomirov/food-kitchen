import React from "react";
import Header from "../header";
import Footer from "../footer";

const PageLayout = (props) => {

    return (
        <>
            <Header/>
            <main>
                {props.children}
            </main>
            <Footer/>
        </>
    )
};

export default PageLayout;