import * as request from "superagent";
import {
    SuperAgentStatic
} from "superagent";

type CallbackHandler = (err: any, res ? : request.Response) => void;
type UserResponse = {
    'success' ? : boolean

    'data' ? : {
        'content' ? : Array < {
                'id' ? : number

                'permissions' ? : Array < string >
                    | string

                'enabled' ? : boolean

                'superAdmin' ? : boolean

                'authorities' ? : Array < string >
                    | string

                'accountNonExpired' ? : boolean

                'accountNonLocked' ? : boolean

                'credentialsNonExpired' ? : boolean

            } >
            | {
                'id' ? : number

                'permissions' ? : Array < string >
                    | string

                'enabled' ? : boolean

                'superAdmin' ? : boolean

                'authorities' ? : Array < string >
                    | string

                'accountNonExpired' ? : boolean

                'accountNonLocked' ? : boolean

                'credentialsNonExpired' ? : boolean

            }

        'pageable' ? : {
            'sort' ? : {
                'sorted' ? : boolean

                'unsorted' ? : boolean

            }

            'offset' ? : number

            'pageSize' ? : number

            'pageNumber' ? : number

            'unpaged' ? : boolean

            'paged' ? : boolean

        }

        'last' ? : boolean

        'totalPages' ? : number

        'totalElements' ? : number

        'size' ? : number

        'number' ? : number

        'first' ? : boolean

        'sort' ? : {
            'sorted' ? : boolean

            'unsorted' ? : boolean

        }

        'numberOfElements' ? : number

    }

};

type Logger = {
    log: (line: string) => any
};

/**
 * No description
 * @class BackendApi
 * @param {(string)} [domainOrOptions] - The project domain.
 */
export default class BackendApi {

    private domain: string = "";
    private errorHandlers: CallbackHandler[] = [];

    constructor(domain ? : string, private logger ? : Logger) {
        if (domain) {
            this.domain = domain;
        }
    }

    getDomain() {
        return this.domain;
    }

    addErrorHandler(handler: CallbackHandler) {
        this.errorHandlers.push(handler);
    }

    private request(method: string, url: string, body: any, headers: any, queryParameters: any, form: any, reject: CallbackHandler, resolve: CallbackHandler) {
        if (this.logger) {
            this.logger.log(`Call ${method} ${url}`);
        }

        let req = (request as any as SuperAgentStatic)(method, url).query(queryParameters);

        Object.keys(headers).forEach(key => {
            req.set(key, headers[key]);
        });

        if (body) {
            req.send(body);
        }

        if (typeof(body) === 'object' && !(body.constructor.name === 'Buffer')) {
            req.set('Content-Type', 'application/json');
        }

        if (Object.keys(form).length > 0) {
            req.type('form');
            req.send(form);
        }

        req.end((error, response) => {
            if (error || !response.ok) {
                reject(error);
                this.errorHandlers.forEach(handler => handler(error));
            } else {
                resolve(response);
            }
        });
    }

    getByUserIdURL(parameters: {
        'userId': number,
        $queryParameters ? : any,
        $domain ? : string
    }): string {
        let queryParameters: any = {};
        const domain = parameters.$domain ? parameters.$domain : this.domain;
        let path = '/{userId}';

        path = path.replace('{userId}', `${parameters['userId']}`);

        if (parameters.$queryParameters) {
            Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
                queryParameters[parameterName] = parameters.$queryParameters[parameterName];
            });
        }

        let keys = Object.keys(queryParameters);
        return domain + path + (keys.length > 0 ? '?' + (keys.map(key => key + '=' + encodeURIComponent(queryParameters[key])).join('&')) : '');
    }

    /**
     * 
     * @method
     * @name BackendApi#getByUserId
     * @param {integer} userId - No description
     */
    getByUserId(parameters: {
        'userId': number,
        $queryParameters ? : any,
        $domain ? : string
    }): Promise < request.Response > {
        const domain = parameters.$domain ? parameters.$domain : this.domain;
        let path = '/{userId}';
        let body: any;
        let queryParameters: any = {};
        let headers: any = {};
        let form: any = {};
        return new Promise((resolve, reject) => {
            headers['Accept'] = 'application/json';
            headers['Content-Type'] = 'application/json';

            path = path.replace('{userId}', `${parameters['userId']}`);

            if (parameters['userId'] === undefined) {
                reject(new Error('Missing required  parameter: userId'));
                return;
            }

            if (parameters.$queryParameters) {
                Object.keys(parameters.$queryParameters).forEach(function(parameterName) {
                    queryParameters[parameterName] = parameters.$queryParameters[parameterName];
                });
            }

            this.request('GET', domain + path, body, headers, queryParameters, form, reject, resolve);
        });
    }

}