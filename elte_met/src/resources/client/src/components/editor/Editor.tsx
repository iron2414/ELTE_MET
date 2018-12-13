import * as React from "react";
import { CrudSubpage } from "../../api/manual/api";
import Entity from "../../api/manual/interfaces/Entity";
import ExtendedComponent from "../../util/ExtendedComponent";
import { Lookup } from "../crud/Crud";
import "./Editor.css";

interface Props<T extends Entity> {
    initialValue: T;
    onChange: (t: T) => void;
    onCancel: () => void;
}

class State<T extends Entity> {
    constructor(public entity: T) {}
    lookups: { [x: string]: Map<number, string> } = {};
}

export default class Editor<T extends Entity> extends ExtendedComponent<
    Props<T>,
    State<T>
> {
    constructor(props: Props<T>) {
        super(props);

        this.state = new State(props.initialValue);
    }

    componentDidMount() {
        for (const [k, v] of Object.entries(this.state.entity)) {
            if (v instanceof Lookup && v.source instanceof CrudSubpage) {
                (async () => {
                    const resp = await (v.source as CrudSubpage<Entity>).get();
                    if (resp.success) {
                        this.setState({
                            lookups: {
                                ...this.state.lookups,
                                [k]: new Map(
                                    resp.data.content.map(
                                        e =>
                                            [
                                                e.id,
                                                (e as any).name || `${e.id}`,
                                            ] as [number, string],
                                    ),
                                ),
                            },
                        });
                    }
                })();
            }
        }
    }

    back = () => this.props.onCancel();

    confirm = () => this.props.onChange(this.state.entity);

    editor = (k: keyof T) => {
        const { entity } = this.state;

        const value = entity[k];

        if (k == "id") {
            return `${value}`;
        }

        if (value instanceof Lookup) {
            return (
                <select
                    value={`${value.defaultValue}`}
                    onChange={async e =>
                        this.setStateAsync({
                            entity: {
                                ...(entity as any),
                                [k]: new Lookup(
                                    value.source,
                                    Number(e.target.value),
                                    value.isMultiselect,
                                ),
                            },
                        })
                    }
                    multiple={value.isMultiselect}
                >
                    {[
                        ...(value.source instanceof CrudSubpage
                            ? this.state.lookups[k as string] ||
                              new Map([[-1, "fetching ..."]])
                            : value.source
                        ).entries(),
                    ].map(([value, name]) => (
                        <option key={value} value={`${value}`}>
                            {name}
                        </option>
                    ))}
                </select>
            );
        }

        if (value instanceof Date) {
            return (
                <input
                    type="date"
                    value={`${value}`}
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
