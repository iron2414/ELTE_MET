import Entity from "./interfaces/Entity";
import Pageable from "./interfaces/Pageable";
import Response from "./interfaces/Response";
import Subject from "./interfaces/Subject";
import User from "./interfaces/User";

type Method = "GET" | "POST" | "PUT" | "DELETE";

export class CrudSubpage<T extends Entity> {
    public namePlural: string;

    constructor(
        private parent: ElteMetApi,
        public name: string,
        namePlural?: string,
    ) {
        this.namePlural = namePlural || name + "s";
    }

    public get(): Promise<Response<Pageable<T>>>;
    public get(id: number): Promise<Response<T>>;
    public get(id?: number) {
        const { name, namePlural } = this;

        if (id == null) {
            return this.parent.get(`/${name}/${namePlural}`);
        } else {
            return this.parent.get(`/${name}/${name}/${id}`);
        }
    }

    public post(entity: T): Promise<Response<never>> {
        return this.parent.post(`/${this.name}/${this.namePlural}`, entity);
    }

    public put(entity: T) {
        return this.parent.fetch(
            `/${this.name}/${this.namePlural}`,
            "PUT",
            entity,
        );
    }

    public delete(id: number) {
        return this.parent.fetch(`/${this.name}/${id}`, "DELETE");
    }
}

export class ElteMetApi {
    public base = "http://localhost:8080";

    public token?: string;

    constructor() {
        this.init();
    }

    public async fetch<T>(path: string, method: Method = "GET", body?: object) {
        console.assert(path.startsWith("/"), "Path should start with '/'.");
        const { token } = this;
        const response: Response = await (await fetch(this.base + path, {
            method,
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json",
                "X-CSRF-TOKEN": token || "",
            },
            body: body && JSON.stringify(body),
        })).json();

        if (!response.success) {
            throw response.exception;
        }

        return response;
    }

    public async get<T>(path: string) {
        return this.fetch<T>(path);
    }

    public async post<T>(path: string, body: object) {
        return this.fetch<T>(path, "POST", body);
    }

    private async init() {
        await this.get("/session_init");
        const token = document.cookie.match(/XSRF-TOKEN=([^;]+);/);
        this.token = (token && token[1]) || undefined;
    }

    public async logIn(username: string, password: string) {
        return await this.post("/login", { username, password });
    }

    // public async getUsers(): Promise<Response<Pageable<User>>> {
    //     return this.get("/user/users");
    // }

    // public async getUser(id: number): Promise<Response<User>> {
    //     return this.get(`/user/user/${id}`);
    // }

    public users = new class extends CrudSubpage<User> {
        constructor(parent: ElteMetApi) {
            super(parent, "user");
        }
    }(this);

    public subject = new CrudSubpage<Subject>(this, "subject");
}
