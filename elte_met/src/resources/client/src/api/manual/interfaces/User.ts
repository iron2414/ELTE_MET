export default interface User {
    id: number;
    permissions: any[];
    enabled: boolean;
    superAdmin: boolean;
    authorities: any[];
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    credentialsNonExpired: boolean;
}
