/*
Represents the body of a user registration request
*/
export interface RegisterPayloadModel {
    username : string;
    password : string;
    email : string; //Add validation later
}
