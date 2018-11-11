import * as React from "react";
import Entity from "../../api/manual/interfaces/Entity";
import ExtendedComponent from "../../util/ExtendedComponent";
import "./Editor.css";

interface Props<T extends Entity> {
    initialValue: T;
    onChange: (t: T) => void;
    onCancel: () => void;
}

class State<T extends Entity> {
    constructor(public entity: T) {}
}

export default class Editor<T extends Entity> extends ExtendedComponent<
    Props<T>,
    State<T>
> {
    constructor(props: Props<T>) {
        super(props);

        this.state = new State(props.initialValue);
    }

    back = () => this.props.onCancel();

    confirm = () => this.props.onChange(this.state.entity);

    editor = (k: keyof T) => {
        const { entity } = this.state;

        const value = entity[k];

        switch (typeof value) {
            case "boolean":
                return (
                    <input
                        type="checkbox"
                        checked={value}
                        onChange={async e =>
                            this.setStateAsync({
                                entity: {
                                    ...(entity as any),
                                    [k]: e.target.checked,
                                },
                            })
                        }
                    />
                );
            case "number":
                return (
                    <input
                        type="number"
                        value={value}
                        onChange={async e =>
                            this.setStateAsync({
                                entity: {
                                    ...(entity as any),
                                    [k]: Number(e.target.value),
                                },
                            })
                        }
                    />
                );
            case "string":
                return (
                    <input
                        type="textbox"
                        value={value}
                        onChange={async e =>
                            this.setStateAsync({
                                entity: {
                                    ...(entity as any),
                                    [k]: e.target.value,
                                },
                            })
                        }
                    />
                );
        }
    };

    render() {
        const { initialValue } = this.props;
        const { entity } = this.state;

        return (
            <div>
                <button onClick={this.back}>Cancel</button>
                <button onClick={this.confirm}>Confirm</button>
                {Object.keys(initialValue).map((k, i) => (
                    <p key={i}>
                        {k}: {this.editor(k as keyof T)}
                    </p>
                ))}
            </div>
        );
    }
}
