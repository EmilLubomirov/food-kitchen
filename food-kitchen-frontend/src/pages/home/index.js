import React from "react";
import Gallery from "../../components/gallery";
import Header from "../../components/header";
import Footer from "../../components/footer";

const HomePage = () => {

    return (
        <>
            <Header/>
            <main style={{padding: 0}}>
                <Gallery/>
            </main>
            <Footer/>
        </>
    )
};

export default HomePage;