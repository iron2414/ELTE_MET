import * as React from "react";
import ExtendedComponent from "../../util/ExtendedComponent";
import "./Details.css";

interface Props {
    data: object;
    onClose: () => void;
}

export default class Details extends ExtendedComponent<Props> {
    back = () => this.props.onClose();

    render() {
        const { data } = this.props;

        return (
            <div>
                <button onClick={this.back}>Back</button>
                {Object.entries(data).map(([k, v], i) => (
                    <p key={i}>
                        {k}: {JSON.stringify(v)}
                    </p>
                ))}
            </div>
        );
    }
}