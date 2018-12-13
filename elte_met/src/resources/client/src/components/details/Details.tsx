import * as React from "react";
import ExtendedComponent from "../../util/ExtendedComponent";
import * as css from  "./Details.css";
import View from "./View";

interface Props {
    data: object;
    onClose: () => void;
}

export default class Details extends ExtendedComponent<Props> {
    back = () => this.props.onClose();

    render() {
        const { data } = this.props;

        return (
            <div className={css.root}>
                <button onClick={this.back}>◂ Back</button>
                {Object.entries(data).map(([k, v], i) => (
                    <p key={i}>
                        <b>{k}</b>: <View data={v} />
                    </p>
                ))}
            </div>
        );
    }
}
