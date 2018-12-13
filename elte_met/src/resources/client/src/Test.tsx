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
        | "Users"
        | "Subjects"
        | "DDs"
        | "Messages"
        | "Practicals"
        | "Groups"
        | "exception" = "Users";
    loggedIn = true;
    teacher = false;
}

export default class Test extends ExtendedComponent<{}, State> {
    state = new State();
    api: ElteMetApi;

    constructor(props: {}) {
        super(props);
        this.api = new ElteMetApi();
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

        if (!this.state.loggedIn) {
            return (
                <LoginScreen
                    api={this.api}
                    requestHandler={async e => {
                        const x = await e();

                        if (x.success) {
                            await this.setStateAsync({
                                loggedIn: true,
                            });
                        }

                        return this.handleRequest(e);
                    }}
                />
            );
        }

        return (
            <div className={undefined}>
                <div
                    className={css.header}
                    style={{ backgroundImage: "url(img/header.png)" }}
                />

                {exception && (
                    <ExceptionToast
                        message={exception.message}
                        trace={exception.trace}
                        className={exception.className}
                    />
                )}

                <div className={css.buttons}>
                    {([
                        "Users",
                        "Subjects",
                        "DDs",
                        "Messages",
                        "Practicals",
                        "Groups",
                    ] as State["page"][]).map((name, i) => (
                        <>
                            <button
                                key={i}
                                onClick={() => this.setState({ page: name })}
                            >
                                {name}
                            </button>
                            {"|"}
                        </>
                    ))}
                </div>

                <div className={css.mainContent}>
                    {(() => {
                        switch (page) {
                            case "Users":
                                return (
                                    <Crud
                                        apiHandler={this.api.user}
                                        requestHandler={this.handleRequest}
                                        constructor={() =>
                                            ({
                                                name: "Minta Péter",
                                                taxNumber: "29572910",
                                                degree: "BSc",
                                                phoneNumber: "06304481282",
                                                bankAccountNumber: "7776655544",
                                                email: "abc@def.com",
                                                nationality: "HUN",
                                                username: "pminta",
                                                isSuperAdmin: false,
                                                dateOfBirth: new Date(
                                                    "2018-05-12",
                                                ),
                                            } as any)
                                        }
                                    />
                                );

                            case "Subjects":
                                return (
                                    <Crud
                                        apiHandler={this.api.subject}
                                        requestHandler={this.handleRequest}
                                        constructor={() =>
                                            ({
                                                name:
                                                    "Bevezetés a minta tantárgyba 2. EA.",
                                                credit: 4,
                                                hasPractice: true,
                                                isNecessary: true,
                                                lecutresPerWeek: 1,
                                                recommendedSemester: 2,
                                                semester: "2018-2019 ősz",
                                                whichRoom:
                                                    "0-621 Bolyai János terem",
                                                lecturer: new Lookup(
                                                    this.api.user,
                                                ),
                                            } as any)
                                        }
                                    />
                                );

                            case "DDs":
                                return (
                                    <Crud
                                        apiHandler={this.api.dds}
                                        requestHandler={this.handleRequest}
                                        constructor={() =>
                                            ({
                                                date: new Date(
                                                    "2018-10-01T09:45:00.000",
                                                ),
                                                durability: 90,
                                                seatNumber: 30,
                                                practice: new Lookup(
                                                    this.api.practice,
                                                ),
                                            } as any)
                                        }
                                    />
                                );

                            case "Messages":
                                return (
                                    <Crud
                                        apiHandler={this.api.message}
                                        requestHandler={this.handleRequest}
                                        constructor={() => ({} as any)}
                                    />
                                );

                            case "Practicals":
                                return (
                                    <Crud
                                        apiHandler={this.api.practice}
                                        requestHandler={this.handleRequest}
                                        constructor={() =>
                                            ({
                                                subject: new Lookup(
                                                    this.api.subject,
                                                ),
                                                credit: 1,
                                                teacher: new Lookup(
                                                    this.api.user,
                                                ),
                                                hasTasks: true,
                                                howManyTasks: 1,
                                                whichRoom: "0-804",
                                            } as any)
                                        }
                                    />
                                );

                            case "Groups":
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
                                <p />;
                        }
                    })()}
                </div>
            </div>
        );
    }
}
