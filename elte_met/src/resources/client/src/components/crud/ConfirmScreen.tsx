import * as React from "react";
import * as css from "./ConfirmScreen.css";

interface Props {
    onDelete: () => void;
    onCancel: () => void;
}

export default ({ onDelete, onCancel }: Props) => (
    <div className={css.root}>
        <p>U sure?</p>
        <button onClick={onDelete}>Yes, delete!</button>
        <button onClick={onCancel}>Cancel</button>
    </div>
);
