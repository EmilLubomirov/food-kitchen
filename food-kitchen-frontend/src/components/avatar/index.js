import React, {useRef} from "react";
import {Avatar} from "primereact/avatar";
import AvatarDropdownMenu from "../avatar-dropdown-menu";

const AvatarComponent = ({image, handleLogout}) => {

    const menu = useRef(null);

    return (
        <div>

            <div className="card">

                {
                    image ?
                        (
                            <Avatar className="p-mr-2" size="large"
                                    image={image}
                                    shape="circle"
                                    onClick={(event) => menu.current.toggle(event)}/>
                        )
                        : (
                            <Avatar icon="pi pi-user" className="p-mr-2" size="large"
                                    shape="circle"
                                    onClick={(event) => menu.current.toggle(event)}/>
                        )
                }
            </div>

            <AvatarDropdownMenu menu={menu}
                                handleLogout={handleLogout}/>
        </div>
    )
};

export default AvatarComponent;