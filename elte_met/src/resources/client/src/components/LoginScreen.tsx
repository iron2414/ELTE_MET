import * as React from "react";
import { ElteMetApi } from "../api/manual/api";
import Response from "../api/manual/interfaces/Response";

interface Props {
    api: ElteMetApi;
    requestHandler: <T extends any>(
        generator: () => Promise<Response<T>>,
    ) => Promise<T | undefined>;
}

class State {
    username: string = "";
    password: string = "";
}

export default class LoginScreen extends React.Component<Props, State> {
    state = new State();

    render() {
        return (
            <div>
                user:{" "}
                <input
                    value={this.state.username}
                    onChange={e => this.setState({ username: e.target.value })}
                />
                pass:{" "}
                <input
                    type="password"
                    value={this.state.password}
                    onChange={e => this.setState({ password: e.target.value })}
                />
                <button
                    onClick={() =>
                        this.props.requestHandler(() =>
                            this.props.api.logIn(
                                this.state.username,
                                this.state.password,
                            ),
                        )
                    }
                >
                    Login
                </button>
            </div>
        );
    }
}
