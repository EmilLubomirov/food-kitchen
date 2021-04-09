import React from "react";
import {Avatar} from "primereact/avatar";
import LinkComponent from "../link";

const AvatarComponent = ({image}) => {

    const style = { backgroundColor: '#2196F3', color: '#ffffff' };

    return (
        <LinkComponent path='/profile'>
            <div className="p-col-12 p-md-4">
                <div className="card">

                    {
                        image ?
                            (
                                <Avatar className="p-mr-2" size="large"
                                image={image}
                                    style={style} shape="circle" />
                                    )
                            : (
                                <Avatar icon="pi pi-user" className="p-mr-2" size="large"
                                        style={style}
                                        shape="circle" />
                            )
                    }
                </div>
            </div>
        </LinkComponent>
    )
};

export default AvatarComponent;