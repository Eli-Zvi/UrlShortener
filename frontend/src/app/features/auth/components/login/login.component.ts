import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../auth.service';

@Component({
  imports: [RouterLink],
  selector: 'app-login.component',
  styleUrl: '../../../../shared/styles/auth.css',
  templateUrl: './login.component.html',
})
export class LoginComponent {

}
