import * as React from "react";
import { ElteMetApi } from "./api/manual/api";
import Exception from "./api/manual/interfaces/Exception";
import Response from "./api/manual/interfaces/Response";
import Crud from "./components/crud/Crud";
import ExceptionToast from "./components/ExceptionToast";
import LoginScreen from "./components/LoginScreen";
import * as css from "./Test.css";
import ExtendedComponent from "./util/ExtendedComponent";

class State {
    exception?: Exception;
    page: "login" | "user" | "subject" = "user";
}

export default class Test extends ExtendedComponent<{}, State> {
    state = new State();
    api: ElteMetApi;

    constructor(props: {}) {
        super(props);
        this.api = new ElteMetApi();
    }

    async componentDidMount() {
        // const api = new BackendApi();
        // const { api } = this;
        // try {
        //     const result = await api.getUsers();
        //     console.log("res", result);
        //     if (result.success) {
        //         await this.setStateAsync({ users: result });
        //     }
        //     // const r = await api.logIn("admin", "admin");
        // } catch (e) {
        //     if ("message" in e) {
        //         const exception: Exception = e;
        //         await this.setState({ exception });
        //         console.log(JSON.stringify(exception.trace));
        //     } else {
        //         throw e;
        //     }
        // }
    }

    handleRequest = async <T extends any>(
        generator: () => Promise<Response<T>>,
    ): Promise<T | undefined> => {
        try {
            const result = await generator();

            console.log("res", result);

            if (!result.success) {
                const e = result.exception;
                if ("className" in e) {
                    const exception: Exception = e;
                    await this.setStateAsync({ exception });
                    console.log(JSON.stringify(exception.trace));
                } else {
                    throw e;
                }
            } else {
                return result.data;
            }
        } catch (e) {
            if ("className" in e) {
                const exception: Exception = e;
                await this.setStateAsync({ exception });
                console.log(JSON.stringify(exception.trace));
            } else {
                throw e;
            }
        }
    };

    // selectUser = async (user: User) => {
    //     this.setState({
    //         detailsForUser: user.id,
    //         userDetails: await this.handleRequest(() =>
    //             this.api.getUser(user.id),
    //         ),
    //     });
    // };

    // closeDetails = () => this.setState({ detailsForUser: undefined });

    render() {
        const { exception, page } = this.state;

        return (
            <div className={undefined}>
                {exception && (
                    <ExceptionToast
                        message={exception.message}
                        trace={exception.trace}
                        className={exception.className}
                    />
                )}
                <div className={css.buttons}>
                    {(["login", "user", "subject"] as State["page"][]).map(
                        (name, i) => (
                            <button
                                key={i}
                                onClick={() => this.setState({ page: name })}
                            >
                                {name}
                            </button>
                        ),
                    )}
                </div>

                {(() => {
                    switch (page) {
                        case "user":
                            return (
                                <Crud
                                    apiHandler={this.api.users}
                                    requestHandler={this.handleRequest}
                                    constructor={() =>
                                        ({
                                            name: "",
                                            username: "",
                                            phoneNumber: "",
                                            email: "",
                                            isEnabled: true,
                                        } as any)
                                    }
                                />
                            );

                        case "subject":
                            return (
                                <Crud
                                    apiHandler={this.api.subject}
                                    requestHandler={this.handleRequest}
                                    constructor={() =>
                                        ({
                                            name: "",
                                            credit: 0,
                                            lecturer: 0,
                                        } as any)
                                    }
                                />
                            );

                        case "login":
                            return (
                                <LoginScreen
                                    api={this.api}
                                    requestHandler={this.handleRequest}
                                />
                            );
                    }
                })()}
            </div>
        );
    }
}
