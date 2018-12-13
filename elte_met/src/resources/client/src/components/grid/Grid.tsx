import * as React from "react";
import Entity from "../../api/manual/interfaces/Entity";
import Pageable from "../../api/manual/interfaces/Pageable";
import ExtendedComponent from "../../util/ExtendedComponent";
import * as css from "./Grid.css";

interface Props<T extends Entity> {
    entities: Pageable<T>;
    onSelect(t: T): void;
    onEdit(t: T): void;
    onDelete(t: T): void;
}

class State {
    fields: Field[] = [];
}

interface Field {
    name: string;
    type: String | Boolean | Number;
}

export default class Grid<T extends Entity> extends ExtendedComponent<
    Props<T>,
    State
> {
    state = new State();

    componentDidMount() {
        this.updateHeader(this.props);
    }

    componentWillReceiveProps(props: Props<T>) {
        this.updateHeader(props);
    }

    private updateHeader = (props: Props<T>) => {
        const { entities } = props;
        if (!entities.content.length) {
            return;
        }

        this.setStateAsync({
            fields: Object.entries(entities.content[0]).map(([k, v]) => {
                return { name: k, type: v && v.constructor };
            }),
        });
    };

    private selectEntry = (t: T) => this.props.onSelect(t);

    render() {
        const { entities, onEdit, onDelete } = this.props;
        const { fields } = this.state;

        return (
            <div className={css.root}>
                <table className={css.table}>
                    <thead>
                        <tr>
                            {fields.map((field, i) => (
                                <th key={i}>{field.name}</th>
                            ))}
                            <th />
                            <th />
                        </tr>
                    </thead>
                    <tbody>
                        {entities.content.map(data => (
                            <tr
                                key={data.id}
                                onClick={() => this.selectEntry(data)}
                            >
                                {Object.values(data).map((data, i) => (
                                    <td key={i}>{JSON.stringify(data)}</td>
                                ))}
                                <td>
                                    <button onClick={() => onEdit(data)}>
                                        Edit
                                    </button>
                                </td>
                                <td>
                                    <button onClick={() => onDelete(data)}>
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}
