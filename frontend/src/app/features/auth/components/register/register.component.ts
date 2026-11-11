import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../auth.service';
import { RegisterPayloadModel } from '../../models/register-payload.model';
import { form, FormField } from '@angular/forms/signals';

@Component({
  imports: [RouterLink, FormField],
  selector: 'app-register.component',
  styleUrl: '../../../../shared/styles/auth.css',
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  private authService: AuthService = inject(AuthService);

  registerModel = signal<RegisterPayloadModel>({
    username: '',
    password: '',
    email: '',
  });

  registerForm = form(this.registerModel);

  callService(): void {
    this.authService.registerUser(this.registerModel()).subscribe({
      next: (response) => {
        console.log('Success:', response);
      },
      error: (error) => {
        console.error('Error:', error);
      },
    });
  }
}
