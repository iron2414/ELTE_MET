import * as React from "react";

export default class ExtendedComponent<
    P = {},
    S = {},
    SS = {}
> extends React.Component<P, S, SS> {
    setStateAsync<K extends keyof S>(
        state:
            | ((
                  prevState: Readonly<S>,
                  props: Readonly<P>,
              ) => Pick<S, K> | S | null)
            | (Pick<S, K> | S | null),
    ): Promise<void> {
        return new Promise(resolve => this.setState(state, resolve));
    }
}
