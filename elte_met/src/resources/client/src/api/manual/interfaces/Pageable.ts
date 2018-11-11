export interface Sort {
    sorted: boolean;
    unsorted: boolean;
}

export interface Pageable2 {
    sort: Sort;
    offset: number;
    pageSize: number;
    pageNumber: number;
    unpaged: boolean;
    paged: boolean;
}

export interface Sort2 {
    sorted: boolean;
    unsorted: boolean;
}

export default interface Pageable<T> {
    content: T[];
    pageable: Pageable2;
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    numberOfElements: number;
    first: boolean;
    sort: Sort2;
}
