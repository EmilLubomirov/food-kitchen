import React from "react"
import getNavigation from "../../utils/navigation";
import LinkComponent from "../../components/link";
import {Button} from "primereact/button";

const Header = () => {

    const navigation = getNavigation();

    return (
        <div>
            <Button label="Warning" className="p-button-warning" iconPos="right"/>
            {  navigation.map((link, index) => {
                return <LinkComponent key={index}
                                      path={link.path}
                                      title={link.title}/>

            })
            }
        </div>
    )
};

export default Header;