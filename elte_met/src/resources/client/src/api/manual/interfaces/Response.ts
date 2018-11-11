import Exception from "./Exception";
type Response<T = never> =
    | {
          success: true;
          data: T;
      }
    | {
          success: false;
          exception: Exception;
      };
export default Response;
