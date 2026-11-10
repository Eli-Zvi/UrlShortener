/*
Represents the body of a user registration request
*/
export interface RegisterPayload {
    username : string;
    password : string;
    email : string; //Add validation later
}
