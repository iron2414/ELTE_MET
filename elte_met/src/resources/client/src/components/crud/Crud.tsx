import * as React from "react";
import { CrudSubpage } from "../../api/manual/api";
import Entity from "../../api/manual/interfaces/Entity";
import Pageable from "../../api/manual/interfaces/Pageable";
import Response from "../../api/manual/interfaces/Response";
import ExtendedComponent from "../../util/ExtendedComponent";
import Details from "../details/Details";
import Editor from "../editor/Editor";
import Grid from "../grid/Grid";
import ConfirmScreen from "./ConfirmScreen";
import * as css from "./Crud.css";

interface Props<T extends Entity> {
    apiHandler: CrudSubpage<T>;
    requestHandler: <T extends any>(
        generator: () => Promise<Response<T>>,
    ) => Promise<T | undefined>;
    constructor: () => T;
}

class State<T extends Entity> {
    entities?: Pageable<T>;
    details?: T;
    isEditing: boolean = false;
    idToDelete?: number;
}

export class Lookup extends Number {
    constructor(
        public source: CrudSubpage<Entity> | Map<number, string>,
        public defaultValue = 0,
        public isMultiselect = false,
    ) {
        super(defaultValue);
    }
}

export default class Crud<T extends Entity> extends ExtendedComponent<
    Props<T>,
    State<T>
> {
    state = new State<T>();

    componentDidMount() {
        this.init();
    }

    componentWillReceiveProps(props: Props<T>) {
        this.init(props);
    }

    init = async (props: Props<T> = this.props) => {
        const { apiHandler, requestHandler } = props;

        await this.setStateAsync({
            entities: await requestHandler(() => apiHandler.get()),
        });
    };

    closeDetails = () =>
        this.setState({
            details: undefined,
            isEditing: false,
            idToDelete: undefined,
        });

    onSelect = async (t: T) => {
        const { apiHandler, requestHandler } = this.props;

        const details = await requestHandler(() => apiHandler.get(t.id));
        if (!details) return;

        const newlyCreatedObject = this.props.constructor();

        for (const [k, v] of Object.entries(newlyCreatedObject) as Iterable<
            [keyof T, T[keyof T]]
        >) {
            if (v instanceof Lookup) {
                if (k in details) {
                    // @ts-ignore
                    newlyCreatedObject[k] = new Lookup(
                        v.source,
                        Number(details[k]),
                        v.isMultiselect,
                    );
                } else {
                    newlyCreatedObject[k] = details[k];
                }
            }
        }

        newlyCreatedObject.id = details.id;

        await this.setStateAsync({
            details: newlyCreatedObject,
        });
    };

    onEdit = async (t: T) => {
        await this.setStateAsync({ isEditing: true });
        await this.onSelect(t);
    };

    onEditConfirmed = async (t: T) => {
        const { apiHandler, requestHandler } = this.props;
        const { isEditing, details } = this.state;

        if ("id" in t) {
            await requestHandler(() => apiHandler.put(t));
        } else {
            // We must be creating.
            await requestHandler(() => apiHandler.post(t));
        }

        await this.closeDetails();
        await this.init();
    };

    onDelete = async (t: T) => {
        await this.setStateAsync({ idToDelete: t.id });
    };

    onDeleteConfirmed = async () => {
        const { apiHandler, requestHandler } = this.props;
        const { idToDelete } = this.state;

        if (idToDelete == null) {
            return;
        }

        await requestHandler(() => apiHandler.delete(idToDelete));
        await this.init();
    };

    create = async () => {
        await this.setStateAsync({
            isEditing: true,
            details: this.props.constructor(),
        });
    };

    render() {
        const { entities, details, isEditing, idToDelete } = this.state;

        return (
            <div className={css.root}>
                {((): JSX.Element | undefined => {
                    if (idToDelete != null) {
                        return (
                            <ConfirmScreen
                                onDelete={this.onDeleteConfirmed}
                                onCancel={this.closeDetails}
                            />
                        );
                    }

                    if (isEditing && details) {
                        return (
                            <Editor
                                initialValue={details}
                                onChange={this.onEditConfirmed}
                                onCancel={this.closeDetails}
                            />
                        );
                    }

                    if (details) {
                        return (
                            <Details
                                data={details}
                                onClose={this.closeDetails}
                            />
                        );
                    }

                    if (entities) {
                        return (
                            <>
                                <div className={css.buttons}>
                                    <button onClick={this.create}>+ Add</button>
                                </div>
                                <Grid
                                    {...{ entities }}
                                    onSelect={this.onSelect}
                                    onEdit={this.onEdit}
                                    onDelete={this.onDelete}
                                />
                            </>
                        );
                    }
                })()}
            </div>
        );
    }
}
