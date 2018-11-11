import * as React from "react";
import * as css from "./ExceptionToast.css";

interface Props {
    message: string;
    trace: string;
    className: string;
}

export default class ExceptionToast extends React.Component<Props> {
    render() {
        const { message, trace, className } = this.props;

        return (
            <div className={css.root}>
                <p>{className}</p>
                <p>{message}</p>
                <textarea className={css.trace} value={trace} readOnly />
            </div>
        );
    }
}
