import * as React from "react";
import { ElteMetApi } from "./api/manual/api";
import Exception from "./api/manual/interfaces/Exception";
import Response from "./api/manual/interfaces/Response";
import Crud, { Lookup } from "./components/crud/Crud";
import ExceptionToast from "./components/ExceptionToast";
import LoginScreen from "./components/LoginScreen";
import * as css from "./Test.css";
import ExtendedComponent from "./util/ExtendedComponent";

class State {
    exception?: Exception;
    page:
        | "login"
        | "user"
        | "subject"
        | "dds"
        | "message"
        | "practice"
        | "group"
        | "exception" = "user";
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
                await this.setStateAsync({ exception, page: "exception" });
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
                    {([
                        "login",
                        "user",
                        "subject",
                        "dds",
                        "message",
                        "practice",
                        "group",
                    ] as State["page"][]).map((name, i) => (
                        <button
                            key={i}
                            onClick={() => this.setState({ page: name })}
                        >
                            {name}
                        </button>
                    ))}
                </div>

                {(() => {
                    switch (page) {
                        case "user":
                            return (
                                <Crud
                                    apiHandler={this.api.user}
                                    requestHandler={this.handleRequest}
                                    constructor={() =>
                                        ({
                                            name: "TesztPost",
                                            taxNumber: "1786",
                                            degree: "bsc",
                                            phoneNumber: "06304481282",
                                            bankAccountNumber: "7776655544",
                                            email: "abc@def.com",
                                            nationality: "HUN",
                                            username: "TesztPoszt",
                                            isSuperAdmin: 0,
                                            dateOfBirth: "2018-05-12",
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
                                            name: "TesztSubject",
                                            credit: 1,
                                            hasPractice: 1,
                                            isNecessary: 1,
                                            lecutresPerWeek: 1,
                                            recommendedSemester: 1,
                                            semester: "2018-2019 osz",
                                            whichRoom: "0-804",
                                            lecturer: new Lookup(
                                                this.api.user,
                                            ),
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

                        case "dds":
                            return (
                                <Crud
                                    apiHandler={this.api.dds}
                                    requestHandler={this.handleRequest}
                                    constructor={() =>
                                        ({
                                            date: "2018-10-01T09:45:00.000",
                                            durability: 90,
                                            seatNumber: 30,
                                            practice: 2,
                                        } as any)
                                    }
                                />
                            );

                        case "message":
                            return (
                                <Crud
                                    apiHandler={this.api.message}
                                    requestHandler={this.handleRequest}
                                    constructor={() => ({} as any)}
                                />
                            );

                        case "practice":
                            return (
                                <Crud
                                    apiHandler={this.api.practice}
                                    requestHandler={this.handleRequest}
                                    constructor={() =>
                                        ({
                                            subject: 3,
                                            credit: 1,
                                            teacher: 1,
                                            hasTasks: 1,
                                            howManyTasks: 1,
                                            whichRoom: "0-804",
                                        } as any)
                                    }
                                />
                            );

                        case "group":
                            return (
                                <Crud
                                    apiHandler={this.api.group}
                                    requestHandler={this.handleRequest}
                                    constructor={() =>
                                        ({
                                            name: "TesztGroup",
                                            description:
                                                "TesztGroupDescription",
                                        } as any)
                                    }
                                />
                            );

                        case "exception":
                            <p>Some error happened.</p>;
                    }
                })()}
            </div>
        );
    }
}
