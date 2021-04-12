import React from "react";
import {Dialog} from "primereact/dialog";

const DialogWindow = ({visible, header, footer, onHide, draggable, children}) => {

    return (
        <Dialog header={header}
                visible={visible}
                draggable={draggable}
                footer={footer}
                onHide={onHide}
                breakpoints={{'960px': '75vw'}}
                style={{width: '50vw'}}
                baseZIndex={1000}>
            {children}
        </Dialog>
    )
};

export default DialogWindow;