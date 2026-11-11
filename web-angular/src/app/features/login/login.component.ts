import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  email = 'ahmed@matjari.demo';
  password = 'demo123';
  name = 'أحمد';
  error = '';

  submit(): void {
    if (!this.email || !this.password) {
      this.error = 'أدخل البريد وكلمة المرور';
      return;
    }
    const ok = this.auth.login(this.email, this.password, this.name);
    if (ok) {
      this.router.navigateByUrl('/home');
    } else {
      this.error = 'تعذّر تسجيل الدخول';
    }
  }
}
