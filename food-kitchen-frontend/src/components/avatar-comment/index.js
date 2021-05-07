import React from "react";
import {Avatar} from "primereact/avatar";

const AvatarComment = ({image}) => {

    return (
        <div>

            <div className="card">

                {
                    image ?
                        (
                            <Avatar className="p-mr-2" size="large"
                                    image={image}
                                    shape="circle"/>
                        )
                        : (
                            <Avatar className="p-mr-2" size="large"
                                    image="https://icon-library.com/images/default-user-icon/default-user-icon-13.jpg"
                                    shape="circle"/>
                        )
                }
            </div>
        </div>
    )
};

export default AvatarComment;