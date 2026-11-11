import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterPayloadModel} from './models/register-payload.model';

@Injectable({
  providedIn: 'root'
  })
export class AuthService {
  private http = inject(HttpClient);

  registerUser(user: RegisterPayloadModel) {
    return this.http.post('http://localhost:8080/register', user)
  }
}
